package dto;

import lombok.Data;

@Data
public class QuestionCurDto {
    //当前位于第几题（一开始肯定是第一题，但是后来掉线重进可能就不是第一题了.下标从0开始）
    private int cur;

    //当前题的内容
    private String content;

    //当前题的选项
    private String[] options;

    //已经选择的选项，ABCD，逗号隔开,没选就是空的
    private String selections;
}
