package com.xinchen.project.core.orm.mongo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import java.util.Optional;

/**
 *
 * EnableMongoAuditing 是为了使用 CreatedDate LastModifiedDate CreatedBy LastModifiedBy
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/7 20:58
 */
@Profile("mongo")
@Configuration
@Import(value = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,})
@EnableMongoAuditing
@ComponentScan(basePackageClasses = ConfigMongoDB.class)
class ConfigMongoDB implements InitializingBean {

    private final MappingMongoConverter mappingMongoConverter;

    ConfigMongoDB(MappingMongoConverter mappingMongoConverter) {
        this.mappingMongoConverter = mappingMongoConverter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 去除spring data在mongodb中自动生成的_class
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
    @Profile("mongo")
    @Configuration
    static class AuditorConfig implements AuditorAware<String>{
        @Override
        public Optional<String> getCurrentAuditor() {
            // 审计时使用 CreatedBy LastModifiedBy
            return Optional.of("ADMIN");
        }
    }
}
