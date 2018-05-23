package dto;

import lombok.Data;

/**
 * 某教师的课程信息
 */
@Data
public class CourseDto {

	//唯一id
	private String id;
	
	//课程名字
	private String name;

	//课程类型名称
	private String courseTypeName;
	
	//学院名称
	private String institutionName;
	
	//课程的学年以及学期
	private String period;
	
	//学分
	private int score;
}
