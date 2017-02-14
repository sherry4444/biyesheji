package com.luosee.manager.audit.brand;

import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/14.
 */
public interface BrandDao {
    //未审核
    List<Brand> selectAllunaudited(Map<String, Object> parameter);

    //已审核
    List<Brand> selectAllaudited(Map<String, Object> parameter);

    //统计已审核条数
    int count(Map<String,Object> parameter);

    //统计未审核条数
    int countun(Map<String,Object> parameter);

    //否决
    void veto(Brand brand);

    //通过审核
    void pass(Brand brand);

    //取消审核通过
    void cancel(Brand brand);

    void change(Brand brand);
}
