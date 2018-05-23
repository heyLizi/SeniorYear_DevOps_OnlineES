package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 题目类型关系表实体类
 */
@Data
@Table(name = "es_exercise_type")
public class ExerciseType extends IDEntity {
    //题型名称
    private String name;
}
