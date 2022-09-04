package com.xinchen.project.core.hbase.spring;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

/**
 *
 * Callback for mapping rows of a {@link ResultScanner} on a per-row basis.
 * Implementations of this interface perform the actual work of mapping each row to a result object, but don't need to worry about exception handling.
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 14:01
 */
public interface RowMapper<T> {
    T mapRow(Result result, int rowNum) throws Exception;
}
