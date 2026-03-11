package org.example.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "org.example")
@PropertySource("classpath:hibernate.properties") // Hibernate özellikleri için
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        // C3P0 connection pool konfigürasyonu
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(env.getProperty("hibernate.connection.driver_class"));
            dataSource.setJdbcUrl(env.getProperty("hibernate.connection.url"));
            dataSource.setUser(env.getProperty("hibernate.connection.username"));
            dataSource.setPassword(env.getProperty("hibernate.connection.password"));

            // C3P0 pool ayarları
            dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.min_size")));
            dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.max_size")));
            dataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("hibernate.c3p0.timeout")));
            dataSource.setMaxStatements(Integer.parseInt(env.getProperty("hibernate.c3p0.max_statements")));
            dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getProperty("hibernate.c3p0.idle_test_period")));

        } catch (PropertyVetoException e) {
            throw new RuntimeException("Failed to configure datasource", e);
        }
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("org.example.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        // C3P0 özellikleri
        properties.put("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
        properties.put("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
        properties.put("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
        properties.put("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
        properties.put("hibernate.c3p0.idle_test_period", env.getProperty("hibernate.c3p0.idle_test_period"));

        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }
}