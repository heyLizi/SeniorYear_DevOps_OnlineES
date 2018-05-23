package cn.edu.nju.software.feign;

import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import param.FinishParam;
import param.MarkParam;
import param.SelectParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mengf on 2017/12/6 0006.
 */

@FeignClient("exam-client")
public interface ExamFeign {

    /**
     * 获取考试基本信息
     * @param eid
     * @return
     */
    @GetMapping(value = "/exam/info/{eid}")
    Result getExamInfo(@PathVariable(name = "eid") Long eid);

    /**
     * 获取考试的试卷
     * 所有试题信息
     * @param sid
     * @param eid
     * @return
     */
    @GetMapping(value = "/exam/paper")
    Result getExamPaper(@RequestParam(name = "sid") Long sid,@RequestParam(name = "eid") Long eid);

    /**
     * 考生标记了某题目后保存该状态
     * @param param
     * @return
     */
    @PostMapping(value = "/exam/mark")
    Result markQuestion(@RequestBody MarkParam param);

    /**
     * 考生提交了某题目后保存该题目的选择
     * @param param
     * @return
     */
    @PostMapping(value = "/exam/select")
    Result selectQuestion(@RequestBody SelectParam param);

    /**
     * 根据exam_exercise_id获取对应的题目详细信息
     * @param id
     * @return
     */
    @GetMapping(value = "/exam/exercise")
    Result getExamPaperExercise(@RequestParam(name="exam_exercise_id") Long id);


    /**
     * 考试结束 统计结果
     * @param param
     * @return
     */
    @PostMapping(value = "/exam/finish")
    Result finish(@RequestBody FinishParam param);


    /**
     * 获取考生考试结果
     * @param sid
     * @param eid
     * @return
     */
    @GetMapping(value = "/exam/score")
    Result getScore(@RequestParam("sid") Long sid,@RequestParam("eid") Long eid);

    /**
     *
     * @param sid
     * @param eid
     * @return StudentExamDto
     */
    @GetMapping(value = "/exam/student")
    public Result getExamStudent(@RequestParam("sid") Long sid,@RequestParam("eid") Long eid);

}
