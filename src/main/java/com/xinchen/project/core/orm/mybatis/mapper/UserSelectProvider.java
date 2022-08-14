package com.xinchen.project.core.orm.mybatis.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/7/17 15:56
 */
public class UserSelectProvider {
  public String selectAll(){
    return new SQL() {{
      SELECT("*");
      FROM("app_user");
      WHERE("1=1");
    }}.toString();
  }
}
