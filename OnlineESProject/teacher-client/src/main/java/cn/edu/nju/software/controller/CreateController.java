package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.EmailFeign;
import cn.edu.nju.software.feign.ExamFeign;
import cn.edu.nju.software.feign.ImportFeign;
import cn.edu.nju.software.service.FileService;
import cn.edu.nju.software.service.MailService;
import cn.edu.nju.software.service.StudentService;
import com.alibaba.fastjson.JSON;
import common.Result;
import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import param.CreateExamParam;
import param.MailParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author danian
 * 定义了教师的创建考试
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */
@Controller
public class CreateController {

    @Autowired
    private FileService fileService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private MailService mainService;

	@Autowired
	private ImportFeign importFeign;
	@Autowired
	private ExamFeign examFeign;
    @Autowired
    private EmailFeign emailFeign;
	@Value("${file.path}")
	private String filePath;


	// -----------------------------------------我是分界线----------------------------------------
	// 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
	// -----------------------------------------我是分界线----------------------------------------

	/**
	 * 下载学生模板
	 * @param response
	 * @return
	 */
	@RequestMapping(value="createTemplate",method = RequestMethod.GET)
	public void downloadStudentTemplate(HttpServletResponse response){
		String filename="student_template.xlsx";
		String filePath = "file";
		fileService.downloadTemplate(response,filePath,filename);
	}

	//上传人员名单
	@RequestMapping(value="createUpload", method = RequestMethod.POST)
    @ResponseBody

    public void uploadStudents(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		String message = fileService.uploadExcel(file, filePath);
		System.out.println(message);
		if(!message.startsWith("success+")){
			Result.send(response, message);
			return;
		}
		//试题文件路径
		String path = message.replace("success+","");
        HttpSession session = request.getSession();
        session.setAttribute("studentPath", path);
		Result.sendResult(response, Result.success());
    }
	
	//创建考试
    @ResponseBody
	@RequestMapping(value = "createAction",method = RequestMethod.POST)
	public String createExam( HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		String eName = request.getParameter("eName");
		String sTime = request.getParameter("sTime");
		String eTime = request.getParameter("eTime");

		int singleNumV = Integer.parseInt(request.getParameter("singleNum"));
		int singleScoreV = Integer.parseInt(request.getParameter("singleScore"));
		int multiNumV = Integer.parseInt(request.getParameter("multiNum"));
		int multiScoreV = Integer.parseInt(request.getParameter("multiScore"));
		String ids = request.getParameter("ids");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date sD = null;
		Date eD = null;
		try {
			sD = sdf.parse(sTime);
			eD = sdf.parse(eTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long from = sD.getTime();
		long to = eD.getTime();
		int minutes = (int) ((to - from)/(1000 * 60));

		if(ids == null || ids.equals("")){
			return JSON.toJSONString(Result.error().errorMessage("题库不能为空"));
		}
		if(minutes < 0){
			return JSON.toJSONString(Result.error().errorMessage("考试时间输入有误"));
		}

		CreateExamParam param = new CreateExamParam();
		param.setCid(cid);
		param.setName(eName);
		param.setStartTime(sD);
		param.setEndTime(eD);
		param.setSingleNum(singleNumV);
		param.setSingleScore(singleScoreV);
		param.setMultiNum(multiNumV);
		param.setMultiScore(multiScoreV);
		param.setPeriod(minutes);
		param.setLids(ids);


		//参考名单的存放路径,没有就返回false
		String path = (String) request.getSession().getAttribute("studentPath");
		if(path == null || path.equals("")) {
			return JSON.toJSONString(Result.error().errorMessage("请上传学生名单!"));
		}
		//导入学生
        Result result = importFeign.importStudentExcel(path);
		if(!result.isSuccess()){
		    Result.sendResult(response, result);
        }
        List<StudentUploadDto> studentDtos = JSON.parseArray((String)result.getData(),StudentUploadDto.class);
		if(studentDtos == null || studentDtos.size() == 0){
            return JSON.toJSONString(Result.error().errorMessage("请上传正确的学生名单!"));
        }
        System.out.println(JSON.toJSONString(studentDtos));
		List<StudentBriefDto> studentBriefDtos = studentService.queryStudentList(studentDtos);
		param.setStudentDtos(studentBriefDtos);
        //ID集合处理
		String tempString = param.getLids().replace("，",",");
		param.setLids(tempString);
		//创建考试+生成考试信息
        result = examFeign.createExam(param);
        if(!result.isSuccess()){
           Result.sendResult(response, result);
        }
        Integer eid = (Integer) result.getData();
        //发送邮件
        List<MailParam> mailParams = mainService.changeToParam(eid,param);
        emailFeign.sendMail(mailParams);
        request.getSession().removeAttribute("studentPath");
        return JSON.toJSONString(Result.success());
	}
	
	
	
}
