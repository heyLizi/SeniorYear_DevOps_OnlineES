package dto;

import lombok.Data;

/**
 * Created by mengf on 2017/12/16 0016.
 */
@Data
public class ExamExerciseResultDto {
    private Long id;
    private Long exerciseId;
    private Long examPaperId;
    //问题类型 多选题/单选题
    private Integer exerciseType;
    //正确状态 0错误 1正确 2漏选
    private Integer correctStatus;
}
