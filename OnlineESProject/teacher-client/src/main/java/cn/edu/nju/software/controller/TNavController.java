package cn.edu.nju.software.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.edu.nju.software.feign.ExamFeign;
import cn.edu.nju.software.service.CourseService;
import com.alibaba.fastjson.JSON;
import common.Result;
import common.SystemConst;
import dto.CourseDto;
import dto.ExamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author danian
 * 定义了教师界面的导航栏的跳转方法
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * */
@Controller
public class TNavController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private ExamFeign examFeign;

	//home 跳转到首页，在这里就是考试列表界面, 以后可能真的加个首页什么的 #todo
	@RequestMapping("home") 
	public String toHome() {
		return "redirect:/exams";
	}

	/**
     * finished
	 * libs 跳转到课程列表界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "courses",method = RequestMethod.GET)
	public ModelAndView courseList(HttpServletRequest request){
		HttpSession session = request.getSession();
		String tid = (String) session.getAttribute("tid");

		//#finished 需要知道这个老师的课程总数
		int courseCount = courseService.queryCourseCount(tid);
		//#finished 该教师的课程列表，返回第一页也就是前五个
		List<CourseDto> courseDtos = courseService.queryCourseList(tid,0, SystemConst.PAGE_SIZE);
		ModelAndView m = new ModelAndView("courses");
		m.addObject("infos", JSON.toJSONString(courseDtos));
		m.addObject("total", courseCount);
		return m;
	}

/*
	///**
    // * finished
	// * 分页查询数据
	// * @param request
	// * @return
	//
	@ResponseBody
	@RequestMapping(value = "course/page",method = RequestMethod.GET)
	public String courseListPage(HttpServletRequest request, @RequestParam("pageIndex")Integer pageIndex,@RequestParam("pageSize")Integer pageSize){
		HttpSession session = request.getSession();
		String tid = (String) session.getAttribute("tid");
		if(pageIndex < 0 || pageSize < 1){
			return JSON.toJSONString(Result.error().errorMessage("参数错误！"));
		}
		//#finished 该教师的课程列表，返回第一页也就是前四个
		List<CourseDto> courseDtos = courseService.queryCourseList(tid,pageIndex,pageSize);
		Result result = Result.success().withData(courseDtos);
		return JSON.toJSONString(result);
	}

*/

	/**
	 * exams 跳转到考试列表界面
	 * @param request
	 * @return
	 */
	@RequestMapping("exams")
	public ModelAndView toExams(HttpServletRequest request) {
		//教师名在session里
		HttpSession session = request.getSession();
		String tid = (String) session.getAttribute("tid");
        Integer teacher = Integer.parseInt(tid);

		//#finished 需要知道这个老师的exam的总数，因为地下的选页要根据这个来决定显示几个按钮
		Result result = examFeign.queryExamCount(teacher);
		if(!result.isSuccess()){
            ModelAndView eView = new ModelAndView("error");
            return eView;
		}
		int total = (int) result.getData();
		//#finished 考试信息列表,返回第一页的5个（也就是前五个）
		result = examFeign.queryExamList(teacher,0,SystemConst.PAGE_SIZE);
		List<ExamDto> examDtos = JSON.parseArray((String)result.getData(),ExamDto.class);

		//构造结果
		ModelAndView view = new ModelAndView("exams");
		view.addObject("info", JSON.toJSONString(examDtos));
		view.addObject("total", total);
		return view;
	}

	/**
	 * login 点击退出时跳转到登录按钮
	 * @param request
	 * @return
	 */
	@RequestMapping("quit") 
	public String quit(HttpServletRequest request){
		//删除session并跳转到登录界面
		HttpSession session = request.getSession();
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/login";
	}
}
