package entity;

/**
 * Created by mengf on 2017/11/21 0021.
 */

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * 课程数据表实体结构
 */
@Data
@Table(name = "es_course")
public class Course extends IDEntity {
    //课程名称
    private String name;
    //课程学期
    private String period;
    //学分
    private Integer score;
    //教师ID
    private Long teacherId;
    //课程类型ID
    private Long courseTypeId;
    //学院ID
    private Long institutionId;
}
