package com.luosee.product.tag;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by server1 on 2016/12/1.
 */
@Service
public class TagsService {
    @Resource(name = "tagDao")
    private TagDao tagDao;

    public List<Tag> findTag() {
        return tagDao.finTag();
    }
}
