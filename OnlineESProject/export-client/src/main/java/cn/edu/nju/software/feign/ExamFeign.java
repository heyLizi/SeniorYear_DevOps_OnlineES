package cn.edu.nju.software.feign;

import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 考试服务模块
 */
@FeignClient(value = "exam-client")
public interface ExamFeign {

    @RequestMapping(value = "/exam/result",method = RequestMethod.GET)
    public Result queryExamResult(@RequestParam("eid") Integer eid);

    @RequestMapping(value = "/paper/list",method = RequestMethod.GET)
    public Result queryExamStudentList(@RequestParam("eid") Integer eid, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/paper/detail",method = RequestMethod.GET)
    public Result queryExamDetail(@RequestParam("epid") Integer epid);

    @RequestMapping(value = "/paper/questions",method = RequestMethod.GET)
    public Result queryExamQuestionList(@RequestParam("epid") Integer epid, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/paper/students",method = RequestMethod.GET)
    public Result queryPaperList(@RequestParam("eid") Integer eid, @RequestParam("sidStr") String sidStr);
}
