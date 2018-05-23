package entity;

/**
 * Created by mengf on 2017/11/21 0021.
 */

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * 年级表实体类
 */
@Data
@Table(name = "es_grade")
public class Grade extends IDEntity {
    //年级名称
    private String name;
    //学院ID
    private Long institutionId;
}
