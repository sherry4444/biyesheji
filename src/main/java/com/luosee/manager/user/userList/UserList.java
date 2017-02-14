package com.luosee.manager.user.userList;

import java.sql.Timestamp;

/**
 * Created by server2 on 2016/12/10.
 */
public class UserList {

    private Integer userId;//用户ID
    private String username;//用户名
    private String nickname;//昵称
    private Timestamp createTime;//注册时间
    private Integer planNum;//方案数量
    private String imagePath;//预览图

    public UserList(){

    }

    public UserList(Integer userId, String username, Timestamp createTime, Integer planNum, String imagePath) {
        this.userId = userId;
        this.username = username;
        this.createTime = createTime;
        this.planNum = planNum;
        this.imagePath = imagePath;
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

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
