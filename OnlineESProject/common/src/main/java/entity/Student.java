package entity;

/**
 * Created by mengf on 2017/11/21 0021.
 */

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * 学生表实体类
 */
@Data
@Table(name = "es_student")
public class Student extends IDEntity {
    //学号
    private String number;
    //名称
    private String name;
    //性别
    private Integer sex;
    //邮箱
    private String mail;
    //加密后的密码
    private String password;
    //加密的盐度
    private Double salt;
    //学校ID
    private Long schoolId;
    //学院ID
    private Long institutionId;
    //年级ID
    private Long gradeId;
    //班级ID
    private Long classId;
    //激活码
    private String verifyCode;
    //是否激活 0 未激活 1激活
    private Integer active;
}
