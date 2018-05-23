package dto;

import entity.ExerciseOption;
import lombok.Data;

import java.util.List;

/**
 * 类说明：考试题目
 * 创建者：zs
 * 创建时间：2017/12/11 下午1:53
 * 包名：dto
 */

@Data
public class ExamQuestionDto {

    //考试题目ID
    private Integer id;

    //对应习题
    private QuestionBriefDto question;

    //选项
    List<ExerciseOption> optionList;
}
