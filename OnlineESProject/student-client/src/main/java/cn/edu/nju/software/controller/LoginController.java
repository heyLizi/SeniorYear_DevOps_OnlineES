package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.AccountService;
import cn.edu.nju.software.service.MailService;
import common.Result;
import entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * @author danian
 * 定义了学生的登录和注册界面的按钮调用的方法（包括跳转到登录界面）
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */
@Controller
public class LoginController {
	
	@Autowired
    private AccountService accountService;
	@Autowired
	private MailService mailService;

    //login 跳转到登录界面(学生不需要登录，因此什么都没有#todo)
    @RequestMapping(value="login", method=RequestMethod.GET)
    public String login(HttpServletRequest request) {
    	/*
        //判断是否已经登录
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if(session != null && session.getAttribute("login") != null) {
            return "redirect:/action?type=login";
        } 
        else {
            //session已失效或者非法访问，跳到登录界面
            ModelAndView m = new ModelAndView("login");
            Cookie[] cookies = request.getCookies();
            
            //增加对cookies是否为空的判断，否则会因为cookies为null而返回status=500的错误
            if(cookies != null && cookies.length > 0) {
                m.addObject("name", cookies[0].getValue());
            }

            m.addObject("fail", request.getParameter("fail"));

            return "login";
        }*/
    	
    	//暂时返回一个错误界面
    	return "error";
    }

    //register 跳转到注册界面
    @RequestMapping(value="register", method=RequestMethod.GET)
    public String register(HttpServletRequest request) {
        return "register";
    }

    //activate 跳转到激活界面
    @RequestMapping(value="activate", method=RequestMethod.GET)
    public String activate(HttpServletRequest request) {
        return "activate";
    }

    //welcome 跳转到欢迎界面
    @RequestMapping(value="welcome", method=RequestMethod.GET)
    public String welcome(HttpServletRequest request) {
        return "welcome";
    }
    
    
    //登录操作（学生不需要登录，因此什么都没有#todo）
    @RequestMapping(value="loginAction", method=RequestMethod.POST)
    public void loginAction(HttpServletRequest request, HttpServletResponse response) {
    	/*
    	//判断是否已经登录
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if(session != null && session.getAttribute("login") != null) {
            //已经登录
            return new ModelAndView("error");
        } 
        else {
            //正常登录动作
            String name = request.getParameter("name");
            String password = request.getParameter("password");

            //#unfinished 调用service查询用户名或密码是否正确
            boolean checked = true; //应该改成:boolean checked = xxxService.findStudent(name, password);用户名密码正确就饿返回true，错误放回false，findStudent这个名字我乱写的，你们自己另外用合适的吧，这一句注释之后也可以删了

            if(checked) {
                //先创建session
                HttpSession newSession = request.getSession();

                //#unfinished 学生唯一id
                String sid = "1231423";

                newSession.setAttribute("login", sid);
                // 清空cookie
                Cookie[] cookies = request.getCookies();
                try {
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie cookie = new Cookie("name", null);
                        cookie.setMaxAge(0);
                        //cookie.setPath("/");//根据你创建cookie的路径进行填写
                        response.addCookie(cookie);
                    }
                } catch (Exception ex) {
                }
            }
        }    
		*/
    }
    
