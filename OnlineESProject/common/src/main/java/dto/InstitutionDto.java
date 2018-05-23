package dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mengf on 2017/11/12 0012.
 */

@Data
public class InstitutionDto {
    private Long schoolId;
    private String schoolName;
    private Long id;
    private String name;

    public InstitutionDto() {
    }

    public InstitutionDto(Long schoolId, String schoolName, Long id, String name) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.id = id;
        this.name = name;
    }
}
