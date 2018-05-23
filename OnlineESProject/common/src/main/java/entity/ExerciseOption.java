package entity;

/**
 * Created by mengf on 2017/11/21 0021.
 */

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * 题目选项表实体类
 */
@Data
@Table(name = "es_exercise_option")
public class ExerciseOption extends IDEntity {
    //试题ID
    public Long exerciseId;
    //选项内容
    public String content;
    //选项正确性 1-正确，0-错误
    public Integer validity;
}
