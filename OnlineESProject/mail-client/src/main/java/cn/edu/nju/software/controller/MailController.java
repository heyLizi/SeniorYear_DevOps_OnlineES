package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.MailService;
import common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import param.MailParam;

import java.util.List;

/**
 * Created by mengf on 2017/11/22 0022.
 */
@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private MailService service;

    @PostMapping("/simple")
    public Result sendMail(@RequestBody List<MailParam> mailParams) {
        mailParams.forEach(mailParam -> service.sendSimpleMail(mailParam.getTos(), mailParam.getSubject(), mailParam.getContent()));
        return Result.success().message("发送成功!");
    }


    @PostMapping("/html")
    public Result sendHtmlMail(@RequestBody List<MailParam> mailParams) {
        mailParams.forEach(mailParam -> service.sendHtmlMail(mailParam.getTos(), mailParam.getSubject(), mailParam.getContent()));
        return Result.success().message("发送成功!");
    }
}
