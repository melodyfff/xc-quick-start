package com.xinchen.project.core.orm.jpa;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 21:50
 */
@Component("IDProvider")
class IDProvider implements IdentifierGenerator {

  private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSS");
  @Override
  public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor,
      Object o) throws HibernateException {
    return Long.parseLong(FORMAT.format(new Date()));
  }
}
