package com.luosee.product;

import com.luosee.po.Page;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/26.
 */
public interface ProductDao {
    List<Product> queryProduct(Page page);
    void addProduct(Product product);
    void addProductStyle(List<ProductStyle> productStyleList);
    void addProductTag(ProductTagmap productTagmap);
    void updateProductInfo(Map<String,Object> infoMap);
    void deleteProductStyle(List<BigInteger> styleIds);
    void updateProductStyle(List<ProductStyle> productStyleList);
    BigInteger queryLastProductId();
    Integer queryProductCount(Page page);
    void deleteProduct(List<Long> productIds);
    Product queryProductInfoByName(Map<String,Object> infoMap);
    List<ProductStyle> queryProductStyleByName(Map<String,Object> infoMap);
    List<Product> queryIntroduceImg(List<Long> productIds);
    List<ProductStyle> queryNormalPic(List<Long> productIds);
    List<Long> query_seller_product(int sellerId);

}
