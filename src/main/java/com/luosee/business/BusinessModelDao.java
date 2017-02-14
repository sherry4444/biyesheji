package com.luosee.business;

import com.luosee.po.Page;

import java.util.List;

/**
 * Created by server1 on 2016/11/21.
 */
public interface BusinessModelDao {
    void saveModel(BusinessModel businessModel);
    void deleteModel(List<String> modelIdList);
    List<BusinessModel> findModelByName(Page page);
    Integer queryModelCount(Page page);

    List<BusinessModel> findModelImg(List<String> modelIdList);

}
