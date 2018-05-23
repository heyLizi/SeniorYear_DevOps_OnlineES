package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.LoginService;
import dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * @author danian
 * 定义了教师登录界面的按钮调用的方法
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * 注意：除了教师id可以用session。getAttribute("tid")获得，其他查询要用的id也都提供了
 * */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

    /**
     * login 教师登录界面
     * @param request
     * @return
     */
	@RequestMapping(value = "login",method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		//使用session判断是否已经登录
		HttpSession session = request.getSession(false);
	    if(session==null||session.getAttribute("tid")==null)  {
	    	ModelAndView view = new ModelAndView("login");
	    	Cookie[] cookies = request.getCookies();
	    	if(cookies != null && cookies.length > 0) {
	    		view.addObject("name", cookies[0].getValue());
	    	}
	    	view.addObject("fail", request.getParameter("fail"));
	    	return view;
	    } 
		//已经登录,跳转到登录后界面
	    return new ModelAndView("redirect:/exams");
	}

    /**
     * register 注册-跳转到登录界面
     * @return
     */
	@RequestMapping(value = "register",method = RequestMethod.GET)
	public String register() {
		//由于暂时不提供老师登录功能,直接调回登录界面。#todo
		return "redirect:/login";
	}

    /**
     * action 教师登录注册动作
     * 登录请求：type:类型 name:用户名 这里是老师工号 password：密码
     * @param request
     * @param response 用户ID和用户信息在session中
     * @return
     */
	@RequestMapping(value = "action",method = RequestMethod.POST)
	public String action(HttpServletRequest request, HttpServletResponse response){
		//判断是登录还是注册
		String type = request.getParameter("type");
		if(type.equals("register")) {
			//暂时无处理
			return "redirect:/login";
		} else if(type.equals("login")) {
			//正常登录动作，name在这里是教师的唯一用户名-即对应的工号
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			
			//finished 调用service查询用户名或密码是否正确
			TeacherDto teacherDto = loginService.loginForTeacher(name, password);//用户名密码正确就饿返回true，错误放回false，findTeacher这个名字我乱写的，你们自己另外用合适的吧，这一句注释之后也可以删了


            if(teacherDto != null) {
                String tid = String.valueOf(teacherDto.getId());
				//先创建session
				HttpSession newSession = request.getSession();
				//#test
				newSession.setAttribute("tid", tid);

				newSession.setAttribute("teacher",teacherDto);
				// 清空cookie
				Cookie[] cookies = request.getCookies();
				try {
					for (Cookie cookie : cookies) {
                        if(cookie.getName().equals("name")){
                            cookie.setValue(null);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
					}
				} catch (Exception ex) {
				}

				//创建cookie
				Cookie cookie = new Cookie("name",name);//用户ID 
				cookie.setMaxAge(60*60*24*365);//cookie时间
				response.addCookie(cookie);
				//登陆成功，跳转到exams界面
				return "redirect:/exams";
			}
		}
		return "redirect:/login?fail=1";
	}
}
