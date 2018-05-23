package dto;

import lombok.Data;

@Data
public class QuestionBriefDto {
	//ID
	private Integer id;

	//类型
	private String typeName;
	
	//内容
	private String question;

	//选项个数
	private int optionCount;

	//正确答案个数
	private int answerCount;

}
