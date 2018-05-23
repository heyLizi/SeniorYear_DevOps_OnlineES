package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import common.Result;
import dto.*;
import entity.*;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.annotatoin.Sql;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import param.CreateExamParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类说明：考试逻辑层
 * 创建者：zs
 * 包名：cn.edu.nju.software.service
 */

@Service
public class ExamService {

    @Autowired
    private ExamDao examDao;
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private ExamExerciseDao examExerciseDao;
    @Autowired
    private ExamPaperDao examPaperDao;
    @Autowired
    private SQLManager sql;

    /**
     * 创建考试
     *
     * @param param
     * @return
     */
    public Long createExam(CreateExamParam param) {
        //保存考试
        List<StudentBriefDto> studentBriefDtos = param.getStudentDtos();
        Exam exam = new Exam(param);
        exam.setStudentCount(studentBriefDtos.size());
        examDao.insert(exam, true);
        //创建试卷表
        Long eid = exam.getId();
        for (StudentBriefDto studentDto : studentBriefDtos) {
            ExamPaper paper = new ExamPaper();
            paper.setExamId(eid);
            paper.setStatus(0);
            paper.setStudentId(studentDto.getId().longValue());
            paper.setPassword(studentDto.getPassword());
            paper.setCorrectCount(0);
            paper.setScore(0);
            paperDao.insert(paper);
        }
        return eid;
    }

    /**
     * 查询老师考试数量
     *
     * @param tid
     * @return
     */
    public int queryExamCount(String tid) {
        return examDao.queryExamCount(tid);
    }

    /**
     * 查询老师考试列表
     *
     * @param tid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<ExamDto> queryExamList(String tid, Integer pageIndex, Integer pageSize) {
        int pageStart = pageIndex * pageSize + 1;
        return examDao.queryExamList(tid, pageStart, pageSize);
    }

    /**
     * 查询考试结果
     *
     * @param eid
     * @return
     */
    public ExamResultDto queryExamResult(Integer eid) {
        //查询考试基本情况
        ExamResultDto resultDto = examDao.queryExamResult(eid);
        Integer totalScore = resultDto.getSingleScore() * resultDto.getSingleNum() + resultDto.getMultiNum() * resultDto.getMultiScore();
        resultDto.setTotalScore(totalScore);
        //统计考试结果-参加考试的同学
        int attendCount = paperDao.queryAttendCount(eid);
        int passCount = paperDao.queryPassCount(eid);
        ExamScoreAnalyseDto analyse = paperDao.queryExamScoreAnalyse(eid);
        resultDto.setAttendNum(attendCount);
        resultDto.setPassedNum(passCount);
        resultDto.setHighestScore(analyse.getMaxScore());
        resultDto.setLowestScore(analyse.getMinScore());
        resultDto.setAverageScore(analyse.getAverageScore());
        return resultDto;
    }

