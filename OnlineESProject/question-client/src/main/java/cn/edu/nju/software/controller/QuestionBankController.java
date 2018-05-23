package cn.edu.nju.software.controller;

import cn.edu.nju.software.service.BankService;
import dto.LibBriefDto;
import dto.LibDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类说明：题库管理
 * 创建者：zs
 * 包名：cn.edu.nju.software.controller
 */

@RestController
@RequestMapping(value = "bank")
public class QuestionBankController {

    @Autowired
    private BankService bankService;

    @RequestMapping(value = "count",method = RequestMethod.GET)
    public int queryBankCount(@RequestParam("cid")Integer cid){
        return bankService.queryBankCount(cid);
    }

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public List<LibDto> queryBankList(@RequestParam("cid")Integer cid, @RequestParam("pageIndex")Integer pageIndex,@RequestParam("pageSize")Integer pageSize){
        List<LibDto> libDtos = bankService.queryBankList(cid,pageIndex,pageSize);
        return libDtos;
    }

    @RequestMapping(value = "simpleList",method = RequestMethod.GET)
    public List<LibBriefDto> querySimpleBankList(@RequestParam("cid")Integer cid){
        return bankService.querySimpleBankList(cid);
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public int addBank(@RequestParam("cid")Integer cid, @RequestParam("name") String name){
        return bankService.addBank(cid,name);
    }

    @RequestMapping(value = "detail",method = RequestMethod.GET)
    public LibDto queryBankList(@RequestParam("lid")Integer lid){
        LibDto libDto = bankService.queryBankById(lid);
        return libDto;
    }
}
