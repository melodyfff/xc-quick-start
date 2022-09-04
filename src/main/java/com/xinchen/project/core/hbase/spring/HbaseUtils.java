package com.xinchen.project.core.hbase.spring;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 14:05
 */
public class HbaseUtils {

    /**
     * Converts the given (Hbase) exception to an appropriate exception from <tt>org.springframework.dao</tt> hierarchy.
     *
     * @param ex Hbase exception that occurred
     * @return the corresponding DataAccessException instance
     */
    public static DataAccessException convertHbaseException(Exception ex) {
        return new HbaseSystemException(ex);
    }

    /**
     * Retrieves an Hbase table instance identified by its name.
     *
     * @param configuration Hbase configuration object
     * @param tableName     table name
     * @return table instance
     */
    public static Table getHTable(String tableName, Configuration configuration) {
        return getHTable(tableName, configuration, null);
    }

    /**
     * Retrieves an Hbase table instance identified by its name and charset using the given table factory.
     *
     * @param tableName     table name
     * @param configuration Hbase configuration object
     * @param connection    connection (may be null)
     * @return table instance
     */
    public static Table getHTable(String tableName, Configuration configuration,  Connection connection) {
        if (HbaseSynchronizationManager.hasResource(tableName)) {
            return (HTable) HbaseSynchronizationManager.getResource(tableName);
        }
        Table t;
        try {

            if (connection != null) {
                t = connection.getTable(TableName.valueOf(tableName));
            } else {
                // 这里的connection其实不应该新建，得全局使用一个单例
                connection = ConnectionFactory.createConnection(configuration);
                t = connection.getTable(TableName.valueOf(tableName));
            }
            return t;
        } catch (Exception ex) {
            throw convertHbaseException(ex);
        }
    }

    static Charset getCharset(String encoding) {
        return (StringUtils.hasText(encoding) ? Charset.forName(encoding) : StandardCharsets.UTF_8);
    }

    /**
     * Releases (or closes) the given table, created via the given configuration if it is not managed externally (or bound to the thread).
     *
     * @param tableName table name
     * @param table     table
     */
    public static void releaseTable(String tableName, Table table) {
        try {
            doReleaseTable(tableName, table);
        } catch (IOException ex) {
            throw HbaseUtils.convertHbaseException(ex);
        }
    }

    private static void doReleaseTable(String tableName, Table table)
            throws IOException {
        if (table == null) {
            return;
        }
        // close only if its unbound
        if (!isBoundToThread(tableName)) {
            table.close();
        }
    }

    private static boolean isBoundToThread(String tableName) {
        return HbaseSynchronizationManager.hasResource(tableName);
    }
}
