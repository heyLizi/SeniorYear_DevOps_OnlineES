package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.ExamService;
import cn.edu.nju.software.service.MailService;
import common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import param.MarkParam;
import param.SelectParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author danian
 *         定义了学生的考试进入过程的方法
 */
@Controller
@RequestMapping("exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private MailService mailService;

    //finish 确认交卷后，跳转到已完成界面（显示“谢谢参与”什么的）
    @RequestMapping(value="finish", method=RequestMethod.POST)
    public String finish(@RequestParam String sid, @RequestParam String eid, HttpServletRequest request, HttpServletResponse response) {
    	
    	Result finishResult = examService.finishExam(Long.parseLong(sid), Long.parseLong(eid));
    	if(finishResult.isSuccess()) {
    		//提交成功了就给该学生发送邮件，结束考试
    		Result mailResult = mailService.sendScoreCode(Long.parseLong(sid), Long.parseLong(eid));
    		//删除session
            request.getSession().invalidate();
    	}
    	return "finish";
    }

    // -----------------------------------------我是分界线----------------------------------------
    // 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
    // -----------------------------------------我是分界线----------------------------------------

    //mark 标记某题（已经标记了那就取消标记）
    //@RequestMapping(value = "/mark", produces = "application/json; charset=utf-8")
    @PostMapping("mark")
    @ResponseBody
    public Result mark(@RequestBody MarkParam markParam, HttpServletRequest request, HttpServletResponse response) {
        if (!judgeSession(request)) {
            return invalid(response);
        }
        //#finished 标记该题
        //标记题目
        return examService.mark(markParam);
        //return Result.success().code("0");
    }

    //select 选择某题一个选项(该选项已经选中就取消这个选项）
    @PostMapping("select")
    @ResponseBody
    public Result select(@RequestBody SelectParam selectParam, HttpServletRequest request, HttpServletResponse response) {
        if (!judgeSession(request)) {
            return invalid(response);
        }
        //#finished 设置该题选项，如果已经选中了这个选项那就取消选择
        //设置一个题目的选项
        return examService.select(selectParam);
        //return Result.success().code("0");
    }

    /**
     * 获取本次考试的基本信息
     * result如果为success，data为ExamDto
     * @param eid
     * @return
     */
    @GetMapping("info/{eid}")
    @ResponseBody
    public Result getExamInfo(@PathVariable Long eid){
        return examService.getExamInfo(eid);
    }

    /**
     * 获取该学生本次考试的所有试题信息
     * 组织方式见文档
     * @param sid
     * @param eid
     * @return
     */
    @GetMapping("paper")
    @ResponseBody
    public Result getWholePaper(@RequestParam Long sid,@RequestParam Long eid){
        return examService.getExamPaperResult(sid,eid);
    }

    /**
     * 获取该学生某道题目的详情
     * 组织方式见文档
     * @param examExerciseId
     * @return
     */
    @GetMapping("exercise/{examExerciseId}")
    @ResponseBody
    public Result getExamExercise(@PathVariable Long examExerciseId){
        return examService.getExamExercise(examExerciseId);
    }

//    @PostMapping("paper/{secretCode}")
//    public Result queryExamPaper(@PathVariable String secretCode,HttpServletRequest request,HttpServletResponse response){
//        if (!judgeSession(request)) {
//            return invalid(response);
//        }
//
//    }

    //jump 如果是跳到最后一题的下一页，那就submit
//    @RequestMapping("jump")
//    public void jump(HttpServletRequest request, HttpServletResponse response) {
//        if (!judgeSession(request)) {
//            invalid(response);
//            return;
//        }
//
//        //学生id
//        String sid = request.getParameter("sid");
//
//        //考试id
//        String eid = request.getParameter("eid");
//
//        //#unfinished 这道题的信息
//        QuestionCurDto info = new QuestionCurDto();
//
//        Result r = Result.success().withData(info).code("0");
//        Result.sendResult(response, r);
//    }

    // -----------------------------------------我是分界线----------------------------------------
    // 以下是 common方法 供所有方法调用，没有写过滤器，因为就一点
    // -----------------------------------------我是分界线----------------------------------------

    //judgeSession
    public boolean judgeSession(HttpServletRequest request) {
        //判断是否已经登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("exam") == null) {
            return false;
        }
        return true;
    }

    //invalid
    public Result invalid(HttpServletResponse response) {
        return Result.error().errorMessage("未授权的访问").code("1");
    }
}
