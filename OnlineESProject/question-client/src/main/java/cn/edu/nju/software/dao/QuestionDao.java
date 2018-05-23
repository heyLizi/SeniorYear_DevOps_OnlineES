package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.QuestionBriefDto;
import dto.QuestionCountDto;
import entity.Exercise;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.RowSize;
import org.beetl.sql.core.annotatoin.RowStart;

import java.util.List;

/**
 * 类说明：题目Dao
 * 创建者：zs
 * 包名：cn.edu.nju.software.dao
 */

public interface QuestionDao extends $Mapper<Exercise> {

    /**
     * 查询题目数
     * @param lid
     * @return
     */
    public int queryQuestionCount(@Param("lid") Integer lid);


    /**
     * 分页查询题目信息
     * @param lid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<QuestionBriefDto> queryQuestionList(@Param("lid") Integer lid, @RowStart int pageIndex, @RowSize int pageSize);

    /**
     * 查询题目信息
     * @param qid
     * @return
     */
    public QuestionBriefDto queryQuestionById(@Param("qid") Integer qid);

    /**
     * 查询题库数量
     * @param lid
     * @return
     */
    public List<QuestionCountDto> queryQuestionCounts(@Param("lid") String lid);

    /**
     * 查询题库中的某类型题目ID
     * @param libs
     * @param type
     * @return
     */
    public List<Integer> queryLibQuestion(@Param("lids") String[] lids, @Param("type") int type);
}
