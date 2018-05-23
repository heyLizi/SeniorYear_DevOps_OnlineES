package dto;

import lombok.Data;

import java.util.List;

/**
 * Created by mengf on 2017/12/16 0016.
 */
@Data
public class ExamExerciseDto {
    private Long id;
    private Long exerciseId;
    private Long examPaperId;
    private Integer exerciseType;
    //选项ID的集合 逗号隔开 比如49，36，59，12
    // 这是四个选项 分别代表A(49) B(36) C(59) D(12)
    private String options;
    //private List<Long> optionIds;
    //学生选择的答案选项 逗号隔开
    //36,59 => BC
    private String studentAnswers;
    //private List<Long> selectIds;
    //选择的选项是什么 需要根据上面两个字段 生成这个字段吗(B,C)
    //private String selectOptions;
    private Integer markStatus;
}
