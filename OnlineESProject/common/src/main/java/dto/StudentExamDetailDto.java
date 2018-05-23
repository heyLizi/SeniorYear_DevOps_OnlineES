package dto;

import lombok.Data;

import java.util.Date;


//学生考试的详细信息
@Data
public class StudentExamDetailDto {
	//试卷唯一ID
	private Integer id;

	//学生唯一id
	private String sid;
	
	//考试唯一id
	private String eid;
	
	//考试名称
	private String eName;
	
	//科目名称
	private String courseName;
	
	//开始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;
	
	//状态 1-参加考试，0-未参加
	private int state;

	//考试题目数量
	private int questionCount;
	
	//考生姓名
	private String sName;
	
	//考生学号
	private String number;
	
	//考生年级
	private String gradeName;

	//考生班级
	private String className;
	
	//考生得分
	private double score;
	
	//试卷总分
	private double totalScore;
	
	//考试实际开始时间
	private Date trueStartTime;
	
	//考试实际结束时间
	private Date trueEndTime;

}
