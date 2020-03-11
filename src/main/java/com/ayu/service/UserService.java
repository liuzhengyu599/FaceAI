package com.ayu.service;

import com.ayu.dao.UserMapper;
import com.ayu.domain.User;
import com.ayu.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public static List<User> list;
    static{
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        list=mapper.getUserList();
        sqlSession.close();
    }
    /**
     * 获取用户数组
     * @return
     */
    public static List<User> getUserList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();
        sqlSession.close();
        return userList;
    }

    /**
     * 添加用户
     * @param user
     */
    public static void addUser(User user){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.addUser(user);
        sqlSession.commit();
        sqlSession.close();
    }
}
