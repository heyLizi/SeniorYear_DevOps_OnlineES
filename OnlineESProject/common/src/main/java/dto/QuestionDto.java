package dto;

import entity.ExerciseOption;
import lombok.Data;
import java.util.List;

//一道题目的信息
@Data
public class QuestionDto {

	//ID
    private Integer id;
	//类型
    private Integer typeId;
    private String typeName;
	
	//考题内容
    private String question;
	
	//选项-按顺序ABCDEF
    private Integer optionCount;
    private List<ExerciseOption> options;
    private String optionStr;

	//答案
    private Integer answerCount;

	//正确答案
    private String tAnswer;

	//考生答案
    private String sAnswer;
	
	//考生得分:注意是考生获得的分数
    private double sScore;
	//答题状态
    private int correctStatus;


	public QuestionDto() {
    }

    public QuestionDto(QuestionBriefDto briefDto) {
        super();
        this.id = briefDto.getId();
        this.typeName = briefDto.getTypeName();
        this.question = briefDto.getQuestion();
    }

}

