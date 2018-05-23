package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.ExamService;
import com.alibaba.fastjson.JSON;
import com.netflix.discovery.util.StringUtil;
import common.Result;
import dto.ExamDto;
import dto.ExamExerciseDto;
import dto.ExamResultDto;
import entity.Exam;
import entity.ExamExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import param.FinishParam;
import param.MarkParam;
import param.SelectParam;

import javax.print.attribute.standard.Finishings;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类说明：考试Controller
 * 创建者：zs
 * 创建时间：2017/12/12 上午9:51
 * 包名：cn.edu.nju.software.controller
 */
@RestController
@RequestMapping("exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping(value = "count")
    public Result queryExamCount(@RequestParam("tid") String tid){
        int count = examService.queryExamCount(tid);
        return Result.success().withData(count);
    }


    @GetMapping(value = "list")
    public Result queryExamList(@RequestParam("tid")String tid, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize){
        List<ExamDto> examDtos = examService.queryExamList(tid,pageIndex,pageSize);
        if(examDtos == null || examDtos.isEmpty()){
            return Result.error().errorMessage("查询列表为空");
        }
        return Result.success().withData(JSON.toJSONString(examDtos));
    }

    @GetMapping(value = "result")
    public Result queryExamResult(@RequestParam("eid") Integer eid){
        ExamResultDto examResultDto = examService.queryExamResult(eid);
        return Result.success().withData(JSON.toJSONString(examResultDto));
    }


    @GetMapping(value = "info/{eid}")
    public Result getExamInfo(@PathVariable(name = "eid") Long eid){
        return examService.getExam(eid);
    }

    @GetMapping(value = "paper")
    public Result getExamPaper(@RequestParam(name = "sid") Long sid,@RequestParam(name = "eid") Long eid){
        return examService.getExamPaper(sid,eid);
    }

    @GetMapping(value = "exercise")
    public Result getExamPaperExercise(@RequestParam(name="exam_exercise_id") Long id){
        return examService.getExercise(id);
    }

    /**
     * 考生标记了某题目后保存该状态
     * @param param
     * @return
     */
    @PostMapping(value = "mark")
    public Result markQuestion(@RequestBody MarkParam param){
        if(StringUtils.isEmpty(param.getPaperId())){
            Long papaerId = examService.getPaperId(param.getStudentId(),param.getExamId());
            param.setPaperId(papaerId);
        }
        if(StringUtils.isEmpty(param.getPaperId())||StringUtils.isEmpty(param.getExerciseId())){
            return Result.error().errorMessage("试卷信息或者信息丢失，标记失败！");
        }
        return examService.mark(param.getPaperId(),param.getExerciseId(),param.getState());
    }

    /**
     * 考生提交了某题目后保存该题目的选择
     * @param param
     * @return
     */
    @PostMapping(value = "select")
    public Result selectQuestion(@RequestBody SelectParam param){
        if(StringUtils.isEmpty(param.getPaperId())){
            Long papaerId = examService.getPaperId(param.getStudentId(),param.getExamId());
            param.setPaperId(papaerId);
        }
        if(StringUtils.isEmpty(param.getPaperId())||StringUtils.isEmpty(param.getExerciseId())){
            return Result.error().errorMessage("试卷信息或者信息丢失，保存本题失败！");
        }
        return  examService.select(param.getPaperId(),param.getExerciseId(),param.getStudentAnswers());
    }

    /**
     * 考生结束考试，存在两种情况
     * 考试到期自动finish
     * 考试
     * @param param
     * @return
     */
    @PostMapping(value = "finish")
    public Result finish(@RequestBody FinishParam param){
        Long sid = param.getSid();
        Long eid = param.getEid();
        return examService.finishExam(sid,eid);

    }

    @GetMapping(value = "score")
    public Result finish(@RequestParam("sid") Long sid,@RequestParam("eid") Long eid){
        //finished
        return examService.getExamScore(sid,eid);
        //return Result.error().errorMessage("还未完成/exam/score");
    }
    @GetMapping(value = "student")
    public Result getExamStudent(@RequestParam("sid") Long sid,@RequestParam("eid") Long eid){
        return examService.getExamStudent(sid,eid);
    }

}
