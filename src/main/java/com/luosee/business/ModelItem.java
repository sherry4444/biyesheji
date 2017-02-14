package com.luosee.business;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by server1 on 2016/11/24.
 */
public class ModelItem {
    private List<String> modelIdList;

    private List<String> positionName;

    private List<MultipartFile> modelImgFile;

    public void setModelImgFile(List<MultipartFile> modelImgFile) {
        this.modelImgFile = modelImgFile;
    }

    public void setPositionName(List<String> positionName) {
        this.positionName = positionName;
    }

    public void setModelIdList(List<String> modelIdList) {
        this.modelIdList = modelIdList;
    }

    public List<String> getModelIdList() {
        return modelIdList;
    }

    public List<String> getPositionName() {
        return positionName;
    }

    public List<MultipartFile> getModelImgFile() {
        return modelImgFile;
    }
}
