package com.luosee.product;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by server1 on 2016/12/1.
 */
public class ProductItem {
    private List<ProductStyle> productStyleList;

    private List<Long>  productIds;

    private List<MultipartFile> productStyleImg;

    private List<MultipartFile> productIntroduceImg;

    public void setProductIntroduceImg(List<MultipartFile> productIntroduceImg) {
        this.productIntroduceImg = productIntroduceImg;
    }

    public List<MultipartFile> getProductIntroduceImg() {
        return productIntroduceImg;
    }

    public void setProductStyleImg(List<MultipartFile> productStyleImg) {
        this.productStyleImg = productStyleImg;
    }

    public List<MultipartFile> getProductStyleImg() {
        return productStyleImg;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductStyleList(List<ProductStyle> productStyleList) {
        this.productStyleList = productStyleList;
    }

    public List<ProductStyle> getProductStyleList() {
        return productStyleList;
    }
}
