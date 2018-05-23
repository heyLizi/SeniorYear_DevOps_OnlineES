package cn.edu.nju.software.feign;

import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import param.MailParam;

import java.util.List;

/**
 * Created by mengf on 2017/12/6 0006.
 */

@FeignClient("mail-client")
public interface MailFeign {

    @PostMapping("/mail/simple")
    public Result sendMail(@RequestBody List<MailParam> mailParams);

    @PostMapping("/mail/html")
    public Result sendHtmlMail(@RequestBody List<MailParam> mailParams);
}
