
package com.bernardomg.example.spring.mvc.security.test.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.bernardomg.example.spring.mvc.security.**.repository")
public class PersistenceTestConfig {

    public PersistenceTestConfig() {
        super();
    }

    @Bean("dataSource")
    public DataSource getDataSource() {
        final HikariDataSource dataSource;

        dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:spring-mvc-security-example");
        dataSource.setUsername("da");
        dataSource.setPassword("");
        dataSource.setPoolName("testPool");

        return dataSource;
    }

    @Bean("jpaProperties")
    public Properties getJpaProperties() {
        final Properties properties;

        properties = new Properties();

        return properties;
    }

    @Bean("liquibase")
    public SpringLiquibase getLiquibase(final DataSource dataSource) {
        final SpringLiquibase liquibase;

        liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-test.yaml");

        return liquibase;
    }

    @Bean("entityManagerFactory")
    public LocalSessionFactoryBean getSessionFactory(
            final DataSource dataSource,
            @Qualifier("jpaProperties") final Properties hibernateProperties) {
        final LocalSessionFactoryBean factory;

        factory = new LocalSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan(
                "com.bernardomg.example.**.model.persistence");
        factory.setHibernateProperties(hibernateProperties);

        return factory;
    }

    @Bean("transactionManager")
    public TransactionManager
            getTransactionManager(final SessionFactory sessionFactory) {
        final HibernateTransactionManager transactionManager;

        transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }

}
