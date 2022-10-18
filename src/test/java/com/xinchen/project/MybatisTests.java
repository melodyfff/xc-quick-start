package com.xinchen.project;

import com.xinchen.project.core.orm.mybatis.entity.Address;
import com.xinchen.project.core.orm.mybatis.entity.User;
import com.xinchen.project.core.orm.mybatis.mapper.BatchMapper;
import com.xinchen.project.core.orm.mybatis.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;


/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 20:28
 */
@SpringBootTest
@ActiveProfiles("dev")
public class MybatisTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    BatchMapper batchMapper;

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

    @Test
    void testBatch(){
        List<Address> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            Address address = new Address();
            address.setUserId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
            address.setAddress("tests-"+i);
            list.add(address);
        }

        StopWatch watch = new StopWatch();
        watch.start();
        batchMapper.insertList(list);
        watch.stop();

        System.out.println("Cost:"+watch.getTotalTimeSeconds());
    }

    @Test
    void uuid2long(){
        System.out.println(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    }
}
