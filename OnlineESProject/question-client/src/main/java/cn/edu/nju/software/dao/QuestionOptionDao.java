package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import entity.ExerciseOption;
import org.beetl.sql.core.annotatoin.Param;
import java.util.List;

/**
 * 类说明：题目选项Dao
 * 创建者：zs
 * 包名：cn.edu.nju.software.dao
 */
public interface QuestionOptionDao extends $Mapper<ExerciseOption> {


    /**
     * 根据ID查询选项列表
     * @param qid
     * @return
     */
    public List<ExerciseOption> queryOptionList(@Param("qid") Integer qid);

    /**
     * 乱序查询选项列表-生成考题
     * @param qid
     * @return
     */
    public List<ExerciseOption> queryRandomOptionList(@Param("qid")Integer qid);
}
