package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.ExamService;
import common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import param.CreateExamParam;

/**
 * 类说明：创建考试服务
 * 创建者：zs
 * 创建时间：2017/12/10 下午12:14
 * 包名：cn.edu.nju.software.controller
 */

@RestController
@RequestMapping("create")
public class ExamCreateController {

    @Autowired
    private ExamService examService;

    @PostMapping(value = "add")
    public Result createExam(@RequestBody CreateExamParam param){
        return Result.success().code("0").withData(examService.createExam(param));
    }
}
