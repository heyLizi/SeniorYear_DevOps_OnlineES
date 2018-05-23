package dto;

import lombok.Data;

/**
 * 类说明：登录后老师信息
 * 创建者：zs
 * 包名：dto
 */

@Data
public class TeacherDto {
    //老师ID
    private Integer id;
    //老师工号
    private String number;
    //老师名字
    private String name;
    //学校名
    private String schoolName;
    //学院名
    private String institutionName;
}
