package cn.edu.nju.software.feign;

import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import param.CreateExamParam;
import param.MailParam;

import java.util.List;

/**
 * 考试服务模块
 */
@FeignClient(value = "mail-client")
public interface EmailFeign {

    @RequestMapping(value = "/mail/simple",method = RequestMethod.POST)
    public Result sendMail(@RequestBody List<MailParam> mailParams);
}
