package dto;

import lombok.Data;

/**
 * 类说明：上传学生的信息
 * 创建者：zs
 * 包名：dto
 */

@Data
public class StudentUploadDto {
    //学院
    private String institutionName;

    //年级
    private String gradeName;

    //班级
    private String className;

    //姓名
    private String name;

    //邮箱
    private String email;

    //学号
    private String number;

}
