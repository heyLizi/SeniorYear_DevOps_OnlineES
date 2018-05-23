package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.TeacherDao;
import com.alibaba.fastjson.JSON;
import dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.EncryptUtil;

/**
 * 类说明：登录服务
 * 创建者：zs
 * 包名：cn.edu.nju.software.service
 */

@Service
public class LoginService {

    @Autowired
    private TeacherDao teacherDao;

    /**
     * 老师登录
     * @param name
     * @param password
     * @return
     */
    public TeacherDto loginForTeacher(String name, String password) {
        if(name == null || name.equals("") || password == null || password.equals("")){
            return null;
        }
        //密码加密
        String ePassword = EncryptUtil.SHA256(password);
        TeacherDto teacherDto = teacherDao.loginForTeacher(name,ePassword);
        return teacherDto;
    }

}
