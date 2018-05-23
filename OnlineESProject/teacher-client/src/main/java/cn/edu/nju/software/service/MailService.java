package cn.edu.nju.software.service;

import com.alibaba.fastjson.JSON;
import com.sun.javafx.binding.StringFormatter;
import common.SystemConst;
import dto.StudentBriefDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import param.CreateExamParam;
import param.MailParam;
import util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 类说明：邮件Service
 * 创建者：zs
 * 创建时间：2017/12/6 下午11:58
 * 包名：cn.edu.nju.software.service
 */

@Service
public class MailService {

    @Value("${email.url}")
    private String url;

    /**
     * 转换邮件参数
     * @param eid
     * @param examParam
     * @return
     */
    public List<MailParam> changeToParam(Integer eid, CreateExamParam examParam) {
        List<MailParam> mailParamList = new ArrayList<>();
        List<StudentBriefDto> studentBriefDtos = examParam.getStudentDtos();
        for(StudentBriefDto student : studentBriefDtos){
            MailParam param = new MailParam();
            String[] tos = { student.getEmail() };
            param.setTos(tos);
            param.setSubject(SystemConst.MAIL_CONTENT);
            String key = "eid="+eid+"&sid="+student.getId();
            String encode = Base64.getEncoder().encodeToString(key.getBytes());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(examParam.getStartTime());
            calendar.add(Calendar.HOUR,-8);
            String startTime = sdf.format(calendar.getTime());
            calendar.setTime(examParam.getEndTime());
            calendar.add(Calendar.HOUR,-8);
            String endTime = sdf.format(calendar.getTime());
            String content = String.format("%s，您好\n 您有一场“%s”的考试将于%s开始考试，考试结束时间是%s。请您按时点击下方的链接参加考试。参加考试的密码是%s \n" +
                            "考试链接：%s%s \n" +
                            "OnlineExamnation小组",
                    student.getName(),examParam.getName(),startTime,endTime,student.getPassword(),url,encode);
            param.setContent(content);
            mailParamList.add(param);
        }
        return mailParamList;
    }

}
