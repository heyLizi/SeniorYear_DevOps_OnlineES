package dto;

import lombok.Data;

/**
 * 类说明：学生的简单信息
 * 创建者：zs
 * 包名：dto
 */

@Data
public class StudentBriefDto {

    //ID
    private Integer id;

    //名字
    private String name;

    //邮箱
    private String email;

    //考试密码
    private String password;

}
