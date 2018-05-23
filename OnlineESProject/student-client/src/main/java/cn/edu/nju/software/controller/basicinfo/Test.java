package cn.edu.nju.software.controller.basicinfo;

import com.google.common.collect.Maps;

import java.util.Base64;
import java.util.Map;

/**
 * Created by mengf on 2017/12/14 0014.
 */
public class Test {

    public static void main(String[] args){
        String code = "ZWlkPTEmc2lkPTE=";
        Map<String,Long> params = Maps.newHashMap();
        byte[] bytes = Base64.getDecoder().decode(code);
        String[] vars = new String(bytes).split("&");
        for (String var : vars){
            String[] data = var.split("=");
            params.put(data[0],Long.parseLong(data[1]));
        }
        System.out.println(params.get("sid"));
        System.out.println(params.get("eid"));
    }
}
