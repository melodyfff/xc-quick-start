package com.xinchen.project.core.hbase.spring;

import org.springframework.dao.UncategorizedDataAccessException;

/**
 *
 * HBase Data Access exception.
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 14:06
 */
public class HbaseSystemException extends UncategorizedDataAccessException {

    public HbaseSystemException(Exception cause) {
        super(cause.getMessage(), cause);
    }
}