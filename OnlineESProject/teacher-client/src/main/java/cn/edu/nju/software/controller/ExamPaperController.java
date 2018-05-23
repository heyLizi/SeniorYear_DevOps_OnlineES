package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.ExamFeign;
import cn.edu.nju.software.feign.ExportFeign;
import cn.edu.nju.software.service.FileService;
import com.alibaba.fastjson.JSON;
import common.Result;
import common.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author danian
 * 定义了教师的某场考试某个学生的考卷详情界面的按钮调用的方法
 * 未完成的方法，那一句上面的注释为  #unfinished
 * 完成的方法，注释内容改为  #finished
 * 下个版本拓展的方法，注释为 #todo
 * ajax方法已经标明,注意ajax一定要返回json格式字符串
 */
@Controller
public class ExamPaperController {

    @Autowired
    private ExamFeign examFeign;
    @Autowired
    private ExportFeign exportFeign;
    @Autowired
    private FileService fileService;


    // -----------------------------------------我是分界线----------------------------------------
    // 以下是 ajax方法 页面内部内容发生变化，不发生跳转时使用
    // -----------------------------------------我是分界线----------------------------------------

    // 翻页
    @ResponseBody
    @RequestMapping("questionList")
    public Result examList(@RequestParam("epid") Integer epid, @RequestParam("page") int page) {
        // #finished 每页是12个，你们看着返回，从第0页开始，页数超过了最大页数（异常情况）也返回最后一页（虽然一般不会发生。
        Result result = examFeign.queryExamQuestionList(epid, page, SystemConst.PAGE_SIZE);
        return result;
    }


    /**
     * 导出试卷
     *
     * @param type     0-考卷，1-答卷
     * @param epid     考卷ID
     * @param response
     */
    @RequestMapping("generateExam")
    public void generateExam(@RequestParam("type") Integer type, @RequestParam("epid") Integer epid, HttpServletResponse response) {
        //导出类型 考卷，答卷
        Result result = exportFeign.downloadExamFile(epid, type);
        if (result.isSuccess()) {
            String path = (String) result.getData();
            fileService.downloadFile(response, path,false);
        } else {
            Result.sendResult(response, result);
        }
    }
}