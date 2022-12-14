package com.xinchen.project.core.hbase.spring;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 15:01
 */
public class HbaseTemplate  extends HbaseAccessor implements HbaseOperations {
    public HbaseTemplate() {
    }

    public HbaseTemplate(Configuration configuration) {
        setConfiguration(configuration);
        afterPropertiesSet();
    }

    @Override
    public <T> T execute(String tableName, TableCallback<T> action) {
        Assert.notNull(action, "Callback object must not be null");
        Assert.notNull(tableName, "No table specified");

        Table table = getTable(tableName);

        try {
            return action.doInTable(table);
        } catch (Throwable th) {
            if (th instanceof Error) {
                throw ((Error) th);
            }
            if (th instanceof RuntimeException) {
                throw ((RuntimeException) th);
            }
            throw convertHbaseAccessException((Exception) th);
        } finally {
            releaseTable(tableName, table);
        }
    }

    private Table getTable(String tableName) {
        return HbaseUtils.getHTable(tableName, getConfiguration(), getConnection());
    }

    private void releaseTable(String tableName, Table table) {
        HbaseUtils.releaseTable(tableName, table);
    }

    public DataAccessException convertHbaseAccessException(Exception ex) {
        return HbaseUtils.convertHbaseException(ex);
    }

    @Override
    public <T> T find(String tableName, String family, final ResultsExtractor<T> action) {
        Scan scan = new Scan();
        scan.addFamily(family.getBytes(getCharset()));
        return find(tableName, scan, action);
    }

    @Override
    public <T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action) {
        Scan scan = new Scan();
        scan.addColumn(family.getBytes(getCharset()), qualifier.getBytes(getCharset()));
        return find(tableName, scan, action);
    }

    @Override
    public <T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action) {
        return execute(tableName, new TableCallback<T>() {
            @Override
            public T doInTable(Table htable) throws Throwable {
                ResultScanner scanner = htable.getScanner(scan);
                try {
                    return action.extractData(scanner);
                } finally {
                    scanner.close();
                }
            }
        });
    }

    @Override
    public <T> List<T> find(String tableName, String family, final RowMapper<T> action) {
        Scan scan = new Scan();
        scan.addFamily(family.getBytes(getCharset()));
        return find(tableName, scan, action);
    }

    @Override
    public <T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action) {
        Scan scan = new Scan();
        scan.addColumn(family.getBytes(getCharset()), qualifier.getBytes(getCharset()));
        return find(tableName, scan, action);
    }

    @Override
    public <T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action) {
        return find(tableName, scan, new RowMapperResultsExtractor<T>(action));
    }

    @Override
    public <T> T get(String tableName, String rowName, final RowMapper<T> mapper) {
        return get(tableName, rowName, null, null, mapper);
    }

    @Override
    public <T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper) {
        return get(tableName, rowName, familyName, null, mapper);
    }

    @Override
    public <T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper) {
        return execute(tableName, new TableCallback<T>() {
            @Override
            public T doInTable(Table htable) throws Throwable {
                Get get = new Get(rowName.getBytes(getCharset()));
                if (familyName != null) {
                    byte[] family = familyName.getBytes(getCharset());

                    if (qualifier != null) {
                        get.addColumn(family, qualifier.getBytes(getCharset()));
                    }
                    else {
                        get.addFamily(family);
                    }
                }
                Result result = htable.get(get);
                return mapper.mapRow(result, 0);
            }
        });
    }

    @Override
    public void put(String tableName, final String rowName, final String familyName, final String qualifier, final byte[] value) {
        Assert.hasLength(rowName,"rowName is empty");
        Assert.hasLength(familyName,"familyName is empty");
        Assert.hasLength(qualifier);
        Assert.notNull(value);
        execute(tableName, new TableCallback<Object>() {
            @Override
            public Object doInTable(Table htable) throws Throwable {
                Put put = new Put(rowName.getBytes(getCharset()));
                put.addColumn(familyName.getBytes(getCharset()), qualifier.getBytes(getCharset()), value);
                htable.put(put);
                return null;
            }
        });
    }

    @Override
    public void delete(String tableName, final String rowName, final String familyName) {
        delete(tableName, rowName, familyName, null);
    }

    @Override
    public void delete(String tableName, final String rowName, final String familyName, final String qualifier) {
        Assert.hasLength(rowName,"rowName is empty");
        Assert.hasLength(familyName,"familyName is empty");
        execute(tableName, new TableCallback<Object>() {
            @Override
            public Object doInTable(Table htable) throws Throwable {
                Delete delete = new Delete(rowName.getBytes(getCharset()));
                byte[] family = familyName.getBytes(getCharset());

                if (qualifier != null) {
                    delete.addColumn(family, qualifier.getBytes(getCharset()));
                }
                else {
                    delete.addFamily(family);
                }

                htable.delete(delete);
                return null;
            }
        });
    }
}
