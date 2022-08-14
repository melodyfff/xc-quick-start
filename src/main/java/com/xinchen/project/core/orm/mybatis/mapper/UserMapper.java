package com.xinchen.project.core.orm.mybatis.mapper;

import com.xinchen.project.core.orm.mybatis.entity.Address;
import com.xinchen.project.core.orm.mybatis.entity.SexEnum;
import com.xinchen.project.core.orm.mybatis.entity.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;

import java.util.List;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/7/17 15:18
 */
@Mapper
public interface UserMapper extends BaseMapper<User>, ExampleMapper<User> {

  @Results(id = "user",value = {
      @Result(id = true,column = "id",property = "id"),
      @Result(column = "user_name",property = "username"),
      @Result(column = "password",property = "password")
  })
  @Select("select * from app_user")
  List<User> getUsers();

  @SelectProvider(type = UserSelectProvider.class,method = "selectAll")
  @ResultMap("user")
  List<User> getUser2();

  /**
   * 注解关系映射查询
   * @return List<User>
   */
  @Results({
      @Result(id = true,column = "id",property = "id"),
      @Result(column = "user_name",property = "username"),
      @Result(column = "password",property = "password"),
      @Result(column = "sex",property = "sex",javaType = SexEnum.class),
      @Result(column = "user_id",property = "addresses",javaType = Address.class,one = @One(select = "com.xinchen.project.core.orm.mybatis.mapper.AddressMapper.getAddressByUserId")),
      @Result(column = "user_id",property = "phones",javaType = List.class,many = @Many(select = "com.xinchen.project.core.orm.mybatis.mapper.PhoneMapper.getByUserId"))
  })
  @Select("select * from app_user t left join app_address as t2 on(t.id = t2.user_id) left join app_phone t3 on (t.id = t3.user_id)")
  List<User> getAll();
}
