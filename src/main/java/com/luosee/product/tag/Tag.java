package com.luosee.product.tag;

import java.math.BigInteger;

/**
 * Created by server1 on 2016/12/1.
 */
public class Tag {
    private BigInteger tagId;
    private String tagName;

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTagId(BigInteger tagId) {
        this.tagId = tagId;
    }

    public BigInteger getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }
}
