package com.luosee.common;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Created by server1 on 2016/11/17.
 */
public class SaltedUser extends User {

    private String salt;
    private String nickname;

    private String grade;

    private String certificationStatus;

    private BigInteger userId;

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public String getSalt() {
        return salt;
    }

    public String getNickname() {
        return nickname;
    }

    public String getGrade() {
        return grade;
    }

    public void setCertificationStatus(String certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public String getCertificationStatus() {
        return certificationStatus;
    }

    public SaltedUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired
            , boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String salt, String nickname, String grade, String certificationStatus, BigInteger userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.salt=salt;
        this.nickname=nickname;
        this.grade=grade;
        this.certificationStatus=certificationStatus;
        this.userId=userId;
    }
}
