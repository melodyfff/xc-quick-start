package com.xinchen.project.core.hbase;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 10:28
 */
class HbaseClientTest {

    private static HbaseClient client;

    private static final String TABLE = "hello";
    private static final String ROWKEY = "row1";
    private static final String[] COLUMN_FAMILIES = new String[]{"hello1","hello2"};

    @BeforeAll
    static void setUp(){
        final ClassPathResource classPathResource = new ClassPathResource("application-hbase.yml");
        try {
            final List<PropertySource<?>> hbase = new YamlPropertySourceLoader().load("hbase", classPathResource);


            final HashMap<String,String> objectObjectHashMap = Maps.newHashMap();
            objectObjectHashMap.put("hbase.zookeeper.quorum", (String) hbase.get(0).getProperty("hbase.config.hbase.zookeeper.quorum"));


            final HbaseProperties hbaseProperties = new HbaseProperties();
            hbaseProperties.setEnable(true);
            hbaseProperties.setConfig(objectObjectHashMap);

            final HbaseConfig hbaseConfig = new HbaseConfig(hbaseProperties);

            client = new HbaseClient(hbaseConfig);
            client.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createTable() throws IOException {
        if (!client.tableExists(TABLE)){
            client.createTable(TABLE,COLUMN_FAMILIES);
        }
    }

    @Test
    void modifyTable() throws IOException {
        if (client.tableExists(TABLE)){
            client.modifyTable(TABLE,COLUMN_FAMILIES);
        }
    }

    @Test
    void insertOrUpdate() throws IOException {
        client.insertOrUpdate(TABLE,ROWKEY,COLUMN_FAMILIES[0],"test","Hello World!");
        client.insertOrUpdate(TABLE,ROWKEY,COLUMN_FAMILIES[0],"test1","Hello World!!");
        client.insertOrUpdate(TABLE,ROWKEY,COLUMN_FAMILIES[1],"test2","test222");
    }

    @Test
    void testInsertOrUpdate() {
    }

    @Test
    void deleteRow() throws IOException {
        client.deleteRow(TABLE,"1");
    }

    @Test
    void deleteColumnFamily() throws IOException {
        client.deleteColumnFamily(TABLE,ROWKEY,COLUMN_FAMILIES[1]);
    }

    @Test
    void deleteColumn() {
    }

    @Test
    void deleteTable() throws IOException {
        client.deleteTable(TABLE);
    }

    @Test
    void getValue() {
        System.out.println(client.getValue(TABLE, ROWKEY, COLUMN_FAMILIES[0], "test"));
        System.out.println(client.getValue(TABLE, ROWKEY, COLUMN_FAMILIES[0], "test1"));
    }

    @Test
    void selectOneRow() throws IOException {
        client.selectOneRow(TABLE, ROWKEY);
    }

    @Test
    void scanTable() throws IOException {
        // read each row from TABLE
        client.scanTable(TABLE, null);
        client.scanTable(TABLE, ROWKEY);
    }

    @Test
    void output() {
    }

    @Test
    void tableExists() throws IOException {
        if (client.tableExists(TABLE)){
            assertTrue(client.tableExists(TABLE));
        }
    }
}