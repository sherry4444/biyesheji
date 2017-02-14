package com.luosee.user;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by server1 on 2016/11/15.
 */
public class TbUser {
    private BigInteger userId;

    @Pattern(regexp = "(^[0-9]{11}$)|(^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$)",message = "请输入手机或者邮箱")
    @Length(max = 40,message = "最大长度为40位")
    @NotEmpty
    private String username;

    @Pattern(regexp = "^[a-z0-9_-]{6,44}$",message = "密码格式错误")
    @Length(min=6,max = 44,message = "长度为6到44位")
    private String password;

    private String salt;

    private Boolean verifyEmail;

    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",message = "邮箱格式错误")
    @Length(max = 40,message = "最大长度为40位")
    private String email;

    @Pattern(regexp = "^[0-9]{11}$",message = "手机格式错误")
    @Length(max = 20,message = "最大长度为20位")
    private String mobile;

    private Character accountType;

    private Date createTime;

    private Integer role;

    private String grade;

    @Length(max = 40,message = "最大长度为40位")
    private String nickname;

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setVerifyEmail(Boolean verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public Boolean getVerifyEmail() {
        return verifyEmail;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setAccountType(Character accountType) {
        this.accountType = accountType;
    }

    public Character getAccountType() {
        return accountType;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getRole() {
        return role;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
