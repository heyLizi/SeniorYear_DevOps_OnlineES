package dto;

import lombok.Data;

@Data
public class ExamCurDto {
    //当前位于第几题（一开始肯定是第一题，但是后来掉线重进可能就不是第一题了.下标从0开始）
    private int cur;

    //当前题的内容
    private String content;

    //当前题的选项
    private String[] options;

    //每题的答题情况，已答还是未答，已答的选项是什么，是否标记
    private QuestionDto[] infos;

    //离考试结束还有多少秒
    private int sec;
}
