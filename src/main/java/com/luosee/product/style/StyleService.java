package com.luosee.product.style;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by server1 on 2016/11/24.
 */
@Service
public class StyleService {

    @Resource(name = "styleDao")
    private StyleDao styleDao;

    public List<Style> findStyle()
    {
        return styleDao.findStyle();
    }
}
