package com.luosee.manager.user.userList;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/10.
 */
@Service
public class UserListService {

    @Resource(name = "userListDao")
    private UserListDao userListDao;

    public List<UserList> selectAll(Map<String,Object> parameter){return userListDao.selectAll(parameter);}

    public int count(String title){ return  userListDao.count(title);}

    public UserIncre countIncre(){ return userListDao.countIncre();}

}
