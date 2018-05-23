package dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by mengf on 2017/12/16 0016.
 */
@Data
public class ExamScoreDto {
    //考试id
    private Long eid;
    //学生id
    private Long sid;
    //课程名
    private String courseName;
    //考试时长
    private int period;
    //考试名称
    private String name;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //实际开始考试时间
    private Date trueStartTime;
    //实际结束考试时间
    private Date trueEndTime;
    //试题数
    private int questionCount;
    //考试人数(报名人数)
    private int studentCount;
    private int singleNum;
    private int singleScore;
    private int multiNum;
    private int multiScore;
    //答对题目数
    private int correctCount;
    //成绩
    private int score;
}