    public Result getExam(Long eid) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("eid", eid);
        ExamDto exam = sql.selectSingle("exam.getExamDto", params, ExamDto.class);
        //Exam exam = examDao.unique(eid);
        return Result.success().message("获取考试信息成功").withData(exam);
    }

    public Result mark(Long paperId, Long exerciseId, Integer state) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("paperId", paperId);
        params.put("exerciseId", exerciseId);
        ExamExercise exercise = sql.selectSingle("examExercise.queryExamExercise", params, ExamExercise.class);
        if (exercise != null) {
            exercise.setMarkStatus(state);
            examExerciseDao.updateById(exercise);
            return Result.success().message("标记成功");
        } else {
            return Result.error().errorMessage("未找到标记题目信息");
        }
    }

    public Result select(Long paperId, Long exerciseId, String studentAnswers) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("paperId", paperId);
        params.put("exerciseId", exerciseId);
        ExamExercise exercise = sql.selectSingle("examExercise.queryExamExercise", params, ExamExercise.class);
        if (exercise != null) {
            exercise.setStudentAnswers(studentAnswers);
            exercise.setCorrectStatus(getCorrectStatus(exercise.getAnswers(), studentAnswers));
            examExerciseDao.updateById(exercise);
            return Result.success().message("标记成功");
        } else {
            return Result.error().errorMessage("未找到题目信息,题目信息丢失");
        }
    }

    public Long getPaperId(Long studentId, Long examId) {
        ExamPaper examPaper = new ExamPaper();
        examPaper.setStudentId(studentId);
        examPaper.setExamId(examId);
        List<ExamPaper> papers = examPaperDao.template(examPaper);
        if (papers.size() > 0) {
            return papers.get(0).getId();
        }
        return examPaper.getId();
    }

    //根据学生的答案和标准答案 判断0-错误 1-正确 2-漏选
    private Integer getCorrectStatus(String answers, String studentAnswers) {
        if (StringUtils.isEmpty(studentAnswers)) {
            return 0;
        }
        String[] stdAnswers = answers.split(",");
        List<String> answerList = Lists.newArrayList(stdAnswers);
        String[] stuAnswers = studentAnswers.split(",");
        for (String answer : stuAnswers) {
            if (!answerList.contains(answer)) {
                return 0;
            }
        }
        if (stdAnswers.length > stuAnswers.length) {
            return 2;
        }
        return 1;
    }

    public Result getExamPaper(Long sid, Long eid) {
        ExamPaper paper = examPaperDao.querySingle(sid, eid);
        if (paper.getId() == null) {
            return Result.error().errorMessage("获取不到试卷编号");
        }
        List<ExamExerciseDto> exercises = examExerciseDao.queryPaperExercises(paper.getId());
        return Result.success().message("获取试卷题目集合成功").withData(exercises);
    }

    public Result getExercise(Long examExerciseId) {
        ExamExerciseDto examExercise = examExerciseDao.queryExamExerciseDto(examExerciseId);
        Long exerciseId = examExercise.getExerciseId();
        //题目信息 exercise.question 为题目内容
        //ExamExercise exercise = exerciseDao.unique(exerciseId);
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", exerciseId);
        Exercise exercise = sql.selectUnique("examExercise.queryById", params, Exercise.class);
        String optionsStr = examExercise.getOptions();
        params.put("options", optionsStr.split(","));
        List<ExerciseOptionDto> options = sql.select("exerciseOption.queryOptionListByOrder", ExerciseOptionDto.class, params);
        ExamExerciseDetailDto examExerciseDtailDto = new ExamExerciseDetailDto();
        BeanUtils.copyProperties(examExercise, examExerciseDtailDto);
        examExerciseDtailDto.setContent(exercise.getQuestion());
        examExerciseDtailDto.setOptionDtos(options);
        return Result.success().message("获取该道考题成功!").withData(examExerciseDtailDto);
    }

    public Result finishExam(Long sid, Long eid) {
        ExamPaper paper = examPaperDao.querySingle(sid, eid);
        paper.setTrueEndTime(new Date());
        //考试结束
        paper.setStatus(2);
        //先把考试状态设置为结束
        examPaperDao.updateById(paper);
        //计算成绩
        List<ExamExerciseResultDto> resultDtos = examExerciseDao.queryPaperExerciseResults(paper.getId());
        Exam exam = examDao.unique(eid);
        int multi_score = exam.getMultiScore();
        int single_score = exam.getSingleScore();
        int correctNums = 0;
        int score = 0;
        for (ExamExerciseResultDto dto : resultDtos) {
            //分值是单选题还是多选题的分数
            int question_score = dto.getExerciseType() == 1 ? single_score : multi_score;
            if (dto.getCorrectStatus() == 1) {
                score += question_score;
                correctNums++;
            } else if (dto.getCorrectStatus() == 2) {
                score += ((question_score + 1) / 2);
            }
        }
        paper.setCorrectCount(correctNums);
        paper.setScore(score);
        //更新成绩
        examPaperDao.updateById(paper);
        return Result.success().message("考试结束,交卷评分成功!");
    }


    public Result getExamScore(Long sid, Long eid) {
        ExamPaper paper = examPaperDao.querySingle(sid, eid);
        int status = paper.getStatus();
        if (status == 0) {
            return Result.error().errorMessage("该考生尚未开始考试！");
        }
        if (status == 1) {
            return Result.error().errorMessage("该考生刚开始考试！");
        }
        //Long paperId = paper.getId();
        ExamScoreDto examScoreDto = examPaperDao.queryExamScoreDto(sid, eid);

        return Result.success().message("获取考生成绩信息成功！").withData(examScoreDto);
    }

    public Result getExamStudent(Long sid,Long eid){
        StudentExamDto dto = paperDao.queryExamStudent(eid,sid);
        return Result.success().message("获取学生考试信息成功！").withData(dto);
    }
}
