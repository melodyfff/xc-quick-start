package com.xinchen.project.core.orm.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.xinchen.project.core.orm.jpa.dto.DataDto;
import com.xinchen.project.core.orm.jpa.entity.HelloEntity;
import com.xinchen.project.core.orm.jpa.entity.constant.EnumSex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 21:38
 */
@Profile("jpa")
@SpringBootTest
class HelloRepositoryTest {

  @Autowired
  HelloRepository repository;

  @BeforeEach
  void init(){
    repository.deleteAll();
    final HelloEntity helloEntity = new HelloEntity();
    helloEntity.setName("hello");
    helloEntity.setAge(21);
    helloEntity.setSex(EnumSex.MAN);
    repository.save(helloEntity);
  }

  @Test
  void ok(){
    final DataDto dataDto = repository.searchDto();
    Assertions.assertEquals(21,dataDto.getAge());
    Assertions.assertEquals("hello",dataDto.getName());
  }
}