package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.ExamService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import common.Result;
import dto.ExamCurDto;
import dto.ExamDto;
import dto.StudentExamDto;
import entity.ExamPaper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Map;

/**
 * @author danian
 *         定义了学生的考试进入过程的方法
 *         未完成的方法，那一句上面的注释为  #unfinished
 *         完成的方法，注释内容改为  #finished
 *         下个版本拓展的方法，注释为 #todo
 *         ajax方法已经标明,注意ajax一定要返回json格式字符串
 */
@Controller
@Slf4j
public class VerifyController {

    @Autowired
    private ExamService examService;


    //exam/code=yyyy进入密码验证界面
    @RequestMapping("exam/verify/{code}")
    public ModelAndView decode(@PathVariable String code, HttpServletRequest request) {

        //解密后Code获取sid和eid
        Map<String, Long> params = getParamByCode(code);
        Long sid = params.get("sid");
        Long eid = params.get("eid");

        //判断session，看是不是掉线重登
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("exam") != null) {
            //#finished 只使用code也是可以查到这场考试的吧,那下面的sid和eid应该也可以知道
            return new ModelAndView("redirect:/action?type=enter?sid=" + sid + "&eid=" + eid);
        }

        //这里需要考生和考试的信息，如果只记录考生的信息，那么掉线重登后所有信息全都没有了
        //ExamDto examInfo = (ExamDto)examService.getExamInfo(eid).getData();
        //StudentExamDto stuExamInfo = new StudentExamDto();
        //这里也需要获取学生的信息，比如姓名等


