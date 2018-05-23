package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.PaperService;
import common.Result;
import dto.ExamQuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类说明：
 * 创建者：zs
 * 创建时间：2017/12/11 上午2:07
 * 包名：cn.edu.nju.software.controller
 */

@RestController
@RequestMapping("paper")
public class ExamPaperController {

    @Autowired
    private PaperService paperService;

    /**
     * 生成考题
     * @param eid 考试ID
     * @param sid 考生ID
     * @return
     */
    @GetMapping("create")
    public Result createPaper(@RequestParam("eid") Integer eid,@RequestParam("sid") Integer sid){
        List<ExamQuestionDto> questionList = paperService.createPaper(eid,sid);
        if(questionList == null || questionList.isEmpty()){
            return Result.error().errorMessage("创建考试失败！");
        }
        return Result.success().withData(questionList);
    }
}
