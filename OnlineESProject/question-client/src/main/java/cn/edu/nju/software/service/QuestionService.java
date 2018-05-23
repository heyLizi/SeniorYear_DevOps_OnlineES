package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.QuestionDao;
import cn.edu.nju.software.dao.QuestionOptionDao;
import dto.QuestionBriefDto;
import dto.QuestionDto;
import entity.Exercise;
import entity.ExerciseOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类说明：题目Service
 * 创建者：zs
 * 包名：cn.edu.nju.software.service
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private QuestionOptionDao optionDao;

    /**
     * 查询题库试题数
     * @param lid
     * @return
     */
    public int queryQuestionCount(Integer lid) {
        return questionDao.queryQuestionCount(lid);
    }

    /**
     * 查询题库题目
     * @param lid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<QuestionBriefDto> queryQuestionList(Integer lid, Integer pageIndex, Integer pageSize) {
        int pageStart = pageIndex*pageSize+1;
        List<QuestionBriefDto> questionDtos = questionDao.queryQuestionList(lid,pageStart,pageSize);
        return questionDtos;
    }

    /**
     * 查询题目选项
     * @param qid
     * @return
     */
    public QuestionDto queryQuestionDetail(Integer qid) {
        QuestionBriefDto briefDto = questionDao.queryQuestionById(qid);
        QuestionDto questionDto = new QuestionDto(briefDto);
        List<ExerciseOption> optionList = optionDao.queryOptionList(qid);
        questionDto.setOptions(optionList);
        return questionDto;
    }

    /**
     * 新增题目
     * @param exercise
     * @return
     */
    public Long insertQuestion(Exercise exercise) {
        questionDao.insert(exercise,true);
        return exercise.getId();
    }

    /**
     * 新增选项
     * @param option
     */
    public void insertOption(ExerciseOption option) {
        optionDao.insert(option);
    }
}
