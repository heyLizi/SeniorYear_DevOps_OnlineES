package dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mengf on 2017/11/12 0012.
 */

@Data
public class GradeDto {
    private Long schoolId;
    private String schoolName;
    private Long institutionId;
    private String institutionName;
    private Long id;
    private String name;
}
