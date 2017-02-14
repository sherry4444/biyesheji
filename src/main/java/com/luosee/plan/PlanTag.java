package com.luosee.plan;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by server1 on 2016/12/23.
 */
public class PlanTag {
    private Integer tagId;

    @NotBlank
    @Length(max = 10,message = "标签名不能超过10位")
    private String tagName;
    private Integer groupId;

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }
}
