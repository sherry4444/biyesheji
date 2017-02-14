package com.luosee.manager.audit.modelaudit;



import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/22.
 */
public interface ModelAuditDao {

    //待制作
    List<ModelAudit> waitToBeDone(Map<String, Object> parameter);

    //制作中
    List<ModelAudit> inMaking(Map<String, Object> parameter);

    //完成制作
    List<ModelAudit> toComplete(Map<String, Object> parameter);


    //统计已审核条数
    int count(ModelAudit modelAudit);

    //否决
    void veto(ModelAudit modelAudit);

    //通过审核
    void pass(ModelAudit modelAudit);

    //取消审核通过
    void cancel(ModelAudit modelAudit);
}
