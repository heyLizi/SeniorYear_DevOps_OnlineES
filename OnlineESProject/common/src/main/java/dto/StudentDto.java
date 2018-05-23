package dto;

import lombok.Data;

/**
 * Created by mengf on 2017/12/15 0015.
 */
@Data
public class StudentDto {
    //ID
    private Integer sid;
    //名字
    private String name;
    //邮箱
    private String email;
    //学号
    private String number;
    //学校名称
    private String schoolName;
    //学院名称
    private String institutionName;
    //年级名称
    private String gradeName;
    //班级名称
    private String className;
}
