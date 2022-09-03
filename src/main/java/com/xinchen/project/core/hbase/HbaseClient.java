package com.xinchen.project.core.hbase;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

/**
 * HbaseClient 封装
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/3 21:05
 */
@ConditionalOnBean(HbaseConfig.class)
@Component
public class HbaseClient {
    private final HbaseConfig hbaseConfig;
    private static final Logger logger = LoggerFactory.getLogger(HbaseClient.class);

    private static Connection connection = null;

    public HbaseClient(HbaseConfig hbaseConfig) {
        this.hbaseConfig = hbaseConfig;
    }

    @PostConstruct
    private void init() {
        if (null != connection) {
            return;
        }
        try {
            connection = ConnectionFactory.createConnection(hbaseConfig.configuration());
        } catch (IOException e) {
            logger.error("Hbase create connection failed ,", e);
        }
    }

    @PreDestroy
    private void destroy() {
        logger.info("HbaseClient destroying ...");
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (IOException e) {
            logger.error("HbaseClient connection close error: ", e);
        }
    }

    public void createTable(String tableName, String[] columnFamilies) throws IOException {
        TableName name = TableName.valueOf(tableName);

        boolean isExists = this.tableExists(tableName);
        if (isExists) {
            throw new TableExistsException(tableName + "is exists!");
        }

        TableDescriptorBuilder descriptorBuilder = TableDescriptorBuilder.newBuilder(name);
        List<ColumnFamilyDescriptor> columnFamilyList = new ArrayList<>();
        for (String columnFamily : columnFamilies) {
            ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(columnFamily.getBytes()).build();
            columnFamilyList.add(columnFamilyDescriptor);
        }
        descriptorBuilder.setColumnFamilies(columnFamilyList);
        TableDescriptor tableDescriptor = descriptorBuilder.build();
        try (Admin admin = connection.getAdmin()){
            admin.createTable(tableDescriptor);
        }
    }

    public void insertOrUpdate(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        this.insertOrUpdate(tableName, rowKey, columnFamily, new String[]{column}, new String[]{value});
    }

    public void insertOrUpdate(String tableName, String rowKey, String columnFamily, String[] columns, String[] values) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        for (int i = 0; i < columns.length; i++) {
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
            table.put(put);
        }
    }

    public void deleteRow(String tableName, String rowKey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        table.delete(delete);
    }

    public void deleteColumnFamily(String tableName, String rowKey, String columnFamily) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        delete.addFamily(Bytes.toBytes(columnFamily));
        table.delete(delete);
    }

    public void deleteColumn(String tableName, String rowKey, String columnFamily, String column) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        table.delete(delete);
    }

    public void deleteTable(String tableName) throws IOException {
        boolean isExists = this.tableExists(tableName);
        if (!isExists) {
            return;
        }

        TableName name = TableName.valueOf(tableName);
        try (Admin admin = connection.getAdmin()){
            admin.disableTable(name);
            admin.deleteTable(name);
        }
    }

    public String getValue(String tableName, String rowkey, String family, String column) {
        Table table = null;

        String value = "";
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(family)
                || StringUtils.isBlank(rowkey) || StringUtils.isBlank(column)) {
            return null;
        }

        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Get g = new Get(rowkey.getBytes());
            g.addColumn(family.getBytes(), column.getBytes());
            Result result = table.get(g);
            List<Cell> ceList = result.listCells();
            if (ceList != null && ceList.size() > 0) {
                for (Cell cell : ceList) {
                    value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public String selectOneRow(String tableName, String rowKey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        Result result = table.get(get);
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = result.getMap();
        for (Cell cell : result.rawCells()) {
            String row = Bytes.toString(cell.getRowArray());
            String columnFamily = Bytes.toString(cell.getFamilyArray());
            String column = Bytes.toString(cell.getQualifierArray());
            String value = Bytes.toString(cell.getValueArray());

            // 可以通过反射封装成对象(列名和Java属性保持一致)
            System.out.println(row);
            System.out.println(columnFamily);
            System.out.println(column);
            System.out.println(value);
        }

        return null;
    }

    public String scanTable(String tableName, String rowKeyFilter) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        if (!StringUtils.isEmpty(rowKeyFilter)) {
            RowFilter rowFilter = new RowFilter(CompareOperator.EQUAL, new SubstringComparator(rowKeyFilter));
            scan.setFilter(rowFilter);
        }
        ResultScanner scanner = table.getScanner(scan);

        try {
            for (Result result : scanner) {
                System.out.println(Bytes.toString(result.getRow()));
                for (Cell cell : result.rawCells()) {
                    System.out.println(cell);
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return null;
    }

    public void output(Result result) throws IOException {
        CellScanner cellScanner = result.cellScanner();
        while (cellScanner.advance()) {
            Cell cell = cellScanner.current();
            //本kv所属的行键的字节数组
            byte[] rowArray = cell.getRowArray();
            //列族名的字节数组
            byte[] familyArray = cell.getFamilyArray();
            //列名的字节数据
            byte[] qualifierArray = cell.getQualifierArray();
            // value的字节数组
            byte[] valueArray = cell.getValueArray();

            System.out.printf("|%10s|%10s|%10s|%10s|\n",
                    new String(rowArray, cell.getRowOffset(), cell.getRowLength()),
                    new String(familyArray, cell.getFamilyOffset(), cell.getFamilyLength()),
                    new String(qualifierArray, cell.getQualifierOffset(), cell.getQualifierLength()),
                    new String(valueArray, cell.getValueOffset(), cell.getValueLength()));
        }
    }

    /**
     * 判断表是否已经存在，这里使用间接的方式来实现
     * <p>
     * admin.tableExists() 会报NoSuchColumnFamilyException， 有人说是hbase-client版本问题
     *
     * @param tableName tableName
     * @return b
     * @throws IOException IOException
     */
    public boolean tableExists(String tableName) throws IOException {
       try (Admin admin = connection.getAdmin()){
           TableName[] tableNames = admin.listTableNames();
           if (tableNames != null && tableNames.length > 0) {
               for (TableName name : tableNames) {
                   if (tableName.equals(name.getNameAsString())) {
                       return true;
                   }
               }
           }
           return false;
       }
    }
}
