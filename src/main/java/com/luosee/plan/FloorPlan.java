package com.luosee.plan;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by server1 on 2016/11/22.
 */
public class FloorPlan {
    private BigInteger floorPlanId;
    private String floorPlanUrl;
    private BigInteger userId;
    private Date lastModifiedTime;
    private String title;

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setFloorPlanId(BigInteger floorPlanId) {
        this.floorPlanId = floorPlanId;
    }

    public void setFloorPlanUrl(String floorPlanUrl) {
        this.floorPlanUrl = floorPlanUrl;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public BigInteger getFloorPlanId() {
        return floorPlanId;
    }

    public String getFloorPlanUrl() {
        return floorPlanUrl;
    }
}
