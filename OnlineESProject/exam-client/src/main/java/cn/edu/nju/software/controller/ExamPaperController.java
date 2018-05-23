package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.PaperService;
import com.alibaba.fastjson.JSON;
import common.Result;
import dto.QuestionDto;
import dto.StudentExamDetailDto;
import dto.StudentExamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类说明：试卷相关Controller
 * 创建者：zs
 * 创建时间：2017/12/12 下午2:32
 * 包名：cn.edu.nju.software.controller
 */

@RestController
@RequestMapping("paper")
public class ExamPaperController {

    @Autowired
    private PaperService paperService;

    @GetMapping(value = "list")
    public Result queryExamStudentList(@RequestParam("eid") Integer eid, @RequestParam("pageIndex") Integer pageIndex,
                                       @RequestParam("pageSize") Integer pageSize){
        List<StudentExamDto> studentExamDtos = paperService.queryExamStudentList(eid,pageIndex,pageSize);
        return Result.success().withData(JSON.toJSONString(studentExamDtos));
    }

    @GetMapping(value = "detail")
    public Result queryExamDetail(@RequestParam("epid") Integer epid){
        StudentExamDetailDto detailDto = paperService.queryExamDetail(epid);
        return Result.success().withData(JSON.toJSONString(detailDto));
    }

    @GetMapping(value = "questions")
    public Result queryExamQuestionList(@RequestParam("epid") Integer epid,@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize){
        List<QuestionDto> studentExamDtos = paperService.queryExamQuestionList(epid,pageIndex,pageSize);
        return Result.success().withData(JSON.toJSONString(studentExamDtos));
    }

    @GetMapping(value = "sids")
    public Result queryExamStudentIDList(@RequestParam("eid") Integer eid){
        List<String> sids = paperService.queryExamStudentIDList(eid);
        return Result.success().withData(JSON.toJSONString(sids));
    }

    @GetMapping(value = "students")
    public Result queryPaperList(@RequestParam("eid") Integer eid, @RequestParam("sidStr") String sidStr){
        String[] sids = sidStr.split(" ");
        List<Integer> epids = paperService.queryIdList(eid,sids);
        return Result.success().withData(JSON.toJSONString(epids));
    }

}
