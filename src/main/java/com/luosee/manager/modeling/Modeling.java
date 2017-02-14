package com.luosee.manager.modeling;

import com.luosee.product.tag.Tag;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by server2 on 2016/12/23.
 */
public class Modeling {
    private String modelId;//模型ID
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
    private String styleName;//风格名字
    private Integer typeId;//分类ID
    private String typeName;//分类名字
    private String modelImages;//模型图片
    private String description;//描述
    private String vetoReason;//打回原因
    private Integer attachType;
    private Integer scaleType;
    private Integer rotateType;
    private String tagNames;
    private List<Tag> tags;
    private String introduceImg;
    private String filepath;
    private String productStatus;
    private String certificationStatus;

    @Transient
    private int scaleX;

    @Transient
    private int scaleY;

    @Transient
    private int scaleZ;

    public Modeling(){}

    public Modeling(String modelId, Integer status, Integer makerId, Integer reviewerId, Timestamp createTime, Timestamp startMakeTime, Timestamp finishMaketime, Timestamp lastReviewTime, Timestamp predictMakeDay, Integer businessUserId, String modelName, String genAssetName, Integer styleId, Integer typeId, String modelImages, String description, String vetoReason) {
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
        updateScaleValues();
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
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

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getAttachType() {
        return attachType;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }

    public Integer getScaleType() {
        return scaleType;
    }

    public void setScaleType(Integer scaleType) {
        this.scaleType = scaleType;
        updateScaleValues();
    }

    public Integer getRotateType() {
        return rotateType;
    }

    public void setRotateType(Integer rotateType) {
        this.rotateType = rotateType;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getIntroduceImg() {
        return introduceImg;
    }

    public void setIntroduceImg(String introduceImg) {
        this.introduceImg = introduceImg;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getCertificationStatus() {
        return certificationStatus;
    }

    public void setCertificationStatus(String certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public int getScaleX() {
        return scaleX;
    }

    public int getScaleY() {
        return scaleY;
    }

    public int getScaleZ() {
        return scaleZ;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
        calcScaleType();
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
        calcScaleType();
    }

    public void setScaleZ(int scaleZ) {
        this.scaleZ = scaleZ;
        calcScaleType();
    }
    @PostLoad
    private void initValues() {
        updateScaleValues();
    }

    @PreUpdate
    @PrePersist
    private void setTagNames() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tags.size(); i++) {
            if(i > 0) {
                sb.append("," + tags.get(i).getTagName());
            }
            else {
                sb.append(tags.get(i).getTagName());
            }
        }
        tagNames = sb.toString();
    }

    private void updateScaleValues() {
        scaleX = (short) (scaleType & 0xF);
        scaleY = (short) ((scaleType & 0xF0) >> 4);
        scaleZ = (short) ((scaleType & 0xF00) >> 8);
    }

    private void calcScaleType() {
        scaleType = (scaleX & 0xF) + ((scaleY << 4) & 0xF0) + ((scaleZ << 8) & 0xF00);
    }


    @Override
    public String toString() {
        return "Modeling{" +
                "modelId=" + modelId +
                ", status=" + status +
                ", makerId=" + makerId +
                ", reviewerId=" + reviewerId +
                ", createTime=" + createTime +
                ", startMakeTime=" + startMakeTime +
                ", finishMaketime=" + finishMaketime +
                ", lastReviewTime=" + lastReviewTime +
                ", predictMakeDay=" + predictMakeDay +
                ", businessUserId=" + businessUserId +
                ", modelName='" + modelName + '\'' +
                ", genAssetName='" + genAssetName + '\'' +
                ", styleId=" + styleId +
                ", styleName='" + styleName + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", modelImages='" + modelImages + '\'' +
                ", description='" + description + '\'' +
                ", vetoReason='" + vetoReason + '\'' +
                ", attachType=" + attachType +
                ", scaleType=" + scaleType +
                ", rotateType=" + rotateType +
                ", tagNames='" + tagNames + '\'' +
                ", tags=" + tags +
                ", scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", scaleZ=" + scaleZ +
                '}';
    }
}
