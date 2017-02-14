package com.luosee.product.style;

import java.math.BigInteger;

/**
 * Created by server1 on 2016/11/24.
 */
public class Style {
    private BigInteger styleId;
    private String styleName;

    public void setStyleId(BigInteger styleId) {
        this.styleId = styleId;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public BigInteger getStyleId() {
        return styleId;
    }

    public String getStyleName() {
        return styleName;
    }
}
