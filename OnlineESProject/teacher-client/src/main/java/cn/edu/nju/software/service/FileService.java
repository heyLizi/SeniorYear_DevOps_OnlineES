package cn.edu.nju.software.service;

import com.alibaba.fastjson.JSON;
import common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 文件上传下载逻辑层
 */
@Service
@Slf4j
public class FileService {

    /**
     * 下载对应的模板文件
     * @param response
     * @param filePath
     * @param filename
     */
    public void downloadTemplate(HttpServletResponse response,String filePath,String filename){
        log.info("----------file download start " + filename);
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:"+filePath+"/"+filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(file.exists()){ //判断文件父目录是否存在
            //设置文件MIME类型和header
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = new BufferedOutputStream(response.getOutputStream());
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer,0,i);
                    os.flush();
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("----------file download finish");
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            log.error("file: "+filePath+" is not existed.");
        }
    }

    /**
     * 上传Excel文件
     * @param file
     * @param rootPath
     * @return
     */
    public String uploadExcel(MultipartFile file,String rootPath){
        if(file == null || file.isEmpty()){
            return JSON.toJSONString(Result.error().errorMessage("文件为空！"));
        }
        String contentType = file.getContentType();
        if(!contentType.equals("application/vnd.ms-excel") && !contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return JSON.toJSONString(Result.error().errorMessage("文件格式不正确！"));
        }
        File dir = new File(rootPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String path = rootPath+"/"+uuid+"_"+fileName;
        try {
            file.transferTo(new File(path));
        }catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(Result.error().errorMessage("文件上传失败！"));
        }
        return "success+"+path;
    }

    /**
     * 下载文件
     * @param response
     * @param path
     */
    public void downloadFile(HttpServletResponse response, String path,boolean isZip) {
        File file = new File(path);
        if(file.exists()){ //判断文件父目录是否存在
            int location = path.lastIndexOf("/");
            String filename = path.substring(location+1);
            //设置文件MIME类型和header
            response.setContentType("application/force-download");
            if(isZip){
                response.setContentType("application/octet-stream");
            }
            try {
                filename = new String(filename.getBytes("utf-8"),"ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = new BufferedOutputStream(response.getOutputStream());
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer,0,i);
                    os.flush();
                    i = bis.read(buffer);
                }
                bis.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("----------file download finish");
        }else{
            log.error("file: "+path+" is not existed.");
        }
        file.delete();
    }
}
