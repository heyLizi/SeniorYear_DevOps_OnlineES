package param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by mengf on 2017/11/12 0012.
 */
@Getter
@Setter
public class RegisterParam {
    @NotBlank
    private String name;
    @NotNull
    private Long schoolId;
    @NotBlank
    private String studentId;
    @NotNull
    private Long institutionId;
    @NotNull
    private Long gradeId;
    @NotNull
    private Long classId;
    @NotBlank
    private String mail;
}
