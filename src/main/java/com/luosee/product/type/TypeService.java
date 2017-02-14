package com.luosee.product.type;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by server1 on 2016/11/24.
 */
@Service
public class TypeService {
    @Resource(name = "typeDao")
    private TypeDao typeDao;

    public List<Type> findType()
    {
        return typeDao.findType();
    }
}
