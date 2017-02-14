package com.luosee.manager.user.plan;

import java.sql.Timestamp;

/**
 * Created by server2 on 2016/11/22.
 *
 planId
 userId
 title
 imagePath
 description
 downloadU3DUrl
 createTime
 lastModifiedTime
 floorPlanId
 isPublic

 *
 */
public class PlanList {

    private Integer planId;//方案id
    private String title;//方案名
    private Integer userId;//用户id
    private String username;//用户
    private String nickname;//昵称
    private Timestamp createTime;//创建时间
    private Timestamp lastModifiedTime;//最后更新时间
    private String imagePath;//预览图下载地址
    private Integer isPublic;//是否公开
    private String downloadU3DUrl;//方案下载地址
    private Integer floorPlanId;//户型ID
    private String floorPlanUrl;//户型下载地址


    public PlanList(){

   }

    public PlanList(Integer planId, String title, String username, Timestamp createTime, Timestamp lastModifiedTime, String imagePath, Integer isPublic) {
        this.planId = planId;
        this.title = title;
        this.username = username;
        this.createTime = createTime;
        this.lastModifiedTime = lastModifiedTime;
        this.imagePath = imagePath;
        this.isPublic = isPublic;
    }

    public PlanList(String title, Integer userId, String imagePath, String downloadU3DUrl, Integer floorPlanId) {
        this.title = title;
        this.userId = userId;
        this.imagePath = imagePath;
        this.downloadU3DUrl = downloadU3DUrl;
        this.floorPlanId = floorPlanId;
    }

    public PlanList(Integer userId, String floorPlanUrl) {
        this.userId = userId;
        this.floorPlanUrl = floorPlanUrl;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Timestamp lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public String getDownloadU3DUrl() {
        return downloadU3DUrl;
    }

    public void setDownloadU3DUrl(String downloadU3DUrl) {
        this.downloadU3DUrl = downloadU3DUrl;
    }

    public Integer getFloorPlanId() {
        return floorPlanId;
    }

    public void setFloorPlanId(Integer floorPlanId) {
        this.floorPlanId = floorPlanId;
    }

    public String getFloorPlanUrl() {
        return floorPlanUrl;
    }

    public void setFloorPlanUrl(String floorPlanUrl) {
        this.floorPlanUrl = floorPlanUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
