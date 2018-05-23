package cn.edu.nju.software.feign;

import common.Result;
import dto.ExamResultDto;
import dto.StudentExamDetailDto;
import dto.StudentExamDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import param.CreateExamParam;

import java.util.List;

/**
 * 考试服务模块
 */
@FeignClient(value = "exam-client")
public interface ExamFeign {

    @RequestMapping(value = "/create/add",method = RequestMethod.POST)
    public Result createExam(@RequestBody CreateExamParam param);

    @RequestMapping(value = "/exam/count",method = RequestMethod.GET)
    public Result queryExamCount(@RequestParam("tid") Integer tid);

    @RequestMapping(value = "/exam/list",method = RequestMethod.GET)
    public Result queryExamList(@RequestParam("tid")Integer tid, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/exam/result",method = RequestMethod.GET)
    public Result queryExamResult(@RequestParam("eid") Integer eid);

    @RequestMapping(value = "/paper/list",method = RequestMethod.GET)
    public Result queryExamStudentList(@RequestParam("eid") Integer eid, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/paper/detail",method = RequestMethod.GET)
    public Result queryExamDetail(@RequestParam("epid") Integer epid);

    @RequestMapping(value = "/paper/questions",method = RequestMethod.GET)
    public Result queryExamQuestionList(@RequestParam("epid") Integer epid,@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/paper/sids",method = RequestMethod.GET)
    public Result queryExamStudentIDList(@RequestParam("eid") Integer eid);
}
