package com.luosee.oss;

import com.aliyun.oss.OSSClient;
import com.luosee.common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by server1 on 2016/11/25.
 */
public class UploadImage {

    final Logger logger= LoggerFactory.getLogger(UploadImage.class);
    public String uploadFile(MultipartFile sellerImgFile,String datasource,Object prefix,String folder)
    {
        String filename = null;
        if(!sellerImgFile.getOriginalFilename().equals(""))
        {
            OSSClient client = Utils.getOSSClient();
            try {
                filename=fileName(sellerImgFile.getOriginalFilename(),prefix);
                client.putObject(Utils.getOSSBucket(Dataformal.IsFormal(datasource)),folder+filename, sellerImgFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.shutdown();
        }
        return folder+filename;
    }

    public String fileName(String fileName,Object prefix)
    {
        String type=fileName.substring(fileName.lastIndexOf("."));
        return Utils.generateFileName(String.valueOf(prefix)).concat(type);
    }


    public void deleteFile(String datasource,String fileName)
    {
        if(fileName != null && fileName != "")
        {
            OSSClient client = Utils.getOSSClient();
            client.deleteObject(Utils.getOSSBucket(Dataformal.IsFormal(datasource)),fileName);
            client.shutdown();
        }
    }
}
