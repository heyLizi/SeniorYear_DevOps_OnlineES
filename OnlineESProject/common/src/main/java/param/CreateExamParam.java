package param;

import dto.StudentBriefDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 类说明：创建考试
 * 创建者：zs
 * 包名：param
 */

@Data
public class CreateExamParam {
    //课程ID
    private Integer cid;

    //考试名称
    private String name;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //持续时间-多少分值
    private Integer period;

    //单选题个数
    private Integer singleNum;

    //单选题分值
    private Integer singleScore;

    //多选题个数
    private Integer multiNum;

    //多选题分值
    private Integer multiScore;

    //题库id集合-,号隔开
    private String lids;

    //考生信息-不需要传
    private List<StudentBriefDto> studentDtos;
}
