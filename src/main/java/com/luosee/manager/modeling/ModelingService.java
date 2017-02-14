package com.luosee.manager.modeling;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.GetObjectRequest;
import com.luosee.oss.Constant;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/22.
 */
@Service
public class ModelingService {

    @Resource(name = "modelingDao")
    private ModelingDao modelingDao;

    private Integer ttt_length;

    final org.slf4j.Logger logger = LoggerFactory.getLogger(ModelingService.class);


    List<Modeling> selectAllByStatus(Map<String,Object> parameter){return modelingDao.selectAllByStatus(parameter);}

    //打回
    public void veto(Modeling modeling){modelingDao.veto(modeling);}

    //开始制作
    public void changestatus(Modeling modeling){modelingDao.changestatus(modeling);}

    //是否上架
    public void changeProductStatus(Modeling modeling){modelingDao.changeProductStatus(modeling);}

    //计数
    public int count(Map<String,Object> parameter){return modelingDao.count(parameter);}

    //完成制作
    public void finish(Modeling modeling) {
        modelingDao.tomodal(modeling);
        modelingDao.topruduct(modeling);
    }

    //编辑
    public void edit(Modeling modeling) {
        modelingDao.tomodal(modeling);
        modelingDao.updatepruduct(modeling);
    }


    //图片打包下载
    HttpServletResponse zipDown(String[] strs, HttpServletResponse response) throws Exception {
        /*这里的 Constant.OSS_img_END_POINT = img-cn-shenzhen.aliyuncs.com
        *               不是  OSS_END_POINT = oss-cn-shenzhen.aliyuncs.com
        *                                     ↑前面的img 代表OSS的图片处理
        *                                     ↑                           */
        OSSClient client =new OSSClient(Constant.OSS_img_END_POINT,Constant.OSS_ACCESSKEY_ID,Constant.OSS_ACCESSKEY_SECRET);
        List<File> files = new ArrayList<File>();
        String filename = "myfile.zip";
        String path = "";
        for(int i =  0; i< strs.length;i++)
        {
            String[] ttt = strs[i].split(",");
            ttt_length = ttt.length-1;
            if (ttt[ttt_length].contains("风格")){
                filename = ttt[ttt_length-1];
                path = "E:/y/"+filename;
                logger.info("创建文件========【ttt[ttt_length-1]:"+ttt[ttt_length-1]+",ttt[ ttt_length]:"+ttt[ttt_length]+"=====path:"+path);
                if(CreateTxtAndWrite.creatFile(path)) {
                    if (CreateTxtAndWrite.creatTxtFile(path + "/" + ttt[ttt_length-1])) {
                        CreateTxtAndWrite.contentToTxt(path + "/" + ttt[ttt_length-1] + ".txt",URLDecoder.decode(ttt[ttt_length],"utf-8"));
                    }
                }
            }
            else{
                String key = URLDecoder.decode(ttt[ttt_length],"utf-8");
                logger.info("Arrays.toString(ttt): "+ Arrays.toString(ttt)+"===ttt.length: "+ttt.length+"===ttt[ttt.length-1]: "+ttt[ttt.length-2]+"====key: "+key);
                String downloadFilePath = path+"/"+ttt[ttt_length].substring(ttt[ttt_length].length()-8,ttt[ttt_length].length());
                files.add(new File( "E:/y/"+filename));
                client.getObject(new GetObjectRequest(Constant.OSS_BUCKET_TEST_NAME,key).<GetObjectRequest>withProgressListener(new GetObjectProgressListener()),new File(downloadFilePath));
            }
        }
        File file = new File("WoLiFang.zip");
        response = ZipandDown.downLoadFiles(files,file,response);
        ZipandDown.deleteDirectory(new File("E:/y/"));
        client.shutdown();
        return response;
    }


    //控制台下载进度条显示
    static class GetObjectProgressListener implements ProgressListener {
        private long bytesRead = 0;
        private long totalBytes = -1;
        private boolean succeed = false;
        @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
                case TRANSFER_STARTED_EVENT:
                    System.out.println("Start to download......");
                    break;
                case RESPONSE_CONTENT_LENGTH_EVENT:
                    this.totalBytes = bytes;
                    System.out.println(this.totalBytes + " bytes in total will be downloaded to a local file");
                    break;
                case RESPONSE_BYTE_TRANSFER_EVENT:
                    this.bytesRead += bytes;
                    if (this.totalBytes != -1) {
                        int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
                        System.out.println(bytes + " bytes have been read at this time, download progress: " +
                                percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
                    } else {
                        System.out.println(bytes + " bytes have been read at this time, download ratio: unknown" +
                                "(" + this.bytesRead + "/...)");
                    }
                    break;
                case TRANSFER_COMPLETED_EVENT:
                    this.succeed = true;
                    System.out.println("Succeed to download, " + this.bytesRead + " bytes have been transferred in total");
                    break;
                case TRANSFER_FAILED_EVENT:
                    System.out.println("Failed to download, " + this.bytesRead + " bytes have been transferred");
                    break;
                default:
                    break;
            }
        }
        public boolean isSucceed() {
            return succeed;
        }
    }
}
