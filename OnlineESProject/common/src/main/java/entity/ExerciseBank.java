package entity;

/**
 * Created by mengf on 2017/11/21 0021.
 */

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * 题目题库表实体类
 */
@Data
@Table(name = "es_exercise_bank")
public class ExerciseBank extends IDEntity {
    //题库名称
    private String name;
    //试题数量
    private Integer count;
    //上传时间 修改时间
    private Date createTime;
    private Date modifyTime;
    //所属课程ID
    private Long courseId;
}