    //注册操作，将注册信息返回原界面，成功则由原界面完成跳转，失败则提示失败信息
    @RequestMapping(value="registerAction", method=RequestMethod.POST)
    public void registerAction(HttpServletRequest request, HttpServletResponse response) {
	    
    	Long schoolId = Long.parseLong(request.getParameter("school"));				//学校编号
	    Long institutionId = Long.parseLong(request.getParameter("institution"));	//学院编号
	    Long gradeId = Long.parseLong(request.getParameter("grade"));				//年级编号
	    Long classId = Long.parseLong(request.getParameter("class"));				//班级编号
	    String id = request.getParameter("id");				//学号
	    String name = request.getParameter("name");			//姓名
	    String mailbox = request.getParameter("mailbox");	//邮箱
	    String password = request.getParameter("password");	//密码
	
	    System.out.println("Register: "+schoolId+"   "+institutionId+"   "+gradeId+"   "+classId+"   "+id+"   "+name+"   "+mailbox+"   "+password);
	    
	    //生成一个学生对象
	    Student stu = new Student();
	    stu.setSchoolId(schoolId);
	    stu.setInstitutionId(institutionId);
	    stu.setGradeId(gradeId);
	    stu.setClassId(classId);
	    stu.setNumber(id);
	    stu.setName(name);
	    stu.setMail(mailbox);
	    stu.setPassword(password);
	    
	    //注册学生对象，返回注册结果
	    Result registerResult = accountService.register(stu);
	    //将注册结果写回到注册界面中，让前台进行调整
	    Result.sendResult(response, registerResult);
    	
    	//以下为桩代码，测试激活界面的各种不同情况
    	/*
    	Student stubStu = new Student();
    	stubStu.setId((long) 1);
    	stubStu.setMail("stub@stub.com");
    	
    	Result registerResult = new Result();
    	registerResult.setSuccess(true);
    	registerResult.setData(stubStu);
    	
    	Result.sendResult(response, registerResult);
    	*/
    	
    	//如果注册成功
	    if(registerResult.isSuccess()) {
    	
	    	//获得注册产生的、每一个学生独有的sid的值
	    	Student registerStu = (Student)registerResult.getData();
	    	Long registerId = registerStu.getId();
	    	String email = registerStu.getMail();
	    	
	    	//创建一个session来保存sid的值，后续界面可能用到
	    	HttpSession session = request.getSession(false);
	    	//System.out.println("StudentClient -- Session is new? : "+session.isNew()); //这里不出意外的话，session应该不是new的
	    	session.setAttribute("sid", registerId);
	    	session.setAttribute("email", email);
	    	
	    	//向该学生的注册邮箱发送邮件
	    	//考虑一个可能情况：用户注册成功，但是发邮件不成功。这样进入激活界面后，用户既无法输入激活码激活，又无法回到注册界面继续注册
	    	//因此在激活界面中加一个“重新发送验证码功能”
	    	Result mailResult = mailService.sendVerifyCode(registerId, email);
	    }
    	
	} 
    
    //激活操作，将激活信息返回原界面，成功则由原界面完成跳转，失败则提示失败信息
    @RequestMapping(value="activateAction", method=RequestMethod.POST)
    public void activateAction(HttpServletRequest request, HttpServletResponse response) {
    	
    	//通过获取session来进一步获取session的attribute的值
    	HttpSession session = request.getSession(false);
    	
        String sid = session.getAttribute("sid").toString();		//学生的唯一id
        String activateCode = request.getParameter("activateCode");	//输入的验证码
        System.out.println(sid+"    "+activateCode);
        
        Result activateResult = accountService.checkActive(Long.parseLong(sid), activateCode);
        Result.sendResult(response, activateResult);
        
        //以下为桩代码
        //Result activateResult = Result.success();
        //Result.sendResult(response, activateResult);
       
    }

   //重新发送验证码操作，将发送信息返回原界面，成功则提示成功信息，失败则提示失败信息
    @RequestMapping(value="resendAction", method=RequestMethod.POST)
    public void resendAction(HttpServletRequest request, HttpServletResponse response) {

    	//通过获取session来进一步获取session的attribute的值
    	HttpSession session = request.getSession(false);
    	
        Long sid = Long.parseLong(session.getAttribute("sid").toString());		//学生的唯一id
        String email = session.getAttribute("email").toString();	//输入的验证码
        System.out.println(sid+"    "+email);
        
        Result resendResult = mailService.sendVerifyCode(sid, email);
        Result.sendResult(response, resendResult);
        
        //以下为桩代码，测试激活界面的各种不同情况
        //Result resendResult = Result.success();
        //Result.sendResult(response, resendResult);
       
    }
    
    
    // -----------------------------------------我是分界线----------------------------------------
    // 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
    // -----------------------------------------我是分界线----------------------------------------

