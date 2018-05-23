package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.ImportFeign;
import cn.edu.nju.software.service.FileService;
import com.alibaba.fastjson.JSON;
import common.Result;
import common.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author danian
 * 定义了教师的某个课程的某题库的上传试题界面
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */

@Controller
public class LibQAddController {

    @Autowired
    private FileService fileService;
    @Autowired
    private ImportFeign importFeign;
    @Value("${file.path}")
    private String filePath;


	// -----------------------------------------我是分界线----------------------------------------
	// 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
	// -----------------------------------------我是分界线----------------------------------------

    /**
     * finished
     * 下载excel试题模板
     * @param response
     */
	@RequestMapping(value = "questionTemplate")
	public void questionTemplate(HttpServletResponse response) {
        String filename="exercise_template.xlsx";
        String filePath = "file";
        fileService.downloadTemplate(response,filePath,filename);
	}

    /**
     * finished
     * questionUpload 上传试题文件
     * @param file
     * @param lid 题库id
     * @return
     */
    @ResponseBody
	@RequestMapping(value="questionUpload", method = RequestMethod.POST)
    public String questionUpload(@RequestParam("file") MultipartFile file,@RequestParam("lid")Integer lid) {
        String message = fileService.uploadExcel(file,filePath);
        if(!message.startsWith("success+")){
            return message;
        }
        //试题文件路径
		String path = message.replace("success+","");
        //导入模块进行处理
        Result result = importFeign.importQuestionExcel(lid,path);
		return JSON.toJSONString(result);
    }

}
