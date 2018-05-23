package cn.edu.nju.software.feign;


import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import param.UploadQuestionParam;

@FeignClient(value = "question-client")
public interface QuestionFeign {

    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    public Result insertQuestions(@RequestBody UploadQuestionParam questionParam);
}
