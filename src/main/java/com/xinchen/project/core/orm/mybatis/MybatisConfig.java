package com.xinchen.project.core.orm.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * 启用mybatis
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 18:53
 */
@Profile("mybatis")
@MapperScan("com.xinchen.project.core.orm.mybatis.mapper")
@Configuration
@Import({
        DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class
})
public class MybatisConfig {
}
