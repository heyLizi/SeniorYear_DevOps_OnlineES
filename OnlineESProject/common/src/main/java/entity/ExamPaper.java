package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 考试试卷关系表实体类
 */
@Data
@Table(name = "es_exam_paper")
public class ExamPaper extends IDEntity {
    //考试ID 此试卷属于哪个考试
    private Long examId;
    //学生ID 此试卷是哪个学生的试卷
    private Long studentId;

    //考试密码
    private String password;

    //考试状态 1-参加考试 0-未参加考试
    private Integer status;

    //正确题数,未参加考试为0
    private Integer correctCount;

    //分数，未参加考试为0
    private Integer score;

    private Date trueStartTime;
    private Date trueEndTime;

}
