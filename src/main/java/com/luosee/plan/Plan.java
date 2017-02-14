package com.luosee.plan;

import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by server1 on 2016/11/22.
 */
public class Plan {
    private BigInteger planId;
    private BigInteger userId;
    private String title;
    private String imagePath;
    private String description;
    private String downloadU3DUrl;
    private Date createTime;
    private Date lastModifiedTime;
    private BigInteger floorPlanId;
    private Integer isPublic;

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDownloadU3DUrl(String downloadU3DUrl) {
        this.downloadU3DUrl = downloadU3DUrl;
    }

    public void setFloorPlanId(BigInteger floorPlanId) {
        this.floorPlanId = floorPlanId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setPlanId(BigInteger planId) {
        this.planId = planId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public BigInteger getFloorPlanId() {
        return floorPlanId;
    }

    public BigInteger getPlanId() {
        return planId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public String getDescription() {
        return description;
    }

    public String getDownloadU3DUrl() {
        return downloadU3DUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }
}