        Result examResult = examService.getExamInfo(eid);
        ExamDto examInfo = JSON.parseObject(JSON.toJSONString(examResult.getData()), ExamDto.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //以下为桩代码
//        ExamDto examInfo = new ExamDto();
//        examInfo.setId("1");							//考试ID
//        examInfo.setCourseName("颈椎病康复指南");			//课程名称
//        examInfo.setName("2017年第二学期期中测试");			//考试名称
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        try {
//			examInfo.setStartTime(sdf.parse("2017-12-18 08:00:00"));	//开始日期
//			examInfo.setEndTime(sdf.parse("2017-12-18 10:00:00"));	//结束日期
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//        examInfo.setPeriod(120);						//考试时长
//        examInfo.setQuestionCount(20);					//试题数
//        examInfo.setStudentCount(200);					//考生人数
        Result stuExam = examService.getExamStudent(sid,eid);
        StudentExamDto stuExamInfo = JSON.parseObject(JSON.toJSONString(stuExam.getData()), StudentExamDto.class);
//        StudentExamDto stuExamInfo = new StudentExamDto();
//        stuExamInfo.setId(1);                //试卷ID
//        stuExamInfo.setSid(1);                //学生ID
//        stuExamInfo.setName("狗剩");            //学生姓名
//        stuExamInfo.setNumber("141250185");    //学生学号

        //这里转换成字符串写到ModelAndView里面，否则需要界面javascript转换，非常麻烦
        String startTimeStr = sdf.format(examInfo.getStartTime());
        String endTimeStr = sdf.format(examInfo.getEndTime());

        ModelAndView m = new ModelAndView("verify");
        m.addObject("stuExamInfo", stuExamInfo);
        m.addObject("examInfo", examInfo);
        m.addObject("sid", sid);
        m.addObject("eid", eid);
        m.addObject("startTime", startTimeStr);
        m.addObject("endTime", endTimeStr);

        return m;
    }


    //进入考试界面，返回三种界面：(1)考试尚未开始，提示用户还有多长时间开始 (2)考试已经开始但未结束，进入考试 (3)考试已经结束，提示用户已结束
    @RequestMapping(value = "enter", method = RequestMethod.POST)
    public ModelAndView enter(@RequestParam Long sid, @RequestParam Long eid, @RequestParam String sname, HttpServletRequest request) {

        System.out.println("GetParam: " + sid + "    " + eid + "   " + sname);

        Result examResult = examService.getExamInfo(eid);

        ExamDto examDto = JSON.parseObject(JSON.toJSONString(examResult.getData()), ExamDto.class);
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        try {
//			examDto.setStartTime(sdf.parse("2017-12-16 00:00:00"));	//开始日期
//			examDto.setEndTime(sdf.parse("2017-12-16 23:59:59"));	//结束日期
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
        ExamPaper examPaper = examService.getExamPaper(eid,sid);
        int state = examPaper.getStatus();

        System.out.println(examDto.getStartTime() + "    " + examDto.getEndTime());
        if (examDto.getEndTime().getTime() <= System.currentTimeMillis()||state==2) {
            //考试结束时间小于当前时间，考试已结束， state=2，提示用户考试结束
            state = 2;
            ModelAndView m = new ModelAndView("tip");
            m.addObject("sid", sid);
            m.addObject("eid", eid);
            m.addObject("sname", sname);
            m.addObject("state", state);
            m.addObject("waitSec", -1); //在考试结束后也加入这个waitSec参数，否则tip页面会报错
            return m;
        } 
        else if (System.currentTimeMillis() <= examDto.getStartTime().getTime()) {
            //考试开始时间大于当前时间，考试未开始，state=0，提示用户还有距离考试开始还有多长时间（单位为秒）
            state = 0;
            Long waitSec = (examDto.getStartTime().getTime() - System.currentTimeMillis()) / 1000;  //距考试开始还有多长时间
            ModelAndView m = new ModelAndView("tip");
            m.addObject("sid", sid);
            m.addObject("eid", eid);
            m.addObject("sname", sname);
            m.addObject("state", state);
            m.addObject("waitSec", waitSec);
            return m;
        } else {
            //#finished 返回考试总时间，单位秒，用于设置session超时时间
            Long remainSec = (examDto.getEndTime().getTime() - System.currentTimeMillis()) / 1000;  //距考试结束还有多长时间
            System.out.println("remainSec:" + remainSec);
            log.info("remainSec:{}",remainSec);
            //没明白如果掉线重登之后是什么情况
            //如果没有session就创建一个
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("exam") == null) {
                //创建
                HttpSession newSession = request.getSession();
                newSession.setMaxInactiveInterval(remainSec.intValue()); //考试到时间或者提交试卷会删除这个session
                newSession.setAttribute("exam", 1);
                newSession.setAttribute("sid",sid);
                newSession.setAttribute("eid",eid);
            }


            //获取试卷信息
            Result paperResult = examService.getExamPaperResult(sid, eid);
            System.out.println(paperResult.getData());
            //List<ExamExerciseDto> exerciseListDto = JSON.parseObject(JSON.toJSONString(paperResult.getData()),List<ExamExerciseDto.class>);

            //考试正在进行，返回当前的答题信息
            ExamCurDto curInfo = new ExamCurDto();
            curInfo.setCur(0);//刚进入考试默认从第一题开始，下标为0

            ModelAndView m = new ModelAndView("exam");
            m.addObject("sid", sid);
            m.addObject("eid", eid);
            m.addObject("sname", sname);
            m.addObject("curInfo", curInfo);
            m.addObject("remainSec", remainSec);
            m.addObject("paperInfo", JSON.toJSON(paperResult.getData()));
            return m;
        }

    }

    //end 考试结束界面后的js自动调用该方法跳到结束界面，考试结束后学生访问考试界面都跳到这个界面
    @RequestMapping("end")
    public String end() {
        return "end";
    }

    // -----------------------------------------我是分界线----------------------------------------
    // 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
    // -----------------------------------------我是分界线----------------------------------------

    //验证参加考试的密码是否正确
    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public void verify(@RequestParam Long sid, @RequestParam Long eid, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {

        System.out.println(sid + "    " + eid + "   " + password);

        //Result result = Result.success();
        Result result = examService.checkEnterPwd(sid, eid, password);
        Result.sendResult(response, result);
    }

    //将解密后的链接的数据组织成map供调用
    private Map<String, Long> getParamByCode(String code) {
        Map<String, Long> params = Maps.newHashMap();
        byte[] bytes = Base64.getDecoder().decode(code);
        String[] vars = new String(bytes).split("&");
        for (String var : vars) {
            String[] data = var.split("=");
            params.put(data[0], Long.parseLong(data[1]));
        }
        return params;
    }

}