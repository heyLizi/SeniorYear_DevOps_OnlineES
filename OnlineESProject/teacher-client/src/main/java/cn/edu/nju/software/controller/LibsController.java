package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.QuestionFeign;
import cn.edu.nju.software.service.CourseService;
import com.alibaba.fastjson.JSON;
import common.SystemConst;
import dto.CourseDto;
import dto.LibDto;
import common.Result;
import dto.QuestionBriefDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author danian
 * 定义了教师的题库管理界面
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */
@Controller
public class LibsController {
    @Autowired
    private QuestionFeign questionFeign;


    @Autowired
    private CourseService courseService;

    /**
     * finished
     * 进入查看题目列表页
     * @param lid
     * @return
     */
    @RequestMapping("questions")
    public ModelAndView questionList(HttpServletRequest request,@RequestParam("lid")Integer lid) {
        HttpSession session = request.getSession();
        LibDto libDto = questionFeign.queryBankById(lid);
        if(libDto == null){
            return new ModelAndView("error");
        }
        //#finished 题库名字
        String lName = libDto.getName();
        session.setAttribute("lid",lid);
        session.setAttribute("lName",lName);
        //#finished 总题目数
        int questionCount = questionFeign.queryQuestionCount(lid);
        //#finished 第0页也就是前12道题的信息
        List<QuestionBriefDto> questionDtos = questionFeign.queryQuestionList(lid,0, SystemConst.STUDENT_PAGE_SIZE);

        //#unfinished 所有题目的id，用作后面的删除
        String[] qids = new String[]{"123", "22", "3"};

        //#unfinished 课程名字
        String cName = "haha";

        ModelAndView view = new ModelAndView("questionList");
        view.addObject("qids", qids);
        view.addObject("lName", lName);
        view.addObject("cName", cName);
        view.addObject("infos", JSON.toJSONString(questionDtos));
        view.addObject("total",questionCount);
        return view;
    }

    /**
     * finished
     * lib/add 新增题库页面
     * @return
     */
    @RequestMapping(value = "libAdd",method = RequestMethod.GET)
    public ModelAndView addView(HttpServletRequest request,@RequestParam("cid") Integer cid) {
        HttpSession session = request.getSession();
        String tid = (String) session.getAttribute("tid");
        if(tid == null){
            return new ModelAndView("redirect:/login");
        }
        CourseDto courseDto = courseService.queryCourseDetail(tid,cid);
        if(courseDto == null){
            return new ModelAndView("redirect:/error");
        }
        ModelAndView view = new ModelAndView("addLib");
        view.addObject("course",JSON.toJSONString(courseDto));
        return view;
    }
	
	// -----------------------------------------我是分界线----------------------------------------
	// 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
	// -----------------------------------------我是分界线----------------------------------------

    /**
     * finished
     * 分页查询题库列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "libList",method = RequestMethod.GET)
    public String courseListPage(HttpServletRequest request,@RequestParam("cid")Integer cid, @RequestParam("page")Integer page){
        HttpSession session = request.getSession();
        String tid = (String) session.getAttribute("tid");

        if(tid == null){
            return JSON.toJSONString(Result.error().errorMessage("请先登录！"));
        }
        if(page < 0){
            return JSON.toJSONString(Result.error().errorMessage("参数错误！"));
        }
        //#finished 该课程的题库列表
        List<LibDto> libDtos = questionFeign.queryBankList(cid,page, SystemConst.PAGE_SIZE);
        Result result = Result.success().withData(libDtos);
        return JSON.toJSONString(result);
    }


    //todo libDelete 删除题库（多个）
    @RequestMapping("libDelete")
    @ResponseBody
    public String libDelete(@RequestParam("lids")String lidsStr,@RequestParam("cid")Integer cid, HttpServletRequest request) {
        // 课程的唯一cid
        // 题库lib列表
        String[] lids = lidsStr.split(" ");

        // #unfinished 删除题库，成功返回true，否则返回false
        boolean result = true;

        if(result) {
            Result r = Result.success().withData("删除成功");
            return JSON.toJSONString(r);
        } else {
            Result r = Result.error().errorMessage("删除失败");
            return JSON.toJSONString(r);
        }
    }
}
