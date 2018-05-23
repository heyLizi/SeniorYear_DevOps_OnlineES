package cn.edu.nju.software.controller.basicinfo;

import cn.edu.nju.software.service.BasicInfoService;
import common.Result;
import dto.ClassDto;
import dto.GradeDto;
import dto.InstitutionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mengf on 2017/12/7 0007.
 */
@RestController
@RequestMapping("grades")
public class GradeController {
	
    @Autowired
    private BasicInfoService service;

    @GetMapping("/list/{institutionId}")
    public Result getList(@PathVariable Long institutionId){
        List<GradeDto> grades = service.getGrades(institutionId);
        return Result.success().message("按照学院ID获取年级列表成功！").withData(grades);
    }
    
}
