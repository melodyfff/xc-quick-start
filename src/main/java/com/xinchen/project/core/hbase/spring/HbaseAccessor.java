package com.xinchen.project.core.hbase.spring;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 14:26
 */
public abstract class HbaseAccessor implements InitializingBean {
    private String encoding;
    private Charset charset = HbaseUtils.getCharset(encoding);

    private Connection connection;
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(configuration, " a valid configuration is required");
        // detect charset
        charset = HbaseUtils.getCharset(encoding);
        if (null==connection){
            try {
                connection = ConnectionFactory.createConnection(configuration);
            } catch (IOException e) {
                throw new RuntimeException("Hbase create connection failed ,",e);
            }
        }
    }

    /**
     * Sets the encoding.
     *
     * @param encoding The encoding to set.
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Sets the configuration.
     *
     * @param configuration The configuration to set.
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Charset getCharset() {
        return charset;
    }
    public Configuration getConfiguration() {
        return configuration;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
