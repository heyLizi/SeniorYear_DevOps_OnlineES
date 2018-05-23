package cn.edu.nju.software.listener;

import cn.edu.nju.software.service.ExamService;
import cn.edu.nju.software.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by mengf on 2017/12/15 0015.
 */

/**
 * 当session失效的时候给用户发送给考试结果
 * 针对未提交考试的用户(session失效时候发送)
 * 提交了的用户直接invalid也会被监听到
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    @Autowired
    private ExamService examService;
    @Autowired
    private MailService mailService;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        //给用户发送成绩信息
        Long eid = session.getAttribute("eid") == null ? null : (Long) session.getAttribute("eid");
        Long sid = session.getAttribute("sid") == null ? null : (Long) session.getAttribute("sid");
        if (eid != null && sid != null) {
            examService.finishExam(sid,eid);
            mailService.sendScoreCode(sid,eid);
        }
    }
}
