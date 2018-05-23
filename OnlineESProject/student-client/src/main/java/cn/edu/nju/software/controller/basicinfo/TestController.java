package cn.edu.nju.software.controller.basicinfo;

import cn.edu.nju.software.service.ExamService;
import cn.edu.nju.software.service.MailService;
import common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import param.MarkParam;
import param.SelectParam;

/**
 * Created by mengf on 2017/12/12 0012.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MailService mailService;


    @Autowired
    private ExamService examService;

    @GetMapping("select")
    public Result select(){
        SelectParam param = new SelectParam();
        param.setStudentId(1L);
        param.setExamId(1L);
        param.setPaperId(1L);
        //param.setExerciseId(12L);
        //param.setExerciseId(7L);
        param.setExerciseId(14L);
        param.setStudentAnswers("49,47");
        return examService.select(param);
    }

    @GetMapping("mark")
    public Result mark(){
        MarkParam param = new MarkParam();
        param.setStudentId(1L);
        param.setExamId(1L);
        param.setPaperId(1L);
        //param.setExerciseId(12L);
        //param.setExerciseId(7L);
        param.setExerciseId(14L);
        param.setState(1);
        return examService.mark(param);
    }
    @GetMapping("exam/info/{eid}")
    public Result getExamInfo(@PathVariable Long eid){
        return examService.getExamInfo(eid);
    }

    @GetMapping("exam/paper")
    public Result getExamPaper(@RequestParam Long sid,@RequestParam Long eid){
        return examService.getExamPaperResult(sid,eid);
    }
    @GetMapping("exam/exercise/{exam_exercise_id}")
    public Result getExamExercise(@PathVariable Long exam_exercise_id){
        return examService.getExamExercise(exam_exercise_id);
    }

    @GetMapping("exam/finish")
    public Result finishExam(@RequestParam Long sid,@RequestParam Long eid){
        return examService.finishExam(sid,eid);
    }

    @GetMapping("exam/score")
    public Result getExamScore(@RequestParam Long sid,@RequestParam Long eid){
        return examService.getExamScore(sid,eid);
    }

    @GetMapping("mail/sendScore")
    public Result sendScoreMail(@RequestParam Long sid,@RequestParam Long eid){
        return mailService.sendScoreCode(sid,eid);
    }
    @GetMapping("/verify")
    public Result sendVerifyCode(){
        return mailService.sendVerifyCode(1L,"desperatewilder@gmail.com");
    }
}
