package com.luosee.business;

import com.luosee.po.Page;

import java.util.List;

/**
 * Created by server1 on 2016/11/21.
 */
public interface SellerDao {
    List<Seller> findSellerByName(Page page);
    void addSeller(Seller seller);
    Integer sellerCount(Page page);
    void deleteSeller(int id);
    Seller queryBrand(int id);
    void updateBrand(Seller seller);
}
