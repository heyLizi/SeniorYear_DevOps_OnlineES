package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.QuestionFeign;
import cn.edu.nju.software.service.CourseService;
import com.alibaba.fastjson.JSON;
import common.Result;
import dto.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author danian
 * 定义了教师的某个课程新增题库界面
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */
 @Controller
public class LibAddController {


	@Autowired
	private QuestionFeign questionFeign;


	// -----------------------------------------我是分界线----------------------------------------
	// 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
	// -----------------------------------------我是分界线----------------------------------------

	/**
     * finished
     * 新建题库，提交数据
     * @param cid
     * @param name
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "libAdd",method = RequestMethod.POST)
	public String addLib(@RequestParam("cid") Integer cid,@RequestParam("name") String name) {
		int id = questionFeign.addBank(cid,name);
		return JSON.toJSONString(Result.success().withData(id));
	}

}
