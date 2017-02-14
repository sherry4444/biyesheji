package com.luosee.common;

import java.util.Map;

/**
 * Created by server1 on 2016/11/22.
 */
public class QualifyInfo {
    private String username;
    private String qualifyContent;
    private Map<String,Object> queryField;

    public void setQueryField(Map<String, Object> queryField) {
        this.queryField = queryField;
    }

    public void setQualifyContent(String qualifyContent) {
        this.qualifyContent = qualifyContent;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQualifyContent() {
        return qualifyContent;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Object> getQueryField() {
        return queryField;
    }
}
