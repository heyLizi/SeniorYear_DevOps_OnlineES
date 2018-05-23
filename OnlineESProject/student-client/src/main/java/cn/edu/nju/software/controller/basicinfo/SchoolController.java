package cn.edu.nju.software.controller.basicinfo;

import cn.edu.nju.software.service.BasicInfoService;
import common.Result;
import dto.ClassDto;
import dto.SchoolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by mengf on 2017/12/7 0007.
 */	
@RestController
@RequestMapping("schools")
public class SchoolController {

    @Autowired
    private BasicInfoService service;

    @GetMapping("/list")
    public Result getList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        List<SchoolDto> schools = service.getSchools();
        return Result.success().message("获取学校列表成功！").withData(schools);
    }
}
