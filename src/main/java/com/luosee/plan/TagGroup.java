package com.luosee.plan;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by server1 on 2016/12/23.
 */
public class TagGroup {
    private Integer groupId;

    @NotBlank
    @Length(max = 10,message = "标签名不能超过10位")
    private String groupName;

    private Integer userId;

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public Integer getUserId() {
        return userId;
    }
}
