package dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import java.util.Date;

//从后台传到教师查考试界面数据
@Data
public class ExamDto {
	//某场考试独一无二的id，可以用作数据库查询的那种
	private String id;

	//课程名
	private String courseName;

	//考试时长
	private int period;

	//考试名称
	private String name;

	//开始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;
	
	//试题数
	private int questionCount;
	
	//考试人数(报名人数)
	private int studentCount;

}
