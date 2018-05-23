package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 课程类型表实体类
 */
@Data
@Table(name = "es_course_type")
public class CourseType extends IDEntity {
    //课程类型名称
    private String name;
}
