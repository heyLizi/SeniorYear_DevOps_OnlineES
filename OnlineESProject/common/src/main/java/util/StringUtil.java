package util;

import java.util.Random;

/**
 * Created by mengf on 2017/11/8 0008.
 */
public class StringUtil {

    /**
     * 生成长度一定的随机字符串
     * @param length
     * @return
     */
    public static String ramdomStr(int length){
        if(length < 1){
            length = 8;
        }
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
