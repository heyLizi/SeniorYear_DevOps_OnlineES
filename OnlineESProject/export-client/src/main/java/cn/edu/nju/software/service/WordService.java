package cn.edu.nju.software.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

/**
 * 类说明：导出word文件
 * 创建者：zs
 * 创建时间：2017/12/14 上午11:32
 * 包名：cn.edu.nju.software.service
 */

@Service
public class WordService {

    public void createWord(Map<String,Object> dataMap,String filePath,String fileName,String templateName){
        //相关配置
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");//模板文件所在路径
        //文件准备
        String path = filePath+File.separator+fileName;
        File outFile = new File(path); //导出文件
        //父文件夹不存在的时候创建
        if(!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
        //模板导出
        try {
            Template template = configuration.getTemplate(templateName); //获取模板文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            template.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
            //关闭流
            out.flush();
            out.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
