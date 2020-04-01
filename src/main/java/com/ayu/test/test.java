package com.ayu.test;

import com.ayu.dao.RoomMapper;
import com.ayu.domain.Room;
import com.ayu.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


public class test {
    @Test
    public void test1(){
        SqlSession sqlSession= MybatisUtils.getSqlSession();
        RoomMapper mapper = sqlSession.getMapper(RoomMapper.class);
        Room room=new Room();
        room.setIdcard("111222200001010101");
        room.setStatus(0);
        mapper.changeStatus(room);
        sqlSession.commit();
    }
}
