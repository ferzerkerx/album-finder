package com.ferzerkerx.albumfinder.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DbConfig {

    private final Environment env;

    @Value("${album_finder.pre_populate_db:false}")
    private boolean shouldInsertData;

    @Autowired
    public DbConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder =
            new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScript("sql/create-db.sql");

            if (shouldInsertData) {
                embeddedDatabaseBuilder.addScript("sql/insert-data.sql");
            }

        return embeddedDatabaseBuilder.build();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("com.ferzerkerx.albumfinder.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return properties;
    }
}
