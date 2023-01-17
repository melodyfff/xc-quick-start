package com.xinchen.project.core.orm.jpa.dto;

import com.xinchen.project.core.orm.jpa.repositories.HelloRepository;

/**
 *
 *  {@link HelloRepository#searchDto()} 组装原始SQL查询返回数据
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 22:19
 */
public interface DataDto {
  String getName();
  Integer getAge();
}
