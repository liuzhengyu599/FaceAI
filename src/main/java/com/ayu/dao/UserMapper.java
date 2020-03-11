package com.ayu.dao;


import com.ayu.domain.User;

import java.util.List;

public interface UserMapper {
    List<User> getUserList();

    int addUser(User user);

    User getUserByIdcard(Integer idcard);
}
