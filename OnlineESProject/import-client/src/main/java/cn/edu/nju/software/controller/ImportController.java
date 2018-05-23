package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.QuestionFeign;
import cn.edu.nju.software.service.ImportService;
import com.alibaba.fastjson.JSON;
import common.Result;
import dto.QuestionDto;
import dto.StudentUploadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import param.UploadQuestionParam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 导入Controller
 */
@RestController
@RequestMapping("import")
public class ImportController {

    @Autowired
    private ImportService importService;
    @Autowired
    private QuestionFeign questionFeign;


    @GetMapping(value = "student")
    public Result importStudentExcel(@RequestParam("path")String path){
        List<StudentUploadDto> uploadDtos = null;
        Result result = Result.error().errorMessage("上传失败！");
        try {
            uploadDtos = importService.importStudentExcel(path);
            if(uploadDtos!=null && !uploadDtos.isEmpty()){
                result = Result.success().withData(JSON.toJSONString(uploadDtos));
            }
        } catch (IOException e) {
            if(e instanceof FileNotFoundException){
                return Result.error().errorMessage("文件不存在！");
            }
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(value = "question")
    public Result importQuestionExcel(@RequestParam("lid") Integer lid, @RequestParam("path") String path){
        UploadQuestionParam param = new UploadQuestionParam();
        param.setLid(lid);
        List<QuestionDto> questionDtos = null;
        Result result = Result.error().errorMessage("上传失败！");
        try {
            questionDtos = importService.importQuestionExcel(path);
            if(questionDtos!=null && !questionDtos.isEmpty()){
                param.setQuestionDtos(questionDtos);
                result = questionFeign.insertQuestions(param);
            }
        } catch (IOException e) {
            if(e instanceof FileNotFoundException){
                return Result.error().errorMessage("文件不存在！");
            }
            e.printStackTrace();
        }
        return result;
    }
}
