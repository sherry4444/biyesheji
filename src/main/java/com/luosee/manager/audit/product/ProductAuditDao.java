package com.luosee.manager.audit.product;



import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/22.
 */
public interface ProductAuditDao {
    //未审核
    List<com.luosee.manager.audit.product.ProductAudit> selectAllunaudited(Map<String, Object> parameter);

    //已审核
    List<com.luosee.manager.audit.product.ProductAudit> selectAllaudited(Map<String, Object> parameter);

    //统计已审核条数
    int count(Map<String,Object> parameter);

    //统计未审核条数
    int countun(Map<String,Object> parameter);

    //否决
    void veto(com.luosee.manager.audit.product.ProductAudit productAudit);

    //通过审核
    void pass(com.luosee.manager.audit.product.ProductAudit productAudit);

    //取消审核通过
    void cancel(com.luosee.manager.audit.product.ProductAudit productAudit);

    void change(ProductAudit productAudit);

    void changestatus(ProductAudit productAudit);

    void edit(ProductAudit productAudit);
}
