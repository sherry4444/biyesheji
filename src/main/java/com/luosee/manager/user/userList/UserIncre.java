package com.luosee.manager.user.userList;

/**
 * Created by server2 on 2016/12/14.
 */

//用户情况实体类
public class UserIncre {
    private Integer Id;
    private Integer countAll;//总用户数
    private Integer countYester;//昨日新增用户数
    private Integer countToday;//今日新增用户数

    public UserIncre(){}

    public UserIncre(Integer countAll, Integer countYester, Integer countToday) {
        this.countAll = countAll;
        this.countYester = countYester;
        this.countToday = countToday;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }

    public Integer getCountYester() {
        return countYester;
    }

    public void setCountYester(Integer countYester) {
        this.countYester = countYester;
    }

    public Integer getCountToday() {
        return countToday;
    }

    public void setCountToday(Integer countToday) {
        this.countToday = countToday;
    }

    @Override
    public String toString() {
        return "UserIncre{" +
                "Id=" + Id +
                ", countAll=" + countAll +
                ", countYester=" + countYester +
                ", countToday=" + countToday +
                '}';
    }
}
