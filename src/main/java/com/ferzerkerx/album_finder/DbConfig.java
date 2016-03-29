package com.ferzerkerx.album_finder;

import javax.sql.DataSource;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class DbConfig {

    @Autowired
    private Environment env;

    @Value("${album_finder.pre_populate_db}")
    private boolean shouldInsertData;

    @Bean
    public DataSource getDataSource() {
        //This can be changed depending on the profile
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder =
            new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScript("sql/create-db.sql");

            if (shouldInsertData) {
                embeddedDatabaseBuilder.addScript("sql/insert-data.sql");
            }

        return embeddedDatabaseBuilder.build();
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan("com.ferzerkerx.album_finder.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
            }
        };
    }
}
