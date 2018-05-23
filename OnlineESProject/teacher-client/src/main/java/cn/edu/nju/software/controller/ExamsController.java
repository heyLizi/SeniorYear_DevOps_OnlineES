package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.ExamFeign;
import com.alibaba.fastjson.JSON;
import common.Result;
import common.SystemConst;
import dto.ExamDto;
import dto.ExamResultDto;
import dto.StudentExamDto;
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
 * 定义了教师的考试列表界面的按钮调用的方法
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */

@Controller
public class ExamsController {

	@Autowired
	private ExamFeign examFeign;

	//exam 跳转到考试详细信息界面
	@RequestMapping("exam") 
	public ModelAndView examStudent(@RequestParam("eid")Integer eid) {

		//#finished 该场考试的详细信息
        Result result = examFeign.queryExamResult(eid);
		ExamResultDto info = JSON.parseObject((String)result.getData(),ExamResultDto.class);

		//#finished 参加考试的学生信息，返回第0页也就是前12个
		result = examFeign.queryExamStudentList(eid,0, SystemConst.STUDENT_PAGE_SIZE);
		List<StudentExamDto> infos = JSON.parseArray((String)result.getData(),StudentExamDto.class);

		//#finished 学生ID
		result = examFeign.queryExamStudentIDList(eid);
        List<String> ids = JSON.parseArray((String) result.getData(),String.class);

		ModelAndView m = new ModelAndView("exam");
		m.addObject("info", JSON.toJSONString(info));
		m.addObject("infos", JSON.toJSONString(infos));
		m.addObject("ids", JSON.toJSONString(ids));

		return m;
	}

	// -----------------------------------------我是分界线----------------------------------------
	// 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
	// -----------------------------------------我是分界线----------------------------------------


	/**
	 * 考试翻页
	 * @param request
	 * @param pageIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "examList",method = RequestMethod.GET)
	public String examList(HttpServletRequest request, @RequestParam("page")Integer pageIndex) {
		HttpSession session = request.getSession();
		String tid = (String) session.getAttribute("tid");
		Integer teacher = Integer.parseInt(tid);
		if(pageIndex < 0){
			return JSON.toJSONString(Result.error().errorMessage("参数错误！"));
		}
		//#finished 该教师的考试列表， 每页是5个
		Result result = examFeign.queryExamList(teacher,pageIndex, SystemConst.PAGE_SIZE);
		return JSON.toJSONString(result);
	}

}
