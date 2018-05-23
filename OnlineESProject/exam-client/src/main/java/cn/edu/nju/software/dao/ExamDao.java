package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.ExamDto;
import dto.ExamResultDto;
import entity.Exam;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.RowSize;
import org.beetl.sql.core.annotatoin.RowStart;

import java.util.List;

/**
 * 类说明：考试数据层
 * 创建者：zs
 * 创建时间：2017/12/10 下午12:46
 * 包名：cn.edu.nju.software.dao
 */

public interface ExamDao extends $Mapper<Exam> {

    /**
     * 查询老师考试数量
     * @param tid
     * @return
     */
    public int queryExamCount(@Param("tid") String tid);

    /**
     * 查询老师考试列表
     * @param tid
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<ExamDto> queryExamList(@Param("tid") String tid, @RowStart int pageStart, @RowSize int pageSize);

    /**
     * 查询考试结果
     * @param eid
     * @return
     */
    public ExamResultDto queryExamResult(@Param("eid") Integer eid);

    /**
     * 根据试卷ID查询考试信息
     * @param epid
     * @return
     */
    public Exam querySimpleExam(@Param("epid") Integer epid);
}
