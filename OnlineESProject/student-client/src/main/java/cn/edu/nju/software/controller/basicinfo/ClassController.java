package cn.edu.nju.software.controller.basicinfo;

import cn.edu.nju.software.service.BasicInfoService;
import common.Result;
import dto.ClassDto;
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
@RequestMapping("classes")
public class ClassController {
    
	@Autowired
    private BasicInfoService service;

    @GetMapping("/list/{gradeId}")
    public Result getList(@PathVariable Long gradeId){
        List<ClassDto> classes = service.getClasses(gradeId);
        return Result.success().message("按照年级ID获取班级列表成功！").withData(classes);
    }
}
