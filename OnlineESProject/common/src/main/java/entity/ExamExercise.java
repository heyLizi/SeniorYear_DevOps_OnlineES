package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 考试试题关系表实体类
 */
@Data
@Table(name = "es_exam_exercise")
public class ExamExercise extends IDEntity {
    //考试试卷ID
    private Long examPaperId;
    //题目ID
    private Long exerciseId;
    //选项们
    //选项ID集合 逗号隔开
    private String options;
    //答案选项集合，逗号隔开
    private String answers;
    //考生答案选项集合
    private String studentAnswers;

    //正确状态 0-错误，1-正确，2-漏选，默认0
    private Integer correctStatus;
    //标记状态 1-已标记，0-未标记 默认0
    private Integer markStatus;

}
