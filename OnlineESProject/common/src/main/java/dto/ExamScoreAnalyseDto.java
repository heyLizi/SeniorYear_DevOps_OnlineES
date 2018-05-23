package dto;

import lombok.Data;

/**
 * 类说明：考试成绩统计
 * 创建者：zs
 * 创建时间：2017/12/12 下午5:22
 * 包名：dto
 */

@Data
public class ExamScoreAnalyseDto {
    //最高分
    private int maxScore;
    //最低分
    private int minScore;
    //平均分
    private double averageScore;


}
