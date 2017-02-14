package com.luosee.manager.audit.qualification;

import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/11/15.
 */
public interface QualificationDao {

    //未审核
    List<Qualification> selectAllunaudited(Map<String, Object> parameter);

    //已审核
    List<Qualification> selectAllaudited(Map<String, Object> parameter);

    //统计已审核条数
    int count(Map<String, Object> parameter);

    //统计未审核条数
    int countun(Map<String, Object> parameter);

    //否决
    void veto(Qualification parameter);

    //通过审核
    void pass(Qualification qualification);

    //取消审核通过
    void cancel(Qualification qualification);

    void change(Qualification qualification);
}
