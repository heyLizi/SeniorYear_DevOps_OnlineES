package cn.edu.nju.software.controller;

import cn.edu.nju.software.feign.ExamFeign;
import cn.edu.nju.software.service.ExportService;
import cn.edu.nju.software.service.ZipService;
import com.alibaba.fastjson.JSON;
import common.Result;
import common.SystemConst;
import dto.ExamResultDto;
import dto.QuestionDto;
import dto.StudentExamDetailDto;
import dto.StudentExamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.DateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：导出模块
 * 创建者：zs
 * 创建时间：2017/12/14 上午10:05
 * 包名：cn.edu.nju.software.controller
 */

@RestController
@RequestMapping("export")
public class ExportController {

    @Autowired
    private ExportService exportService;
    @Autowired
    private ZipService zipService;
    @Autowired
    private ExamFeign examFeign;

    @GetMapping(value = "exam")
    public Result downloadExamFile(@RequestParam("epid") Integer epid, @RequestParam("type") Integer type){
        Result result = examFeign.queryExamDetail(epid);
        StudentExamDetailDto detailDto = JSON.parseObject((String) result.getData(),StudentExamDetailDto.class);
        int num = detailDto.getQuestionCount();
        result = examFeign.queryExamQuestionList(epid,0, num);
        List<QuestionDto> questionDtos = JSON.parseArray((String)result.getData(),QuestionDto.class);
        String path = exportService.exportExamFile(detailDto,questionDtos,type);
        if(path == null || path.equals("")){
            return Result.error().errorMessage("导出失败，请重试！");
        }
        return Result.success().withData(path);
    }

    @GetMapping(value = "report")
    public Result downloadExamReport(@RequestParam("eid") Integer eid){
        Result result = examFeign.queryExamResult(eid);
        ExamResultDto resultDto = JSON.parseObject((String)result.getData(),ExamResultDto.class);
        int num = resultDto.getTotalNum();
        result = examFeign.queryExamStudentList(eid,0, num);
        List<StudentExamDto> studentExamDtos = JSON.parseArray((String)result.getData(),StudentExamDto.class);
        String path = exportService.exportExamReport(resultDto,studentExamDtos);
        if(path == null || path.equals("")){
            return Result.error().errorMessage("导出失败，请重试！");
        }
        return Result.success().withData(path);
    }

    @GetMapping(value = "zip")
    public Result downloadExamZip(@RequestParam("eid") Integer eid,@RequestParam("sidStr")  String sidStr, @RequestParam("type") Integer type){
        String[] sids = sidStr.split(" ");
        //查询对应考生的试卷ID
        Result result = examFeign.queryPaperList(eid,sidStr);
        List<Integer> epids = JSON.parseArray((String) result.getData(),Integer.class);
        List<File> fileList = new ArrayList<>();
        String examName = "";
        for(Integer epid : epids){
            result = examFeign.queryExamDetail(epid);
            StudentExamDetailDto detailDto = JSON.parseObject((String) result.getData(),StudentExamDetailDto.class);
            examName = detailDto.getEName();
            int num = detailDto.getQuestionCount();
            result = examFeign.queryExamQuestionList(epid,0, num);
            List<QuestionDto> questionDtos = JSON.parseArray((String)result.getData(),QuestionDto.class);
            String path = exportService.exportExamFile(detailDto,questionDtos,type);
            if(path == null || path.equals("")){
                continue;
            }
            File file = new File(path);
            fileList.add(file);
        }
        String typeName = type == 0 ? "考卷" : "答卷";
        String fileName = examName+"_"+DateUtil.parseTodayToString()+"_"+typeName+".zip";
        String path = zipService.exportZip(fileName,fileList);
        return Result.success().withData(path);
    }


}
