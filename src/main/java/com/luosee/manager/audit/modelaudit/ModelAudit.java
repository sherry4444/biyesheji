package com.luosee.manager.audit.modelaudit;

import java.sql.Timestamp;

/**
 * Created by server2 on 2016/12/23.
 */
public class ModelAudit {
    private Integer modelId;//模型ID
    private Integer status;//制作状态（0：等待资料审核, 1：确认制作费, 2：确认制作, 3：等待制作, 4：制作中, 5：等待审核中, 6：审核打回, 7：制作完成, 8：制作失败）
    private Integer makerId;//制作员ID
    private Integer reviewerId;//审核员ID
    private Timestamp createTime;//创建时间
    private Timestamp startMakeTime;//开始制作时间
    private Timestamp finishMaketime;//完成制作时间
    private Timestamp lastReviewTime;//最后修改时间
    private Timestamp predictMakeDay;//预估工作时间
    private Integer businessUserId;//商户名称
    private String modelName;//模型名称
    private String genAssetName;//生成的assetname
    private Integer styleId;//风格ID
    private Integer typeId;//分类ID
    private String modelImages;//模型图片
    private String description;//描述
    private String vetoReason;//打回原因

    public ModelAudit(){}

    public ModelAudit(Integer modelId, Integer status, Integer makerId, Integer reviewerId, Timestamp createTime, Timestamp startMakeTime, Timestamp finishMaketime, Timestamp lastReviewTime, Timestamp predictMakeDay, Integer businessUserId, String modelName, String genAssetName, Integer styleId, Integer typeId, String modelImages, String description, String vetoReason) {
        this.modelId = modelId;
        this.status = status;
        this.makerId = makerId;
        this.reviewerId = reviewerId;
        this.createTime = createTime;
        this.startMakeTime = startMakeTime;
        this.finishMaketime = finishMaketime;
        this.lastReviewTime = lastReviewTime;
        this.predictMakeDay = predictMakeDay;
        this.businessUserId = businessUserId;
        this.modelName = modelName;
        this.genAssetName = genAssetName;
        this.styleId = styleId;
        this.typeId = typeId;
        this.modelImages = modelImages;
        this.description = description;
        this.vetoReason = vetoReason;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMakerId() {
        return makerId;
    }

    public void setMakerId(Integer makerId) {
        this.makerId = makerId;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getStartMakeTime() {
        return startMakeTime;
    }

    public void setStartMakeTime(Timestamp startMakeTime) {
        this.startMakeTime = startMakeTime;
    }

    public Timestamp getFinishMaketime() {
        return finishMaketime;
    }

    public void setFinishMaketime(Timestamp finishMaketime) {
        this.finishMaketime = finishMaketime;
    }

    public Timestamp getLastReviewTime() {
        return lastReviewTime;
    }

    public void setLastReviewTime(Timestamp lastReviewTime) {
        this.lastReviewTime = lastReviewTime;
    }

    public Timestamp getPredictMakeDay() {
        return predictMakeDay;
    }

    public void setPredictMakeDay(Timestamp predictMakeDay) {
        this.predictMakeDay = predictMakeDay;
    }

    public Integer getBusinessUserId() {
        return businessUserId;
    }

    public void setBusinessUserId(Integer businessUserId) {
        this.businessUserId = businessUserId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getGenAssetName() {
        return genAssetName;
    }

    public void setGenAssetName(String genAssetName) {
        this.genAssetName = genAssetName;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getModelImages() {
        return modelImages;
    }

    public void setModelImages(String modelImages) {
        this.modelImages = modelImages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVetoReason() {
        return vetoReason;
    }

    public void setVetoReason(String vetoReason) {
        this.vetoReason = vetoReason;
    }
}
