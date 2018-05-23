package common;

/**
 * 类说明：
 * 创建者：zs
 * 创建时间：2017/12/7 上午12:09
 * 包名：common
 */

public class SystemConst {
    //文件上传路径
    public static final String FILE_PATH = "D:/test";

    //列表显示默认页数
    public static final int PAGE_SIZE = 5;


    //学生成绩显示默认页数
    public static final int STUDENT_PAGE_SIZE = 12;

    //考试密码长度
    public static final int PASSWORD_LENGTH = 8;

    //考试链接前缀
    public static final String URL_PREFIX = "http://127.0.0.1:9001/OnlineES/exam/";

    //考试邮件标题
    public static final String MAIL_CONTENT = "OnlineExamination 考试提醒";

    //题目类型
    public static final int SINGLE_QUESTION_TYPE = 1;
    public static final int MULTI_QUESTION_TYPE = 2;
}
