package dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mengf on 2017/11/12 0012.
 */


@Data
public class ClassDto {
    private Long schoolId;
    private String schoolName;
    private Long institutionId;
    private String institutionName;
    private Long gradeId;
    private String gradeName;
    private Long id;
    private String name;
}
