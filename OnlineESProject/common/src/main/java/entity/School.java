package entity;

/**
 * Created by mengf on 2017/11/21 0021.
 */

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * 学校表实体类
 */
@Data
@Table(name = "es_school")
public class School extends IDEntity{
    //学校名称
    private String name;
}
