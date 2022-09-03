package com.xinchen.project.core.hbase;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

/**
 *
 * HbaseProperties
 *
 * see {@link EnableConfigurationProperties}
 *
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/3 20:46
 */
@ConfigurationProperties(prefix = "hbase")
public class HbaseProperties {
    private boolean enable;
    private Map<String, String> config;

    public Map<String, String> getConfig() {
        return config;
    }
    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
