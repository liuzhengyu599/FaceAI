package com.ayu.service;

import com.ayu.dao.RoomMapper;
import com.ayu.domain.Room;
import com.ayu.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RoomService {
    public static List<Room> list;
    public static List<Room> myList;
    static{
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RoomMapper mapper = sqlSession.getMapper(RoomMapper.class);
        list=mapper.getRoomList();
        sqlSession.close();
    }
    public static void bookRoom(Room room){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RoomMapper mapper = sqlSession.getMapper(RoomMapper.class);
        mapper.changeStatus(room);
        sqlSession.commit();
        sqlSession.close();
    }
    public static void freshList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RoomMapper mapper = sqlSession.getMapper(RoomMapper.class);
        list=mapper.getRoomList();
        sqlSession.close();
    }
    public static void getRoomListByIdcard(String idcard){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RoomMapper mapper = sqlSession.getMapper(RoomMapper.class);
        myList=mapper.getRoomListByIdcard(idcard);
        sqlSession.close();
    }

}
