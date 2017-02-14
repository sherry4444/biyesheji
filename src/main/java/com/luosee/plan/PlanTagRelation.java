package com.luosee.plan;

/**
 * Created by server1 on 2016/12/23.
 */
public class PlanTagRelation {
    private Integer ralationId;
    private Integer tagId;
    private Integer planId;

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public void setRalationId(Integer ralationId) {
        this.ralationId = ralationId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public Integer getRalationId() {
        return ralationId;
    }

    public Integer getTagId() {
        return tagId;
    }
}
