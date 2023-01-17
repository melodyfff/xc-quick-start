package com.xinchen.project.core.orm.jpa;

import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 21:26
 */
@Profile("jpa")
@Import({DataSourceAutoConfiguration.class,})
@EntityScan(basePackageClasses = JpaConfiguration.class)
@EnableJpaRepositories(basePackageClasses = JpaConfiguration.class,transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@EnableJpaAuditing
@Configuration
class JpaConfiguration {
  /**
   * PlatformTransactionManager
   * @param entityManagerFactory EntityManagerFactory
   * @return JpaTransactionManager
   */
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }

  @Profile("jpa")
  @Configuration
  static class AuditorConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
      // 审计时使用 CreatedBy LastModifiedBy
      return Optional.of("ADMIN");
    }
  }
}
