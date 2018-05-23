package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 班级表实体结构
 */
@Data
@Table(name = "es_class")
public class Class extends IDEntity {
    //班级名称
    private String name;
    //所属年级的Id
    private Long gradeId;
}
