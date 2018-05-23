package cn.edu.nju.software.feign;

import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 导入服务模块
 */
@FeignClient(value = "import-client")
public interface ImportFeign {

    @RequestMapping(value = "/import/question",method = RequestMethod.GET)
    public Result importQuestionExcel(@RequestParam("lid") Integer lid, @RequestParam("path") String path);

    @RequestMapping(value = "/import/student",method = RequestMethod.GET)
    public Result importStudentExcel(@RequestParam("path")String path);
}
