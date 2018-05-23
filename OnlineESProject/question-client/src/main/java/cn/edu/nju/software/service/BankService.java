package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.BankDao;
import cn.edu.nju.software.dao.QuestionDao;
import dto.LibBriefDto;
import dto.LibDto;
import dto.QuestionCountDto;
import entity.ExerciseBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 类说明：题库Service
 * 创建者：zs
 * 包名：cn.edu.nju.software.service
 */

@Service
public class BankService {


    @Autowired
    private BankDao bankDao;
    @Autowired
    private QuestionDao questionDao;

    /**
     * 查询题库总数
     * @param cid
     * @return
     */
    public int queryBankCount(Integer cid) {
        return bankDao.queryBankCount(cid);
    }

    /**
     * 分页查询题库信息
     * @param cid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<LibDto> queryBankList(Integer cid, int pageIndex, int pageSize) {
        int pageStart = pageIndex*pageSize+1;
        return bankDao.queryBankList(cid,pageStart,pageSize);
    }

    /**
     * 新建题库
     * @param cid
     * @param name
     * @return
     */
    public int addBank(Integer cid, String name) {
        ExerciseBank bank = new ExerciseBank();
        bank.setCourseId(cid.longValue());
        bank.setName(name);
        bank.setCount(0);
        bank.setCreateTime(new Date());
        bank.setModifyTime(new Date());
        bankDao.insert(bank,true);
        return bank.getId().intValue();
    }

    /**
     * 查询题库信息
     * @param lid
     * @return
     */
    public LibDto queryBankById(Integer lid) {
        return bankDao.queryBankById(lid);
    }

    /**
     * 查询题库简单信息
     * @param cid
     * @return
     */
    public List<LibBriefDto> querySimpleBankList(Integer cid) {
        List<LibBriefDto> libBriefDtos = bankDao.querySimpleBankList(cid);
        for(LibBriefDto dto : libBriefDtos){
            List<QuestionCountDto> counts = questionDao.queryQuestionCounts(dto.getId());
            for(QuestionCountDto countDto : counts){
                if(countDto.getTypeId() == 1){
                    dto.setSingle(countDto.getTypeCount());
                }else if(countDto.getTypeId() == 2){
                    dto.setMulti(countDto.getTypeCount());
                }
            }
        }
        return libBriefDtos;
    }

    /**
     * 更新题库数量
     * @param lid
     * @param count
     */
    public void updateBankCount(Integer lid, int count) {
        bankDao.updateBankCount(lid,count,new Date());
    }
}
