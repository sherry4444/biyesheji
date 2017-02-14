package com.luosee.manager.audit.product;

import com.luosee.product.tag.Tag;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by server2 on 2016/12/22.
 */
public class ProductAudit {

    private Integer productId;//商品ID
    private Integer styleId;//商品种类ID
    private String styleName;//商品种类名字
    private String productName;//商品名称
    private String description;//商品描述
    private String isSelling;//是否在售
    private String introduceImg;//商品图片
    private Timestamp lastModified;//最后修改时间
    private String certificationStatus;//认证状态
    private String certificationCause;//认证原因
    private Timestamp certificationTime;//认证时间
    private String productstatus;//是否上架
    private Integer typeId;//分类ID
    private Integer attachType;
    private Integer scaleType;
    private Integer rotateType;
    private String tagNames;
    private List<Tag> tags;
    private String filepath;


    @Transient
    private int scaleX;

    @Transient
    private int scaleY;

    @Transient
    private int scaleZ;


    public ProductAudit() {
    }

    public ProductAudit(Integer productId, Integer styleId, String styleName, String productName, String description, String isSelling, String introduceImg, Timestamp lastModified, String certificationStatus, String certificationCause, Timestamp certificationTime, String productstatus, Integer typeId, Integer attachType, Integer scaleType, Integer rotateType, String tagNames, List<Tag> tags, String filepath, int scaleX, int scaleY, int scaleZ) {
        this.productId = productId;
        this.styleId = styleId;
        this.styleName = styleName;
        this.productName = productName;
        this.description = description;
        this.isSelling = isSelling;
        this.introduceImg = introduceImg;
        this.lastModified = lastModified;
        this.certificationStatus = certificationStatus;
        this.certificationCause = certificationCause;
        this.certificationTime = certificationTime;
        this.productstatus = productstatus;
        this.typeId = typeId;
        this.attachType = attachType;
        this.scaleType = scaleType;
        this.rotateType = rotateType;
        this.tagNames = tagNames;
        this.tags = tags;
        this.filepath = filepath;
        updateScaleValues();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsSelling() {
        return isSelling;
    }

    public void setIsSelling(String isSelling) {
        this.isSelling = isSelling;
    }

    public String getIntroduceImg() {
        return introduceImg;
    }

    public void setIntroduceImg(String introduceImg) {
        this.introduceImg = introduceImg;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public String getCertificationStatus() {
        return certificationStatus;
    }

    public void setCertificationStatus(String certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public String getCertificationCause() {
        return certificationCause;
    }

    public void setCertificationCause(String certificationCause) {
        this.certificationCause = certificationCause;
    }

    public Timestamp getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(Timestamp certificationTime) {
        this.certificationTime = certificationTime;
    }

    public String getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(String productstatus) {
        this.productstatus = productstatus;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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

}