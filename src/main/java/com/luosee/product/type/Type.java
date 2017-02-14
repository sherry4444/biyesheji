package com.luosee.product.type;

import java.math.BigInteger;

/**
 * Created by server1 on 2016/11/24.
 */
public class Type {
    private BigInteger typeId;
    private String typeName;
    private Integer sort;
    private Boolean visible;

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setTypeId(BigInteger typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public BigInteger getTypeId() {
        return typeId;
    }

    public Boolean getVisible() {
        return visible;
    }

    public Integer getSort() {
        return sort;
    }

    public String getTypeName() {
        return typeName;
    }
}
