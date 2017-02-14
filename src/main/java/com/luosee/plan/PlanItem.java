package com.luosee.plan;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by server1 on 2016/12/24.
 */
public class PlanItem {
    private List<BigInteger> planIds;

    private List<Integer> tags;

    private List<PlanTagRelation> relation;

    private List<PlanTagRelation> newRelation;


    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public void setPlanIds(List<BigInteger> planIds) {
        this.planIds = planIds;
    }

    public void setNewRelation(List<PlanTagRelation> newRelation) {
        this.newRelation = newRelation;
    }

    public void setRelation(List<PlanTagRelation> relation) {
        this.relation = relation;
    }

    public List<BigInteger> getPlanIds() {
        return planIds;
    }

    public List<PlanTagRelation> getRelation() {
        return relation;
    }

    public List<PlanTagRelation> getNewRelation() {
        return newRelation;
    }

    public List<Integer> getTags() {
        return tags;
    }
}
