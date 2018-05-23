package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.*;
import common.SystemConst;
import dto.ExamQuestionDto;
import dto.QuestionBriefDto;
import entity.Exam;
import entity.ExamExercise;
import entity.ExerciseOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类说明：试卷逻辑实现
 * 创建者：zs
 * 创建时间：2017/12/11 下午1:21
 * 包名：cn.edu.nju.software.service
 */

@Service
public class PaperService {

    @Autowired
    private PaperDao paperDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private ExamExerciseDao exerciseDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionOptionDao optionDao;

    /**
     * 创建试卷
     * @param eid
     * @param sid
     * @return
     */
    public List<ExamQuestionDto> createPaper(Integer eid, Integer sid) {
        List<ExamQuestionDto> list = new ArrayList<>();
        //查询考卷ID
        Integer epId = paperDao.queryPaperId(eid,sid);
        if(epId == null){
            return null;
        }
        //查询考试信息
        Exam exam = examDao.single(eid);
        if(exam == null){
            return null;
        }
        //查询题目
        String[] lids = exam.getLids().split(",");
        Integer singleNum = exam.getSingleNum();
        Integer multiNum = exam.getMultiNum();
        List<Integer> questionList = shuffleList(lids,singleNum,multiNum);
        if(questionList == null || questionList.size() < singleNum+multiNum){
            return null;
        }
        //构造题目
        for(Integer qid : questionList){
            ExamQuestionDto questionDto = createExamQuestion(epId,qid);
            list.add(questionDto);
        }
        return list;
    }

    /**
     * 根据id创建考题
     */
    private ExamQuestionDto createExamQuestion(Integer epId, Integer qid){
        ExamExercise exercise = new ExamExercise();
        //获得选项
        List<ExerciseOption> optionList = optionDao.queryRandomOptionList(qid);
        int len = optionList.size();
        //选项统计
        List<String> options = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for(ExerciseOption option : optionList){
            int oid = option.getId().intValue();
            options.add(oid+"");
            if(option.getValidity() == 1){
                answers.add(oid+"");
            }
        }
        exercise.setOptions(String.join(",",options));
        exercise.setAnswers(String.join(",",answers));
        exercise.setExamPaperId(epId.longValue());
        exercise.setExerciseId(qid.longValue());
        exercise.setCorrectStatus(0);
        exercise.setMarkStatus(0);
        exerciseDao.insert(exercise,true);
        Integer eqId = exercise.getId().intValue();
        QuestionBriefDto content = questionDao.queryQuestionById(qid);
        ExamQuestionDto examQuestionDto = new ExamQuestionDto();
        examQuestionDto.setId(eqId);
        examQuestionDto.setOptionList(optionList);
        examQuestionDto.setQuestion(content);
        return examQuestionDto;
    }

    /**
     * 乱序选择题目
     * @param lids
     * @param singleNum
     * @param multiNum
     * @return
     */
    private List<Integer> shuffleList(String[] lids,Integer singleNum,Integer multiNum){
        List<Integer> singleList = questionDao.queryLibQuestion(lids, SystemConst.SINGLE_QUESTION_TYPE);
        List<Integer> multiList = questionDao.queryLibQuestion(lids, SystemConst.MULTI_QUESTION_TYPE);
        Collections.shuffle(singleList);
        Collections.shuffle(multiList);
        List<Integer> result = new ArrayList<>();
        for(int i=0; i<singleNum; i++){
            result.add(singleList.get(i));
        }
        for(int j=0; j<multiNum; j++){
            result.add(multiList.get(j));
        }
        return result;
    }
}
