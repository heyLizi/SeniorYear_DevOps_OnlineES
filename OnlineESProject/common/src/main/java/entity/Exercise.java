package entity;

import common.IDEntity;
import dto.QuestionDto;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 题目表实体类
 */
@Data
@Table(name = "es_exercise")
public class Exercise extends IDEntity {
    //题干
    private String question;
    //题目的选项ID集合 逗号隔开
    private Integer optionCount;
    //题目正确答案的选项的ID集合，逗号隔开
    private Integer answerCount;
    //该题目所属的题库的ID
    private Long exerciseBankId;
    //该题目的类型ID 该题目属于单选题还是多选题
    private Long exerciseTypeId;

    public Exercise() {
    }

    public Exercise(QuestionDto questionDto) {
        this.question = questionDto.getQuestion();
        this.optionCount = questionDto.getOptionCount();
        this.answerCount = questionDto.getAnswerCount();
        this.exerciseTypeId = questionDto.getTypeId().longValue();
    }
}
