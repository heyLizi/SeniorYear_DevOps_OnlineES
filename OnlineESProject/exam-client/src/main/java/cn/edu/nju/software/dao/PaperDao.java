package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.ExamScoreAnalyseDto;
import dto.StudentExamDetailDto;
import dto.StudentExamDto;
import entity.ExamPaper;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.RowSize;
import org.beetl.sql.core.annotatoin.RowStart;

import java.util.List;

/**
 * 类说明：考试试卷数据层
 * 创建者：zs
 * 创建时间：2017/12/10 下午12:46
 * 包名：cn.edu.nju.software.dao
 */

public interface PaperDao extends $Mapper<ExamPaper> {

    /**
     * 参加考试的人数
     *
     * @param eid
     * @return
     */
    public int queryAttendCount(@Param("eid") Integer eid);

    /**
     * 考试及格的人数
     *
     * @param eid
     * @return
     */
    public int queryPassCount(@Param("eid") Integer eid);

    /**
     * 考试成绩统计
     *
     * @param eid
     * @return
     */
    public ExamScoreAnalyseDto queryExamScoreAnalyse(@Param("eid") Integer eid);

    /**
     * 分页查询学生考试情况
     *
     * @param eid
     * @param pageStart
     * @param pageSize
     * @return
     */
    public List<StudentExamDto> queryExamStudentList(@Param("eid") Integer eid, @RowStart int pageStart, @RowSize int pageSize);


    public StudentExamDto queryExamStudent(@Param("eid") Long eid, @Param("sid") Long sid);

    /**
     * 查询试卷学生考试详情
     *
     * @param epid
     * @return
     */
    public StudentExamDetailDto queryExamDetail(@Param("epid") Integer epid);

    /**
     * 查询考试所有学生ID
     *
     * @param eid
     * @return
     */
    public List<String> queryExamStudentIDList(@Param("eid") Integer eid);

    /**
     * 根据考试ID和学生ID查询试卷ID
     *
     * @param eid
     * @param sids
     * @return
     */
    public List<Integer> queryIdList(@Param("eid") Integer eid, @Param("sids") String[] sids);
}
