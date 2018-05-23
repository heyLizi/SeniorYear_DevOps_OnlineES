package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 学院表实体类
 */
@Data
@Table(name = "es_institution")
public class Institution extends IDEntity{
    //学院名称
    private String name;
    //学校ID
    private Long schoolId;
}
