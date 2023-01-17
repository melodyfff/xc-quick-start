package com.xinchen.project.core.orm.jpa.repositories;

import com.xinchen.project.core.orm.jpa.dto.DataDto;
import com.xinchen.project.core.orm.jpa.entity.HelloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 21:37
 */
public interface HelloRepository extends JpaRepository<HelloEntity,Long> {

  @Query(value = "select t.name as name,t.age as age from xc_hello t", nativeQuery = true)
  DataDto searchDto();
}
