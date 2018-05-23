package common;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by mengf on 2017/11/12 0012.
 */
@Data
public class Result {
    //请求是否成功
    private boolean success;
    //返回码(正确返回0，错误返回1)
    private String code;
    //返回信息
    private String message;
    //返回错误信息
    private String errorMessage;
    //如果错误信息是list返回错误信息列表
    private List<String> errorMessageList;
    //返回的主要数据对象
    private Object data;
    
    public boolean isSuccess() {
        return success;
    }

    public Result() {
    }

    private Result(boolean success) {
        this.success = success;
    }

    public static Result success() {
        return new Result(true);
    }

    public static Result error() {
        return new Result(false);
    }

    public Result withData(Object data) {
        this.data = data;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result code(String code) {
        this.code = code;
        return this;
    }

    public Result errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Result errorMessageList(List<String> errorMessages) {
        this.errorMessageList = errorMessages;
        return this;
    }

    public static void sendResult(HttpServletResponse response, Result r) {
        try {
            PrintWriter p = response.getWriter();
            p.print(JSON.toJSONString(r));
            p.flush();
            p.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(HttpServletResponse response, String jsonString) {
        try {
            PrintWriter p = response.getWriter();
            p.print(jsonString);
            p.flush();
            p.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
