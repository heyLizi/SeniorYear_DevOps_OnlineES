package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.BankService;
import cn.edu.nju.software.service.QuestionService;
import common.Result;
import dto.QuestionBriefDto;
import dto.QuestionDto;
import entity.Exercise;
import entity.ExerciseOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import param.UploadQuestionParam;

import java.util.List;

/**
 * 类说明：
 * 创建者：zs
 * 创建时间：2017/12/8 下午4:17
 * 包名：cn.edu.nju.software.controller
 */

@RestController
@RequestMapping(value = "question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private BankService bankService;

    @RequestMapping(value = "count",method = RequestMethod.GET)
    public int queryQuestionCount(@RequestParam("lid")Integer lid){
        return questionService.queryQuestionCount(lid);
    }

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public List<QuestionBriefDto> queryQuestionList(@RequestParam("lid")Integer lid, @RequestParam("pageIndex")Integer pageIndex, @RequestParam("pageSize")Integer pageSize){
        List<QuestionBriefDto> questionDtos = questionService.queryQuestionList(lid,pageIndex,pageSize);
        return questionDtos;
    }

    @RequestMapping(value = "detail",method = RequestMethod.GET)
    public QuestionDto queryQuestionDetail(@RequestParam("qid") Integer qid){
        QuestionDto questionDto = questionService.queryQuestionDetail(qid);
        return questionDto;
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result insertQuestions(@RequestBody UploadQuestionParam questionParam){
        int count = 0;
        for(QuestionDto questionDto : questionParam.getQuestionDtos()){
            Exercise exercise = new Exercise(questionDto);
            exercise.setExerciseBankId(questionParam.getLid().longValue());
            Long qid = questionService.insertQuestion(exercise);
            List<ExerciseOption> optionList = questionDto.getOptions();
            for(ExerciseOption option : optionList){
                option.setExerciseId(qid);
                questionService.insertOption(option);
            }
            count++;
        }
        //更新题库数
        bankService.updateBankCount(questionParam.getLid(),count);
        return Result.success().withData(count);
    }
}
