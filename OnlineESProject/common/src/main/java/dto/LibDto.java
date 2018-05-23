package dto;

import lombok.Data;
import java.util.Date;

//题库简略信息
@Data
public class LibDto {

	//唯一id
	private String id;
	
	//题库名称
	private String name;
	
	//题目数量
	private int count;
	
	//创建时间
	private Date createTime;

	//最后修改时间
	private Date modifyTime;

}
