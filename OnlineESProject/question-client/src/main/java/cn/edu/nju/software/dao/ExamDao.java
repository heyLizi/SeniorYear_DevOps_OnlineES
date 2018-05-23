package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import entity.Exam;
import org.beetl.sql.core.annotatoin.Param;

/**
 * 类说明：考试试卷数据层
 * 创建者：zs
 * 创建时间：2017/12/10 下午12:46
 * 包名：cn.edu.nju.software.dao
 */

public interface ExamDao extends $Mapper<Exam> {

    /**
     * 查询考卷ID
     * @param eid
     * @param sid
     * @return
     */
    public Integer queryPaperId(@Param("eid") Integer eid, @Param("sid") Integer sid);
}
