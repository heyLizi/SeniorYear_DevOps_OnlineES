package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.StudentDao;
import cn.edu.nju.software.feign.MailFeign;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import common.Result;
import dto.ExamExerciseResultDto;
import dto.ExamScoreDto;
import entity.Exam;
import entity.ExamExercise;
import entity.Student;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import param.MailParam;
import util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by mengf on 2017/12/10 0010.
 */
@Service
public class MailService {

    @Autowired
    private MailFeign mailFeign;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private ExamService examService;
    private ExamScoreDto scoreDto;

    //private static final String activeURL = "http://localhost:9001/OnlineES/active";

    public Result sendVerifyCode(Long sid, String mailbox) {
        Student student = studentDao.unique(sid);
        student.setActive(0);
        //随机生成验证码
        student.setVerifyCode(RandomStringUtils.random(6, false, true));
        studentDao.updateById(student);
        StringBuffer content = new StringBuffer()
                .append("亲爱的").append(student.getName())
                .append("用户：").append("\n")
                .append("您的账户激活码为：")
                .append(student.getVerifyCode())
                .append("\n你猜这是什么组考试小组致上");
        //.append("您在我们网站注册了账户，需要激活，请点击以下下链接激活:")
        //.append("\n").append("<a>"++"</a>");
        return sendSimpleEmail(mailbox, "注册验证", content.toString());
    }


    public Result sendScoreCode(Long sid, Long eid) {
        Result result = examService.getExamScore(sid, eid);
        if(!result.isSuccess()){
            //获取考试成绩失败直接返回失败信息
            return result;
        }
        ExamScoreDto scoreDto = JSON.parseObject(JSON.toJSONString(result.getData()),ExamScoreDto.class);
        
        Student student = studentDao.unique(sid);
        StringBuilder content = new StringBuilder()
                .append("亲爱的")
                .append(student.getName())
                .append("同学:").append("\n")
                .append("您参加了").append(DateUtil.parseTimeToString(scoreDto.getStartTime()))
                .append("-").append(DateUtil.parseTimeToString(scoreDto.getStartTime()))
                .append("为期").append(scoreDto.getPeriod()).append("分钟的考试！")
                .append("\n")
                .append("实际参加时间：").append(DateUtil.parseTimeToString(scoreDto.getTrueStartTime()))
                .append("\n")
                .append("实际结束时间为：").append(DateUtil.parseTimeToString(scoreDto.getTrueEndTime()))
                .append("\n")
                .append("考试科目：").append(scoreDto.getCourseName())
                .append("\n")
                .append("考试名称：").append(scoreDto.getName())
                .append("\n")
                .append("考试总题目数：")
                .append(scoreDto.getQuestionCount())
                .append("\n")
                .append("单选题数量：")
                .append(scoreDto.getSingleNum())
                .append("\n")
                .append("多选题数量：")
                .append(scoreDto.getMultiNum())
                .append("\n")
                .append("正确数量：")
                .append(scoreDto.getCorrectCount())
                .append("\n")
                .append("分数：")
                .append(scoreDto.getScore());

        return sendSimpleEmail(student.getMail(), "考试结果", content.toString());
    }

    //通用发送HTML邮件的方法
    private Result sendHtmlEmail(String mailbox, String subject, String content) {
        MailParam param = new MailParam();
        param.setSubject(subject);
        param.setContent(content.toString());
        param.setTos(new String[]{mailbox});
        List<MailParam> params = Lists.newArrayList();
        params.add(param);
        try {
            mailFeign.sendHtmlMail(params);
            return Result.success().message("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success().message("发送邮件失败！但是能收到邮件");
        }
    }

    //通用发送简单邮件的方法
    private Result sendSimpleEmail(String mailbox, String subject, String content) {
        MailParam param = new MailParam();
        param.setSubject(subject);
        param.setContent(content.toString());
        param.setTos(new String[]{mailbox});
        List<MailParam> params = Lists.newArrayList();
        params.add(param);
        try {
            mailFeign.sendMail(params);
            return Result.success().message("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success().message("发送邮件失败！但是能收到邮件");
        }
    }
}
