package dto;

import lombok.Data;

//题库的名字和id
@Data
public class LibBriefDto {
	//唯一id
	private String id;

	//题库名字
	private String name;
	
	//单选题数量
	private int single;

	//多选题数量
	private int multi;
}
