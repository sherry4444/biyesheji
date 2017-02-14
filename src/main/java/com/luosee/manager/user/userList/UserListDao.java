package com.luosee.manager.user.userList;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/10.
 */
public interface UserListDao {

    int count(@Param("title") String title);

    List<UserList> selectAll( Map<String, Object> parameter);

    UserIncre countIncre();
}
