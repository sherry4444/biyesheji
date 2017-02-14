package com.luosee.manager.user.plan;

import com.aliyun.oss.OSSClient;
import com.luosee.oss.Dataformal;
import com.luosee.common.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by server2 on 2016/12/1.
 */
@Service
public class PlanListService {

    @Resource(name = "planListDao")
    private PlanListDao planListDao;
    @Value("${dataSource.url}")
    private String DataSourceHolder;
    private Integer newid;
    private String title;

    public void copyaccount(String copyusername, String oldid, String oldplanid) {

        OSSClient client = Utils.getOSSClient();

        /*通过要复制的用户名找到复制人的ID*/
        newid = planListDao.getUserIdByName(copyusername);
        /*用要复制的用户ID和方案ID找到要复制的信息*/
        Map<String,Object> parameter = new HashMap<String, Object>();
        parameter.put("oldid",oldid);
        parameter.put("oldplanid",oldplanid);
        PlanList toBeCopied = (PlanList) planListDao.selectToCopy(parameter);
        title = toBeCopied.getTitle();

        /*生成文件名*/
        String newScreenShotFilename = Utils.generateOSSSreenshotFileName(String.valueOf(newid),Utils.getFileExtension(toBeCopied.getImagePath()));
        String newFloorPlanFilename = Utils.generateOSSFloorplanFileName(String.valueOf(newid));
        String newPlanFilename = Utils.generateOSSPlanFileName(String.valueOf(newid));

       try {
             /*在OSS上进行复制*/
           client.copyObject(
                   Utils.getOSSBucket(Dataformal.IsFormal(DataSourceHolder)),
                   Utils.decodeOSSURL(toBeCopied.getImagePath()),
                   Utils.getOSSBucket(Dataformal.IsFormal(DataSourceHolder)),
                   newScreenShotFilename);//copy screenshot

           client.copyObject(Utils.getOSSBucket(Dataformal.IsFormal(DataSourceHolder)),
                   Utils.decodeOSSURL(toBeCopied.getFloorPlanUrl()),
                   Utils.getOSSBucket(Dataformal.IsFormal(DataSourceHolder)),
                   newFloorPlanFilename);//copy floorplan

           client.copyObject(Utils.getOSSBucket(Dataformal.IsFormal(DataSourceHolder)),
                   Utils.decodeOSSURL(toBeCopied.getDownloadU3DUrl()),
                   Utils.getOSSBucket(Dataformal.IsFormal(DataSourceHolder)),
                   newPlanFilename);//copy u3d

        /*将新复制的信息插入到数据库*/
           //copyinsert(newScreenShotFilename,newFloorPlanFilename,newPlanFilename);
           PlanList myfloorplan = new PlanList();
           myfloorplan.setUserId(newid);
           myfloorplan.setFloorPlanUrl(newFloorPlanFilename);
           planListDao.insertMyfloorplan(myfloorplan);

           int floorPlanId = planListDao.getfloorIdByUrl(newFloorPlanFilename);
           System.out.println("=========newfloorPlanId:============" + floorPlanId);

           PlanList myplan = new PlanList();
           myplan.setUserId(newid);
           myplan.setTitle(title+"(Copy)");
           myplan.setImagePath(newScreenShotFilename);
           myplan.setFloorPlanId(floorPlanId);
           myplan.setDownloadU3DUrl(newPlanFilename);
           planListDao.insertMyplan(myplan);
       }catch (Exception e)
       {
           e.printStackTrace();
       }finally {
           client.shutdown();
       }




    }


//     public void copyinsert(String newScreenShotFilename,String newFloorPlanFilename,String newPlanFilename)
//     {
//         PlanList myfloorplan = new PlanList();
//         myfloorplan.setUserId(newid);
//         myfloorplan.setFloorPlanUrl(newFloorPlanFilename);
//         planListDao.insertMyfloorplan(myfloorplan);
//
//         int floorPlanId = planListDao.getfloorIdByUrl(newFloorPlanFilename);
//         System.out.println("=========newfloorPlanId:============" + floorPlanId);
//
//         PlanList myplan = new PlanList();
//         myplan.setUserId(newid);
//         myplan.setTitle(title);
//         myplan.setImagePath(newScreenShotFilename);
//         myplan.setFloorPlanId(floorPlanId);
//         myplan.setDownloadU3DUrl(newPlanFilename);
//         planListDao.insertMyplan(myplan);
//     }

}





