package dto;

import lombok.Data;

import java.util.Date;

//考试详细信息
@Data
public class ExamResultDto {
	//id 考试唯一id
	private Integer id;

	//考试的名字
	private String name;
	
	//科目
	private String courseName;
	
	//开始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;
	
	//试题总数
	private int questionCount;
	
	//单选题数量
	private int singleNum;
	
	//多选题数量
	private int multiNum;
	
	//总分
	private int totalScore;
	
	//一道单选题分值
	private int singleScore;
	
	//一道多选题分值
	private int multiScore;
	
	//报名人数
	private int totalNum;
	
	//实际参与人数
	private int attendNum;
	
	//及格人数
	private int passedNum;
	
	//平均分
	private double averageScore;
	
	//最高分
	private double highestScore;
	
	//最低分
	private double lowestScore;

}
