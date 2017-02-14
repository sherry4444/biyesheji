package com.luosee.manager.modeling;

import com.aliyun.oss.OSSClient;
import com.luosee.common.Utils;
import com.luosee.oss.Dataformal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by server2 on 2017/1/11.
 */
public class UploadFile {

    private String filepath;

    private final Logger logger = LoggerFactory.getLogger(UploadFile.class);

    public String Uploadimg(MultipartFile file,String path,String datasource)
    {
        OSSClient client = Utils.getOSSClient();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS");
        filepath = path+"/img_"+simpleDateFormat.format(new Date())+"_"+file.getOriginalFilename();
        try {
            client.putObject(Utils.getOSSBucket(Dataformal.IsFormal(datasource)),filepath, file.getInputStream());
            logger.info("模型图片上传成功:"+filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filepath;
    }

    public String Uploadfile(MultipartFile file,String path,String datasource)
    {
        OSSClient client = Utils.getOSSClient();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSS");
        filepath = path+"/"+file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
        try {
            client.putObject(Utils.getOSSBucket(Dataformal.IsFormal(datasource)),filepath, file.getInputStream());
            logger.info("模型文件上传成功:"+filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filepath;
    }


}
