package cn.edu.nju.software.controller;


import cn.edu.nju.software.feign.ExamFeign;
import cn.edu.nju.software.feign.ExportFeign;
import cn.edu.nju.software.service.FileService;
import com.alibaba.fastjson.JSON;
import common.Result;
import common.SystemConst;
import dto.ExamResultDto;
import dto.QuestionDto;
import dto.StudentExamDetailDto;
import dto.StudentExamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author danian
 * 定义了教师的考试详情界面的按钮调用的方法
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 * */

@Controller
public class ExamController {

	@Autowired
	private ExamFeign examFeign;
    @Autowired
    private ExportFeign exportFeign;
    @Autowired
    private FileService fileService;


	//exam 跳转到某学生考试详细信息界面-试卷ID：epid
	@RequestMapping(value = "paperDetail", method = RequestMethod.GET)
	public ModelAndView examDetail(@RequestParam("epid") Integer epid) {
		//#finished 该场考试该学生的考试详细信息
		Result result = examFeign.queryExamDetail(epid);
		StudentExamDetailDto info = JSON.parseObject((String)result.getData(),StudentExamDetailDto.class);

		//#finished 该场考试该学生题目信息，返回第0页也就是前5个
		result = examFeign.queryExamQuestionList(epid, 0, SystemConst.PAGE_SIZE);
		List<QuestionDto> infos = JSON.parseArray((String) result.getData(), QuestionDto.class);

		//#finished 该考试题目总数
		int num = info.getQuestionCount();
		ModelAndView view = new ModelAndView("studentDetail");
		view.addObject("info", JSON.toJSONString(info));
		view.addObject("num", num);
		view.addObject("infos", JSON.toJSONString(infos));
		return view;
	}


	
	// -----------------------------------------我是分界线----------------------------------------
	// 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
	// -----------------------------------------我是分界线----------------------------------------

	// 翻页
	@RequestMapping("studentList")
    @ResponseBody
    public Result studentList(@RequestParam("eid")Integer eid,@RequestParam("pageIndex")Integer pageIndex,@RequestParam("pageSize")Integer pageSize) {
        return examFeign.queryExamStudentList(eid,pageIndex, pageSize);
    }


    /**
     * 导出报告
     * @param type 0-成绩单，1-考卷，2-答卷
     * @param eid
     * @param sidStr
     * @param response
     * @return
     */
	@RequestMapping("generateReport")
	public void generateReport(@RequestParam("type")Integer type,@RequestParam("eid")Integer eid, @RequestParam("sidStr")String sidStr, HttpServletResponse response) {
		if(type ==null || type<0 || type>2){
            return;
        }
        Result result;
	    //导出类型 成绩单，考卷，答卷
		boolean isZip = true;
		if(type==0) {
			isZip = false;
            result = exportFeign.downloadExamReport(eid);
		} else{
			if(sidStr == null || sidStr.equals("")){
				return;
			}
			result = exportFeign.downloadExamZip(eid,sidStr,type-1);
		}
		if(result!= null && result.isSuccess()){
		    String path = (String) result.getData();
		    fileService.downloadFile(response,path,isZip);
		}
	}

}
