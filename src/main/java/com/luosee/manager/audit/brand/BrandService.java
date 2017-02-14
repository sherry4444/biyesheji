package com.luosee.manager.audit.brand;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/15.
 */
@Service
public class BrandService {

    @Resource(name = "brandDao")
    private BrandDao brandDao;

    public List<Brand> selectAllunaudited(Map<String,Object> parameter){return brandDao.selectAllunaudited(parameter);}


    public List<Brand> selectAllaudited(Map<String,Object> parameter){return brandDao.selectAllaudited(parameter);}

    public void veto(Brand brand){
        brandDao.veto(brand);
    }

    public void pass(Brand brand){
        brandDao.pass(brand);
    }

    public int countun(Map<String,Object> parameter){
        return brandDao.countun(parameter);
    }

    public int count(Map<String,Object> parameter){
        return brandDao.count(parameter);
    }

    public void cancel(Brand brand) {
        brandDao.cancel(brand);
    }

    public void change(Brand brand) {
        brandDao.change(brand);
    }
}
