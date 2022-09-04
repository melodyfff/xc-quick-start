package com.xinchen.project.core.hbase.spring;

import org.apache.hadoop.hbase.client.ResultScanner;

/**
 *
 * Callback handling scanner results.
 * Implementations of this interface perform the actula work of extracting results from the
 * {@link ResultScanner} but  without having to worry about exception handling or resource management.
 *
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 14:02
 */
public interface ResultsExtractor<T> {

    /**
     * Implementations must implement this method to process the entire {@link ResultScanner}.
     *
     * @param results {@link ResultScanner} to extract data from. Implementations should not close this; it will be closed
     * automatically by the calling {@link HbaseTemplate}
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws Exception if an Hbase exception is encountered
     */
    T extractData(ResultScanner results) throws Exception;
}
