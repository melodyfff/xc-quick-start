package com.xinchen.project.core.orm.mybatis.mapper;

import com.xinchen.project.core.orm.mybatis.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.example.SelectByExampleMapper;

/**
 *
 * 批量插入实例
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @since 1.0 Created In 2022/10/18 22:45
 **/
@Mapper
public interface BatchMapper extends SelectByExampleMapper<Address>,InsertListMapper<Address> {

}
