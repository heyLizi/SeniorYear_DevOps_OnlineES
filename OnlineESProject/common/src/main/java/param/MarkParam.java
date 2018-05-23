package param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by mengf on 2017/12/10 0010.
 */
@Data
public class MarkParam {
    //学生ID
    private Long studentId;
    //考试ID
    private Long examId;
    //试卷ID
    private Long paperId;
    //试题ID
    private Long exerciseId;
    //标记状态 0未标记 1标记 未使用boolean为了万一将来出现多个状态
    private Integer state;
}
