package dto;

import lombok.Data;

//学生的考试信息

@Data
public class StudentExamDto {
	//试卷ID
	private Integer id;

	//学生唯一id
	private Integer sid;

	//姓名
	private String name;
	
	//学号
	private String number;
	
	//是否参与考试
	private Integer status;
	
	//答对的题目的数量
	private Integer correctCount;
	
	//分数
	private Integer score;
	
	//排名
	private Integer rank;

}
