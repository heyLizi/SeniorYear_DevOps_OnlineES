package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.ExamPaperDao;
import cn.edu.nju.software.feign.ExamFeign;
import cn.edu.nju.software.feign.QuestionFeign;
import com.google.common.collect.Maps;
import common.Result;
import entity.Exam;
import entity.ExamPaper;
import org.beetl.sql.core.Params;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import param.FinishParam;
import param.MarkParam;
import param.SelectParam;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.PrimitiveIterator;

/**
 * Created by mengf on 2017/12/10 0010.
 */
@Service
public class ExamService {
    @Autowired
    private ExamFeign examFeign;
    @Autowired
    private QuestionFeign questionFeign;
    @Autowired
    private SQLManager sql;
    @Autowired
    private ExamPaperDao examPaperDao;
    private Map<String, Object> params;

    public Result getExamInfo(Long eid) {
        return examFeign.getExamInfo(eid);
    }

    public ExamPaper getExamPaper(Long eid,Long sid){
        Map<String, Object> params = Maps.newHashMap();
        params.put("sid", sid);
        params.put("eid", eid);
        ExamPaper examPaper = sql.selectSingle("examPaper.queryExamPaper", params, ExamPaper.class);
        return examPaper;
    }

    public Result checkEnterPwd(Long sid, Long eid, String password) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("sid", sid);
        params.put("eid", eid);
        ExamPaper examPaper = sql.selectSingle("examPaper.queryExamPaper", params, ExamPaper.class);
        if (examPaper == null) {
            return Result.error().errorMessage("未查到该考生的考试信息！");
        }
        System.out.println("Password:"+examPaper.getPassword());
        if (examPaper.getPassword().equals(password)) {
            return Result.success().message("输入的考试密码正确！").withData(examPaper);
        } else {
            return Result.error().errorMessage("输入的密码不正确!");
        }
    }

    public Result select(SelectParam param) {
        return examFeign.selectQuestion(param);
    }

    public Result mark(MarkParam param) {
        return examFeign.markQuestion(param);
    }

    //获取整张考试卷子
    public Result getExamPaperResult(Long sid, Long eid) {
        ExamPaper examPaper = new ExamPaper();
        examPaper.setStudentId(sid);
        examPaper.setExamId(eid);
        List<ExamPaper> examPapers = examPaperDao.template(examPaper);
        if (examPapers.size() > 0) {
            examPaper = examPapers.get(0);
            int status = examPaper.getStatus();
            if (status == 0) {
                //没生成试卷需要生成试卷
                Result result = questionFeign.createPaper(eid.intValue(), sid.intValue());
                //生成成功更新考试状态为开始了
                if (result.isSuccess()) {
                    examPaper.setTrueStartTime(new Date());
                    examPaper.setStatus(1);
                    examPaperDao.updateById(examPaper);
                } else {
                    return Result.error().errorMessage("生成试卷失败！");
                }
            }
            return examFeign.getExamPaper(sid, eid);
        } else {
            return Result.error().errorMessage("该考生没有该考试信息！");
        }
    }

    public Result getExamStudent(Long sid, Long eid) {
        return examFeign.getExamStudent(sid, eid);
    }

    //获取考试某道试题详情
    public Result getExamExercise(Long examExerciseId) {
        return examFeign.getExamPaperExercise(examExerciseId);
    }

    //获取考试成绩
    public Result getExamScore(Long sid, Long eid) {
        if (sid == null || eid == null) {
            return Result.error().errorMessage("结束考试失败，考试信息或学生信息丢失！");
        }
        return examFeign.getScore(sid, eid);
    }

    //结束考试
    public Result finishExam(Long sid, Long eid) {
        if (sid == null || eid == null) {
            return Result.error().errorMessage("结束考试失败，考试信息或学生信息丢失！");
        }
        FinishParam param = new FinishParam();
        param.setEid(eid);
        param.setSid(sid);
        return examFeign.finish(param);
    }
}
