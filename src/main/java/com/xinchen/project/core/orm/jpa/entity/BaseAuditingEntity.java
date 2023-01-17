package com.xinchen.project.core.orm.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 22:07
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
class BaseAuditingEntity implements Serializable {

  @Id
  @GenericGenerator(name = "IDProvider", strategy = "com.xinchen.project.core.orm.jpa.IDProvider" )
  @GeneratedValue(generator = "IDProvider")
  protected Long id;
  @CreatedDate
  protected Date createDate;
  @CreatedBy
  protected String createBy;
  @LastModifiedDate
  protected Date modifiedDate;
  @LastModifiedBy
  protected String modifieBy;
}
