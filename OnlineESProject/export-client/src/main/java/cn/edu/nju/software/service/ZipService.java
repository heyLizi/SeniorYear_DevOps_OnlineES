package cn.edu.nju.software.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 类说明：zip文件生成
 * 创建者：zs
 * 创建时间：2017/12/14 上午11:32
 * 包名：cn.edu.nju.software.service
 */

@Service
public class ZipService {

    @Value("${file.export.zipPath}")
    private String filePath;

    /**
     *  压缩并导出文件
     * @param zipName 压缩为文件名 **.zip
     * @param fileList 需要压缩的文件列表
     * @return
     */
    public String exportZip(String zipName, List<File> fileList) {
        String strZipPath=filePath+"/"+zipName;
        File outFile = new File(strZipPath); //导出文件
        //父文件夹不存在的时候创建
        if(!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
        try {
            //文件不存在创建
            if (!outFile.exists()){
                outFile.createNewFile();
            }
            //输出压缩流
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));

            for(File file : fileList){
                if(file.exists()){
                    FileInputStream fis = new FileInputStream(file);
                    out.putNextEntry(new ZipEntry(file.getName()));
                    int len;
                    byte[] buffer = new byte[1024];
                    // 读入需要下载的文件的内容，打包到zip文件
                    while ((len = fis.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    out.closeEntry();
                    fis.close();
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除导出文件
        for(File file : fileList){
            file.delete();
        }
        return strZipPath;
    }

}
