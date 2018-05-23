package cn.edu.nju.software.controller.basicinfo;

import cn.edu.nju.software.service.BasicInfoService;
import common.Result;
import dto.ClassDto;
import dto.InstitutionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mengf on 2017/12/7 0007.
 */
@RestController
@RequestMapping("institutions")
public class InstitutionController {
	
    @Autowired
    private BasicInfoService service;

    @GetMapping("/list/{schoolId}")
    public Result getList(@PathVariable Long schoolId){
        List<InstitutionDto> institutions = service.getInstitutions(schoolId);
        return Result.success().message("按照学校ID获取学院列表成功！").withData(institutions);
    }
    
}
