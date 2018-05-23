package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 老师表实体类
 */
@Data
@Table(name = "es_teacher")
public class Teacher extends IDEntity{
    //教师工号
    private String number;
    //教师名称
    private String name;
    //教师密码
    private String password;
    //教师学校ID
    private String schoolId;
    //教师学院ID
    private String institutionId;
}
