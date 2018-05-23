package param;

import lombok.Data;

/**
 * Created by mengf on 2017/12/10 0010.
 */
@Data
public class SelectParam {
    //学生ID
    private Long studentId;
    //考试ID
    private Long examId;
    //试卷ID
    private Long paperId;
    //试题ID
    private Long exerciseId;
    //学生答案 选择的选项ID集合 用逗号隔开
    private String studentAnswers;
}
