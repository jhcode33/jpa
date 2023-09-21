package jpabook.jpashop.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "jpabook.jpashop.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = {"jpabook.jpashop.service", "jpabook.jpashop.repository"})
public class AppConfig {

//    <!-- JPA 예외를 스프링 예외로 변환 -->
//    <bean class="org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor"/>
    @Bean
    public PersistenceExceptionTranslationPostProcessor processor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
