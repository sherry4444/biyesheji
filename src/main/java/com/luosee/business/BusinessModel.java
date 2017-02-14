package com.luosee.business;

import com.luosee.product.style.Style;
import com.luosee.product.type.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by server1 on 2016/11/21.
 */
public class BusinessModel {
    private BigInteger modelId;
    private Integer status;
    private Integer makerId;
    private Integer reviewerId;
    private Timestamp createTime;
    private Timestamp startMakeTime;
    private Timestamp finishMaketime;
    private Timestamp lastReviewTime;
    private Timestamp predictMakeDay;
    private BigInteger businessUserId;

    @Length(max = 40,message = "模型名最长为40位")
    @NotEmpty
    private String modelName;

    private String genAssetName;
    private BigInteger styleId;
    private BigInteger typeId;
    private String modelImages;
    private Type type;
    private Style style;

    private String description;

    private String filepath;
    private String introduceImg;

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setIntroduceImg(String introduceImg) {
        this.introduceImg = introduceImg;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private BusinessSubUser businessSubUser;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBusinessUserId(BigInteger businessUserId) {
        this.businessUserId = businessUserId;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setFinishMaketime(Timestamp finishMaketime) {
        this.finishMaketime = finishMaketime;
    }

    public void setGenAssetName(String genAssetName) {
        this.genAssetName = genAssetName;
    }

    public void setLastReviewTime(Timestamp lastReviewTime) {
        this.lastReviewTime = lastReviewTime;
    }

    public void setMakerId(Integer makerId) {
        this.makerId = makerId;
    }

    public void setModelId(BigInteger modelId) {
        this.modelId = modelId;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setPredictMakeDay(Timestamp predictMakeDay) {
        this.predictMakeDay = predictMakeDay;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public void setStartMakeTime(Timestamp startMakeTime) {
        this.startMakeTime = startMakeTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setBusinessSubUser(BusinessSubUser businessSubUser) {
        this.businessSubUser = businessSubUser;
    }

    public void setTypeId(BigInteger typeId) {
        this.typeId = typeId;
    }

    public void setStyleId(BigInteger styleId) {
        this.styleId = styleId;
    }

    public void setModelImages(String modelImages) {
        this.modelImages = modelImages;
    }

    public Style getStyle() {
        return style;
    }

    public Type getType() {
        return type;
    }

    public String getGenAssetName() {
        return genAssetName;
    }

    public BigInteger getBusinessUserId() {
        return businessUserId;
    }

    public BigInteger getModelId() {
        return modelId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getFinishMaketime() {
        return finishMaketime;
    }

    public Timestamp getLastReviewTime() {
        return lastReviewTime;
    }

    public Timestamp getPredictMakeDay() {
        return predictMakeDay;
    }

    public Timestamp getStartMakeTime() {
        return startMakeTime;
    }

    public Integer getMakerId() {
        return makerId;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public Integer getStatus() {
        return status;
    }

    public String getModelName() {
        return modelName;
    }

    public BigInteger getStyleId() {
        return styleId;
    }

    public BigInteger getTypeId() {
        return typeId;
    }

    public String getModelImages() {
        return modelImages;
    }

    public BusinessSubUser getBusinessSubUser() {
        return businessSubUser;
    }

    public String getDescription() {
        return description;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getIntroduceImg() {
        return introduceImg;
    }
}
