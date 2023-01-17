package com.xinchen.project.core.orm.jpa.repositories;

import com.xinchen.project.core.orm.jpa.entity.HelloAuditingEntity;
import com.xinchen.project.core.orm.jpa.entity.constant.EnumSex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 22:11
 */
@Profile("jpa")
@SpringBootTest
class HelloAuditingRepositoryTest {
  @Autowired
  HelloAuditingRepository repository;

  @BeforeEach
  void init(){
    repository.deleteAll();
    final HelloAuditingEntity helloEntity = new HelloAuditingEntity();
    helloEntity.setName("hello");
    helloEntity.setAge(21);
    helloEntity.setSex(EnumSex.MAN);
    repository.save(helloEntity);
  }

  @Test
  void ok(){
  }
}