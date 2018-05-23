package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mengf on 2017/11/8 0008.
 */
public class DateUtil {

    /*--------------------- 时间样式模板 --------------------- */
    // 时间精确到秒
    public static final String TIME_PATTERN_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN_dd = "yyyyMMdd";

    public static String parseTimeToString(Date time){
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN_ss);
        return sdf.format(time);
    }

    public static String parseTodayToString(){
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN_dd);
        return sdf.format(new Date());
    }

}
