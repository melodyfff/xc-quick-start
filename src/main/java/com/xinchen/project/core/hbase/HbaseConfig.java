package com.xinchen.project.core.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;
import java.util.Set;

/**
 *
 * 配置是否开启Hbase
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/3 21:03
 */
@ConditionalOnProperty(prefix = "hbase", name = "enable",matchIfMissing = false)
@Configuration
@EnableConfigurationProperties(HbaseProperties.class)
public class HbaseConfig {
    private final HbaseProperties properties;

    public HbaseConfig(HbaseProperties properties) {
        this.properties = properties;
    }

    public org.apache.hadoop.conf.Configuration configuration(){
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        Map<String, String> config = properties.getConfig();
        Set<String> keySet = config.keySet();
        for (String key : keySet) {
            configuration.set(key, config.get(key));
        }
        return configuration;
    }
}
