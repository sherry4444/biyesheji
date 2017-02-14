package com.luosee.business;

import com.luosee.po.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by server1 on 2016/11/21.
 */
@Service
public class BusinessModelService {

    @Resource(name = "businessModelDao")
    private BusinessModelDao businessModelDao;

    public void saveModel(BusinessModel businessModel)
    {
        businessModelDao.saveModel(businessModel);
    }

    public void deleteModel(List<String> modelIdList)
    {
        businessModelDao.deleteModel(modelIdList);
    }

    public List<BusinessModel> findModelByName(Page page)
    {
        return businessModelDao.findModelByName(page);
    }

    public Integer queryModelCount(Page page)
    {
        return businessModelDao.queryModelCount(page);
    }

    public List<BusinessModel> findModelImg(List<String> modelIdList)
    {
        return businessModelDao.findModelImg(modelIdList);
    }
}
