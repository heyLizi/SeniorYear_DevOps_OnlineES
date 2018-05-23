package cn.edu.nju.software.feign;

import dto.LibBriefDto;
import dto.LibDto;
import dto.QuestionBriefDto;
import dto.QuestionDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 题库服务模块
 */
@FeignClient(value = "question-client")
public interface QuestionFeign {

    @RequestMapping(value = "/bank/count",method = RequestMethod.GET)
    int queryBankCount(@RequestParam("cid") Integer cid);

    @RequestMapping(value = "/bank/list",method = RequestMethod.GET)
    List<LibDto> queryBankList(@RequestParam("cid") Integer cid, @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/bank/simpleList",method = RequestMethod.GET)
    public List<LibBriefDto> querySimpleBankList(@RequestParam("cid")Integer cid);

    @RequestMapping(value = "/bank/add",method = RequestMethod.POST)
    int addBank(@RequestParam("cid")Integer cid, @RequestParam("name") String name);

    @RequestMapping(value = "/bank/detail",method = RequestMethod.GET)
    LibDto queryBankById(@RequestParam("lid")Integer lid);

    @RequestMapping(value = "/question/count",method = RequestMethod.GET)
    int queryQuestionCount(@RequestParam("lid") Integer lid);

    @RequestMapping(value = "/question/list",method = RequestMethod.GET)
    List<QuestionBriefDto> queryQuestionList(@RequestParam("lid") Integer lid, @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize);

    @RequestMapping(value = "/question/detail",method = RequestMethod.GET)
    QuestionDto queryQuestionDetail(@RequestParam("qid") Integer qid);


}
