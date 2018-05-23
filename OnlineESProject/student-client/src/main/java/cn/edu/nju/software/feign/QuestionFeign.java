package cn.edu.nju.software.feign;

import common.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by mengf on 2017/12/14 0014.
 */
@FeignClient(name = "question-client")
public interface QuestionFeign {
    /**
     * 学生准备进行考试 生成考试试卷
     * @param eid 考试ID
     * @param sid 学生ID
     * @return 考试试卷的内容信息
     */
    @GetMapping("paper/create")
    Result createPaper(@RequestParam("eid") Integer eid, @RequestParam("sid") Integer sid);

}
