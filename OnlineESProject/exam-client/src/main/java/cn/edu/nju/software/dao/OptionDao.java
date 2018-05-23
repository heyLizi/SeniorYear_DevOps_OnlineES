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
public interface OptionDao extends $Mapper<ExerciseOption> {


    /**
     * 按顺序查询选项列表
     * @param options
     * @return
     */
    public List<ExerciseOption> queryOptionListByOrder(@Param("options") String[] options);
}
