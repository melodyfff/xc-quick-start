package com.xinchen.project.core.orm.mybatis.entity;


import java.io.Serializable;

/**
 * 联系方式
 */
public class Address implements Serializable {
  private Long userId;
  private String address;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "Address{" +
            "userId=" + userId +
            ", address='" + address + '\'' +
            '}';
  }
}
