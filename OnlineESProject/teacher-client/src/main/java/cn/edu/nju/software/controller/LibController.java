package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.QuestionFeign;
import cn.edu.nju.software.service.CourseService;
import com.alibaba.fastjson.JSON;
import common.SystemConst;
import dto.CourseDto;
import dto.LibDto;
import dto.QuestionBriefDto;
import common.Result;
import dto.QuestionDto;
import entity.ExerciseBank;
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
 * 定义了教师的某个课程的题库界面
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */
 @Controller
public class LibController {

    @Autowired
    private QuestionFeign questionFeign;


    /**
     * finished
     * 进入查看题目详情页
     * @param qid
     * @return
     */
    @RequestMapping("questionDetail")
    public ModelAndView questionDetail(HttpServletRequest request,@RequestParam("qid")Integer qid) {
        HttpSession session = request.getSession();
        //课程唯一id
        Integer cid = (Integer) session.getAttribute("cid");
        //题库唯一id
        Integer lid = (Integer) session.getAttribute("lid");
        //#课程名，题库名
        String cName = (String) session.getAttribute("cName");
        String lName = (String) session.getAttribute("lName");
        //#finished 试题详情
        QuestionDto questionDto = questionFeign.queryQuestionDetail(qid);
        ModelAndView view = new ModelAndView("question");
        view.addObject("cid", cid);
        view.addObject("lid", lid);
        view.addObject("cName", cName);
        view.addObject("lName", lName);
        view.addObject("info", JSON.toJSONString(questionDto));
        return view;
    }

    /**
     * finished
     * 进入上传题目页
     * @param request
     * @return
     */
    @RequestMapping(value = "questionAdd",method = RequestMethod.GET)
    public ModelAndView questionAdd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //课程ID+名，题库ID+名
        Integer cid = (Integer) session.getAttribute("cid");
        Integer lid = (Integer) session.getAttribute("lid");
        String cName = (String) session.getAttribute("cName");
        String lName = (String) session.getAttribute("lName");
        ModelAndView view = new ModelAndView("addQue");
        view.addObject("cid", cid);
        view.addObject("lid", lid);
        view.addObject("cName", cName);
        view.addObject("lName", lName);
        return view;
    }

	// -----------------------------------------我是分界线----------------------------------------
	// 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
	// -----------------------------------------我是分界线----------------------------------------

    /**
     * finished
     * 翻页查看题目信息
     * @param lid
     * @return
     */
    @ResponseBody
	@RequestMapping("questionList1")
	public String questionPage(@RequestParam("lid")Integer lid,@RequestParam("page")Integer page) {
        if(page < 0){
            return JSON.toJSONString(Result.error().errorMessage("参数错误！"));
        }
        //#finished 该课程的题库列表
        List<QuestionBriefDto> questionDtos = questionFeign.queryQuestionList(lid,page, SystemConst.STUDENT_PAGE_SIZE);
        Result result = Result.success().withData(questionDtos);
        return JSON.toJSONString(result);
	}


	//todo 删除题目
    @ResponseBody
    @RequestMapping("questionDelete")
    public String questionDelete(@RequestParam("cid")Integer cid, @RequestParam("lid")Integer lid, @RequestParam("qids")String qidsStr, HttpServletRequest request) {
        //课程id
        //题库唯一id
        //试题id
        String[] qids = qidsStr.split(" ");

        //#unfinished 删除该试题
        boolean result = true;
        if(result) {
            Result r = Result.success().withData("删除成功");
            return JSON.toJSONString(r);
        } else {
            Result r = Result.error().errorMessage("删除失败");
            return JSON.toJSONString(r);
        }
        //删除后还是在这一页,懒得重定向了，直接调用吧
//		questionList(request, response);
    }
}