    //注册的信息查询
    // query?type=school 返回所有学校
    // query?type=key&key=xxx 返回名字中包含xxx的所有学校
    // query?type=institution&schoole=xxx 返回xxx学校的所有学院
    // query?type=grade&school=xxx&institution=yyy 返回xxx学校yyy学院的所有年级
    // query?type=class&school=xxx&institution=yyy&grade=zzz 返回xxx学校yyy学院zzz年级的所有班级
    @RequestMapping("query")
    public void query(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	
    	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("utf-8");
    	
        String type = request.getParameter("type");
        if(type == null)
        	System.out.println("NULL");
        else
        	System.out.println(type);

        Result result = null;
        String[] resultStrArr = new String[]{};
        
        if(type == null) {
        	result = Result.error().errorMessage("木有").code("1");
        }
         

        if(type.equals("school")) {
            //#unfinished 返回学校列表
            resultStrArr = new String[]{"南京大学","南昌大学","北京大学","test1","test2"};
        } 
        else if(type.equals("institution")) {
            //学校名字
            String school = request.getParameter("school");
            //#unfinished 返回某学校的学院列表
            resultStrArr = new String[]{"软件学院","计科"};
        } 
        else if(type.equals("grade")) {
            //学校名字
            String school = request.getParameter("school");
            //学院名字
            String institution = request.getParameter("institution");
            //#unfinished 返回某学校某学院的年级列表
            resultStrArr = new String[]{"大一","大二","大三","大四"};
        } 
        else if(type.equals("class")) {
            //学校名字
            String school = request.getParameter("school");
            //学院名字
            String institution = request.getParameter("institution");
            //年级
            String grade = request.getParameter("grade");
            //#unfinished 返回某学校某学院某年级的班级列表
            resultStrArr = new String[]{"一班","二班","三班","xx班"};
        }

        if(resultStrArr.length == 0) {
            //发生错误
            result = Result.error().errorMessage("查询出错或者该条目数为0").code("1");
        } 
        else {
            result = Result.success().withData(resultStrArr).message("查询成功").code("0");
        }

        Result.sendResult(response, result);
    }


    //注册的格式查询
    // format?type=id&id=xxxx 学号xxxx格式是否正确（格式，是否已经注册）
    // format?type=name&name=xxxx 姓名xxxx格式是否正确
    // format?type=mailbox&address=xxxx 邮箱xxxx格式是否正确（格式，唯一性）
    // format?type=password&password=xxxx 密码xxxx格式是否正确
    // format?type=cpass&password=xxxx&cpassword=xxxx 两个密码是否相同
    @RequestMapping("format")
    public void format(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        boolean result = true;
        boolean validate = true;
        String message = "";

        if(type.equals("id")) {
            //学号
            String id = request.getParameter("id");

            //#unfinished id格式是否正确（格式，是否已经注册）,不正确的原意写在message
            result = true;
            message = ""; //不正确的时候才把原因写在message里，用作提示用户

        } else if(type.equals("name")) {
            //姓名
            String name = request.getParameter("name");

            //#unfinished name格式是否正确
            result = true;
            message = ""; //不正确的时候才把原因写在message里，用作提示用户

        } else if(type.equals("mailbox")) {
            //邮箱
            String mailbox = request.getParameter("mailbox");

            //#unfinished mailbox格式是否正确（格式，是否已经注册）
            result = false;
            message = ""; //不正确的时候才把原因写在message里，用作提示用户

        } else if (type.equals("password")) {
            //密码1
            String password = request.getParameter("password");

            //#unfinished 密码格式是否正确
            result = true;
            message = ""; //不正确的时候才把原因写在message里，用作提示用户

        } else if(type.equals("cpass")) {
            //密码1
            String password = request.getParameter("password");

            //密码2
            String cpassword = request.getParameter("cpassword");

            //#unfinished 两次密码是否一样
            result = true;
            message = ""; //不正确的时候才把原因写在message里，用作提示用户
        } else {
            validate = false;
        }

        Result r = null;

        if(!validate) {
            r = Result.error().errorMessage("错误的格式验证类型").code("1");
        } else {
            r = Result.success().message(message).withData(result).code("0");
        }

        Result.sendResult(response, r);

    }

}
