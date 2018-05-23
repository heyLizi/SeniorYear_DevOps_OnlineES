package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.QuestionFeign;
import cn.edu.nju.software.service.CourseService;
import com.alibaba.fastjson.JSON;
import common.Result;
import common.SystemConst;
import dto.CourseDto;
import dto.ExamDto;
import dto.LibBriefDto;
import dto.LibDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CoursesController {

    //跳转到题库列表
    //跳转到创建考试界面

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionFeign questionFeign;
    /**
     * finished
     * 创建考试页
     * @param request
     * @return
     */
    @RequestMapping(value = "createExam",method = RequestMethod.GET)
    public ModelAndView createExamPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        String tid = (String) session.getAttribute("tid");
        CourseDto courseDto = courseService.queryCourseDetail(tid,cid);
        if(courseDto == null){
            return new ModelAndView("redirect:/error");
        }


        //#finished 该课程的所有题库（id + 名字 + 单选数量 + 多选数量）
        List<LibBriefDto> libBriefDtos = questionFeign.querySimpleBankList(cid);
        //还是没有数据啊，我先写个假的自己用用
        //LibBriefDto[] libBriefDtos = new LibBriefDto[]{new LibBriefDto(), new LibBriefDto(), new LibBriefDto(), new LibBriefDto(), new LibBriefDto()};


        session.setAttribute("cid",cid);
        session.setAttribute("cName",courseDto.getName());
        ModelAndView view = new ModelAndView("create");
        view.addObject("cName", courseDto.getName());
        view.addObject("cid", cid);
        view.addObject("infos", JSON.toJSONString(libBriefDtos));

        return view;
    }

    /**
     * 题库列表页
     * @param request
     * @param cid
     * @return
     */
    @RequestMapping("libs")
    public ModelAndView toCourseLib(HttpServletRequest request, @RequestParam("cid")Integer cid) {
        HttpSession session = request.getSession();
        String tid = (String) session.getAttribute("tid");
        CourseDto courseDto = courseService.queryCourseDetail(tid,cid);
        if(courseDto == null){
            return new ModelAndView("redirect:/error");
        }

        //#unfinished 所有题库的id，用于后面的删除
        String[] lids = new String[]{"12", "22", "32"};

        session.setAttribute("cid",cid);
        session.setAttribute("cName",courseDto.getName());
        //finished 该课程题库总数
        int libCount = questionFeign.queryBankCount(cid);
        //finished 该课程下的题库，显示第0页也就是前五个
        List<LibDto> libDtos = questionFeign.queryBankList(cid,0, SystemConst.PAGE_SIZE);

        ModelAndView view = new ModelAndView("libs");
        view.addObject("cid", cid);
        view.addObject("cName", courseDto.getName());
        view.addObject("infos", JSON.toJSONString(libDtos));
        view.addObject("total", libCount);
        view.addObject("lids", JSON.toJSONString(lids));
        return view;
    }

    // -----------------------------------------我是分界线----------------------------------------
    // 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
    // -----------------------------------------我是分界线----------------------------------------

    // 翻页
    @RequestMapping("courseList")
    public void examList(HttpServletRequest request, HttpServletResponse response) {
        int page = Integer.parseInt(request.getParameter("page"));
        HttpSession session = request.getSession();
        String tid = (String) session.getAttribute("tid");
        // #finished 每页是5个，你们看着返回，从第0页开始，页数超过了最大页数（异常情况）也返回最后一页（虽然一般不会发生。
        List<CourseDto> courseDtos = courseService.queryCourseList(tid,page, SystemConst.PAGE_SIZE);

        Result r = Result.success().withData(courseDtos).code("0");
        Result.sendResult(response, r);
    }
}
