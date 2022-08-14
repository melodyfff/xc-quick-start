package com.xinchen.project.core.orm.mybatis.entity;


import java.io.Serializable;

/**
 */
public class Phone implements Serializable {
  private Long userId;
  private String phone;


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Phone{" +
            "userId=" + userId +
            ", phone='" + phone + '\'' +
            '}';
  }
}
