package com.luosee.product;

import com.luosee.business.Seller;
import com.luosee.product.style.Style;
import com.luosee.product.type.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by server1 on 2016/11/26.
 */
public class Product {
    private BigInteger productId;

    @Length(min=1,max = 50,message = "商品名长度最少为一位,最长为50位")
    @NotBlank
    @NotNull
    private String productName;

    private String description;

    @NotNull
    private BigInteger typeId;

    @NotNull
    private BigInteger styleId;
    private Boolean isSelling;
    private Timestamp lastModified;
    private Integer attachType;
    private Integer scaleType;
    private Integer rotateType;

    @NotNull
    private BigInteger sellerId;

    @Length(max = 50,message = "标签名最长为50位")
    private String tagNames;
    private Boolean productType;
    private ProductStyle productStyle;
    private Seller seller;
    private Style style;
    private Type type;
    private String introduceImg;
    private ProductTagmap tagmap;

    public void setProductType(Boolean productType) {
        this.productType = productType;
    }

    public void setTagmap(ProductTagmap tagmap) {
        this.tagmap = tagmap;
    }

    public ProductTagmap getTagmap() {
        return tagmap;
    }

    public void setProductStyle(ProductStyle productStyle) {
        this.productStyle = productStyle;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setStyleId(BigInteger styleId) {
        this.styleId = styleId;
    }

    public void setTypeId(BigInteger typeId) {
        this.typeId = typeId;
    }

    public void setAttachType(Integer attachType) {
        this.attachType = attachType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setRotateType(Integer rotateType) {
        this.rotateType = rotateType;
    }

    public void setScaleType(Integer scaleType) {
        this.scaleType = scaleType;
    }

    public void setSellerId(BigInteger sellerId) {
        this.sellerId = sellerId;
    }

    public void setSelling(Boolean selling) {
        isSelling = selling;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public void setIntroduceImg(String introduceImg) {
        this.introduceImg = introduceImg;
    }

    public BigInteger getTypeId() {
        return typeId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public BigInteger getSellerId() {
        return sellerId;
    }

    public BigInteger getStyleId() {
        return styleId;
    }

    public Boolean getSelling() {
        return isSelling;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public Integer getAttachType() {
        return attachType;
    }

    public Integer getRotateType() {
        return rotateType;
    }

    public Boolean getProductType() {
        return productType;
    }

    public Integer getScaleType() {
        return scaleType;
    }

    public String getDescription() {
        return description;
    }

    public String getProductName() {
        return productName;
    }

    public String getTagNames() {
        return tagNames;
    }

    public ProductStyle getProductStyle() {
        return productStyle;
    }

    public Seller getSeller() {
        return seller;
    }

    public Style getStyle() {
        return style;
    }

    public Type getType() {
        return type;
    }

    public String getIntroduceImg() {
        return introduceImg;
    }

}
