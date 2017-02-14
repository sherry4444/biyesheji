package com.luosee.pay;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by server1 on 2017/1/10.
 */
@Service
public class payService {

    @Resource(name = "payDao")
    private PayDao payDao;


}
