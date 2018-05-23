package param;

import dto.QuestionDto;
import lombok.Data;

import java.util.List;

/**
 * 类说明：
 * 创建者：zs
 * 创建时间：2017/12/9 下午5:52
 * 包名：param
 */

@Data
public class UploadQuestionParam {

    //题库ID
    private Integer lid;
    //题目
    private List<QuestionDto> questionDtos;

}
