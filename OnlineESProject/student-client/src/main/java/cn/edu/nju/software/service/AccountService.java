package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.StudentDao;
import common.Result;
import dto.StudentDto;
import entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.StringUtil;

/**
 * Created by mengf on 2017/11/12 0012.
 */

@Service
public class AccountService {

    @Autowired
    private StudentDao dao;
    @Autowired
    private MailService mailService;


    public Result getUserDto(Long sid){
        StudentDto dto = dao.getUserDto(sid);
        return Result.success().withData(dto);
    }

    public Result register(Student student){
        if(dao.countByNumber(student.getNumber())>0){
            return Result.error().errorMessage("Number Registered");
        }
        if(dao.countByMailBox(student.getMail())>0){
            return Result.error().errorMessage("Email Registered");
        }
        dao.insert(student, true);
        return Result.success().withData(student);
    }


    public Result checkActive(Long sid, String code) {
        Student student = dao.unique(sid);
        String verifyCode = student.getVerifyCode();
        if (StringUtils.isEmpty(verifyCode)) {
            mailService.sendVerifyCode(student.getId(), student.getMail());
            return Result.error().errorMessage("获取激活码失败，可能是邮件没有发送成功，已重新发送激活码！");
        }
        if (verifyCode.equals(code)) {
            student.setActive(1);
            dao.updateById(student);
            return Result.success().message("激活成功！").withData(student);
        }
        return Result.error().errorMessage("激活失败，激活码不匹配！");
    }
}
