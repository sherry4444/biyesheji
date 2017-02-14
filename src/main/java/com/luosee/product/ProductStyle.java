package com.luosee.product;

import com.luosee.business.BusinessModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by server1 on 2016/11/26.
 */
public class ProductStyle {
    private BigInteger styleId;
    private BigInteger productId;


    @Max(value = 50,message = "款式名最长为50位")
    @NotNull
    private String styleName;
    private String normalPic;
    private Boolean isDefault;
    private String assetName;
    private String assetURL;

    @Max(value = 10,message = "价格最长为10位")
    private BigDecimal price;
    private Float width;
    private Float length;
    private Float height;

    @Max(value = 255,message = "商品名最长为255位")
    private String shoppingUrl;
    private BigInteger modelId;
    private BusinessModel model;
    private Product product;

    public void setModel(BusinessModel model) {
        this.model = model;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setAssetURL(String assetURL) {
        this.assetURL = assetURL;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public void setNormalPic(String normalPic) {
        this.normalPic = normalPic;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setShoppingUrl(String shoppingUrl) {
        this.shoppingUrl = shoppingUrl;
    }

    public void setStyleId(BigInteger styleId) {
        this.styleId = styleId;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public void setModelId(BigInteger modelId) {
        this.modelId = modelId;
    }

    public String getAssetURL() {
        return assetURL;
    }

    public String getAssetName() {
        return assetName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public BigInteger getStyleId() {
        return styleId;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public Float getHeight() {
        return height;
    }

    public Float getLength() {
        return length;
    }

    public Float getWidth() {
        return width;
    }

    public String getNormalPic() {
        return normalPic;
    }

    public Product getProduct() {
        return product;
    }

    public String getShoppingUrl() {
        return shoppingUrl;
    }

    public String getStyleName() {
        return styleName;
    }

    public BusinessModel getModel() {
        return model;
    }

    public BigInteger getModelId() {
        return modelId;
    }
}
