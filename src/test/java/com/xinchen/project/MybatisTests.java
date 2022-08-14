package com.xinchen.project;

import com.xinchen.project.core.orm.mybatis.entity.User;
import com.xinchen.project.core.orm.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 20:28
 */
@SpringBootTest
public class MybatisTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void test(){
        System.out.println(userMapper.getAll());
        System.out.println(userMapper.getUser2());
    }

    @Test
//    @Transactional
    void test2(){
        System.out.println(userMapper.selectByPrimaryKey(3L));
        final User user = new User();
        user.setUsername("ok");
        user.setPassword("ok");
        userMapper.insert(user);

    }
}
