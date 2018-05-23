package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.LibBriefDto;
import dto.LibDto;
import entity.ExerciseBank;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.RowSize;
import org.beetl.sql.core.annotatoin.RowStart;

import java.util.Date;
import java.util.List;

/**
 * 类说明：题库Dao
 * 创建者：zs
 * 包名：cn.edu.nju.software.dao
 */

public interface BankDao extends $Mapper<ExerciseBank> {

    /**
     * 查询题库数
     * @param cid
     * @return
     */
    public int queryBankCount(@Param("cid") Integer cid);


    /**
     * 分页查询题库信息
     * @param cid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<LibDto> queryBankList(@Param("cid") Integer cid, @RowStart int pageIndex, @RowSize int pageSize);

    /**
     * 查询题库信息
     * @param lid
     * @return
     */
    public LibDto queryBankById(@Param("lid") Integer lid);

    /**
     * 查询题库简单信息
     * @param cid
     * @return
     */
    public List<LibBriefDto> querySimpleBankList(@Param("cid") Integer cid);

    /**
     * 更新题目数量
     * @param lid
     * @param count
     * @param modifyTime
     */
    public void updateBankCount(@Param("lid") Integer lid, @Param("count") Integer count, @Param("modifyTime") Date modifyTime);
}
