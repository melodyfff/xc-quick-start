package com.xinchen.project.core.orm.jpa.entity;

import com.xinchen.project.core.orm.jpa.entity.constant.EnumSex;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 21:33
 */

@Entity
@Table(name = "xc_hello")
@Data
public class HelloEntity extends BaseEntity{
  private String name;
  private Integer age;
  /**
   * EnumType:  ORDINAL 枚举序数  默认选项（int）。eg:TEACHER 数据库存储的是 0
   *            STRING：枚举名称       (String)。eg:TEACHER 数据库存储的是 "TEACHER"
   */
  @Enumerated(EnumType.STRING)
  private EnumSex sex;
}
