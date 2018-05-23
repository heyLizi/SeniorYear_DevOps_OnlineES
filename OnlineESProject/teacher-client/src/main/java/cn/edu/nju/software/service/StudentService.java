package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.StudentDao;
import com.alibaba.fastjson.JSON;
import common.SystemConst;
import dto.StudentBriefDto;
import dto.StudentUploadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：学生Service
 * 创建者：zs
 * 包名：cn.edu.nju.software.service
 */

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;


    public List<StudentBriefDto> queryStudentList(List<StudentUploadDto> studentDtos) {
        List<String> emails = new ArrayList<>();
        for(StudentUploadDto dto : studentDtos){
            emails.add(dto.getEmail());
        }
        String[] emailArray = new String[emails.size()];
        for(int i=0;i<emails.size();i++){
            emailArray[i] = emails.get(i);
        }
        System.out.println(JSON.toJSONString(emailArray));
        List<StudentBriefDto> studentBriefDtos = studentDao.queryStudentList(emailArray);
        for(StudentBriefDto studentBriefDto : studentBriefDtos){
            //生成密码
            studentBriefDto.setPassword(StringUtil.ramdomStr(SystemConst.PASSWORD_LENGTH));
        }
        return studentBriefDtos;
    }
}
