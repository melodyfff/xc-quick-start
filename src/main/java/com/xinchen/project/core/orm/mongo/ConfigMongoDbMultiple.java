package com.xinchen.project.core.orm.mongo;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;


/**
 * 多数据源mongodb配置
 *
 *
 * 可观察{@link AbstractMongoClientConfiguration} 中是如何配置
 *
 * 关于事务，第二数据源使用需要指定  ：   @Transactional(rollbackFor = Exception.class,transactionManager = "secondaryTransactionManager")
 *
 * EnableMongoAuditing 是为了使用 CreatedDate LastModifiedDate CreatedBy LastModifiedBy 参考{@link ConfigMongoDB.AuditorConfig}
 *
 */
@Profile("mongo-multiple")
@Configuration
@EnableMongoAuditing
public class ConfigMongoDbMultiple  {
    @Profile("mongo-multiple")
    @Configuration
    static class AuditorConfig implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            // 审计时使用 CreatedBy LastModifiedBy
            return Optional.of("ADMIN");
        }
    }

    @Profile("mongo-multiple")
    @Configuration
    @EnableMongoRepositories(basePackages = "com.xinchen.project.core.orm.mongo.multiple.primary",
    mongoTemplateRef = "primaryMongoTemplate")
    static class PrimaryMongoConfig {

        @Bean
        @ConfigurationProperties("spring.data.mongodb.primary")
        public MongoProperties primaryMongoProperties(){
            return new MongoProperties();
        }
        @Bean
        @Primary
        public MongoMappingContext primaryMongoMappingContext(){
            return new MongoMappingContext();
        }
        @Bean
        @Primary
        public MongoDatabaseFactory primaryMongoDatabaseFactory(MongoProperties primaryMongoProperties){
            return new SimpleMongoClientDatabaseFactory(primaryMongoProperties.getUri());
        }

        @Bean
        @Primary
        public MappingMongoConverter primaryMappingMongoConverter(MongoDatabaseFactory primaryMongoDatabaseFactory,
                                                                  MongoMappingContext primaryMongoMappingContext

        ){
            DefaultDbRefResolver defaultDbRefResolver = new DefaultDbRefResolver(primaryMongoDatabaseFactory);
            MappingMongoConverter converter = new MappingMongoConverter(defaultDbRefResolver, primaryMongoMappingContext);
            // 去除spring data在mongodb中自动生成的_class
            converter.setTypeMapper(new DefaultMongoTypeMapper(null));
            return converter;
        }

        @Primary
        @Bean
        public MongoTemplate primaryMongoTemplate(MongoProperties primaryMongoProperties,MappingMongoConverter primaryMappingMongoConverter) throws Exception {
            return new MongoTemplate(primaryMongoDatabaseFactory(primaryMongoProperties),primaryMappingMongoConverter);
        }

        @Bean
        @Primary
        public MongoTransactionManager primaryTransactionManager(MongoDatabaseFactory primaryMongoDatabaseFactory) {
            return new MongoTransactionManager(primaryMongoDatabaseFactory);
        }
    }

    @Profile("mongo-multiple")
    @Configuration
    @EnableMongoRepositories(basePackages = "com.xinchen.project.core.orm.mongo.multiple.secondary",
            mongoTemplateRef = "secondaryMongoTemplate")
    static class SecondaryMongoConfig{
        @Bean
        @ConfigurationProperties("spring.data.mongodb.secondary")
        public MongoProperties secondaryMongoProperties(){
            return new MongoProperties();
        }
        @Bean
        public MongoMappingContext secondaryMongoMappingContext(){
            return new MongoMappingContext();
        }
        @Bean
        public MongoDatabaseFactory  secondaryMongoDatabaseFactory(MongoProperties secondaryMongoProperties){
            return new SimpleMongoClientDatabaseFactory(secondaryMongoProperties.getUri());
        }
        @Bean
        public MappingMongoConverter secondaryMappingMongoConverter(MongoDatabaseFactory secondaryMongoDatabaseFactory,
                                                                  MongoMappingContext secondaryMongoMappingContext

        ){
            DefaultDbRefResolver defaultDbRefResolver = new DefaultDbRefResolver(secondaryMongoDatabaseFactory);
            MappingMongoConverter converter = new MappingMongoConverter(defaultDbRefResolver, secondaryMongoMappingContext);
            // 去除spring data在mongodb中自动生成的_class
            converter.setTypeMapper(new DefaultMongoTypeMapper(null));
            return converter;
        }


        @Bean
        public MongoTemplate secondaryMongoTemplate(MongoProperties secondaryMongoProperties,MappingMongoConverter secondaryMappingMongoConverter) throws Exception {
            return new MongoTemplate(secondaryMongoDatabaseFactory(secondaryMongoProperties),secondaryMappingMongoConverter);
        }

        @Bean
        public MongoTransactionManager secondaryTransactionManager(MongoDatabaseFactory secondaryMongoDatabaseFactory) {
            return new MongoTransactionManager(secondaryMongoDatabaseFactory);
        }
    }
}
