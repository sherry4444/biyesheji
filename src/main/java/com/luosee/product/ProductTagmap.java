package com.luosee.product;

import com.luosee.product.tag.Tag;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * Created by server1 on 2016/11/26.
 */
public class ProductTagmap {
    private BigInteger tagId;
    @NotNull
    private BigInteger productId;
    @NotNull
    private  BigInteger productTagMapId;
    private Tag tag;

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public void setProductTagMapId(BigInteger productTagMapId) {
        this.productTagMapId = productTagMapId;
    }

    public void setTagId(BigInteger tagId) {
        this.tagId = tagId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public BigInteger getProductTagMapId() {
        return productTagMapId;
    }

    public BigInteger getTagId() {
        return tagId;
    }
}
