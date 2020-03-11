package com.ayu.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

//sqlSessionFactory --> sqlSession
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try {
            //使用Mybatis第一步：获取sqlSessionFactory对象
            String resource="mybatis-config.xml";
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            resourceAsStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //有了SQLSessionFactory我们就可以获取SqlSession实例了
    //SqlSession完全包含了面向数据库执行sql命令所需的方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
