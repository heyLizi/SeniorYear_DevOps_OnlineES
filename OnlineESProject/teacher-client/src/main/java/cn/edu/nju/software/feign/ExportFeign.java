package cn.edu.nju.software.feign;

import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 导出服务模块
 */
@FeignClient(value = "export-client")
public interface ExportFeign {

    @RequestMapping(value = "/export/exam",method = RequestMethod.GET)
    public Result downloadExamFile(@RequestParam("epid") Integer epid, @RequestParam("type") Integer type);

    @RequestMapping(value = "/export/report",method = RequestMethod.GET)
    public Result downloadExamReport(@RequestParam("eid") Integer eid);

    @RequestMapping(value = "/export/zip",method = RequestMethod.GET)
    public Result downloadExamZip(@RequestParam("eid") Integer eid,@RequestParam("sidStr")  String sidStr, @RequestParam("type") Integer type);
}
