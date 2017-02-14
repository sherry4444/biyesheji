package com.luosee.manager.audit.product;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/22.
 */
@Service("/ProductAuditService")
public class ProductAuditService {

    @Resource(name = "productAuditDao")
    private com.luosee.manager.audit.product.ProductAuditDao productAuditDao;

    public List<com.luosee.manager.audit.product.ProductAudit> selectAllunaudited(Map<String,Object> parameter){return productAuditDao.selectAllunaudited(parameter);}


    public List<com.luosee.manager.audit.product.ProductAudit> selectAllaudited(Map<String,Object> parameter){return productAuditDao.selectAllaudited(parameter);}

    public void veto(com.luosee.manager.audit.product.ProductAudit productAudit){
        productAuditDao.veto(productAudit);
    }

    public void pass(com.luosee.manager.audit.product.ProductAudit productAudit){
        productAuditDao.pass(productAudit);
    }

    public int countun(Map<String,Object> parameter){
        return productAuditDao.countun(parameter);
    }

    public int count(Map<String,Object> parameter){
        return productAuditDao.count(parameter);
    }

    public void cancel(com.luosee.manager.audit.product.ProductAudit productAudit) {
        productAuditDao.cancel(productAudit);
    }

    public void change(ProductAudit productAudit) {
        productAuditDao.change(productAudit);
    }

    public void changestatus(ProductAudit productAudit) {
        productAuditDao.changestatus(productAudit);
    }

    public void edit(ProductAudit productAudit) {
        productAuditDao.edit(productAudit);
    }
}
