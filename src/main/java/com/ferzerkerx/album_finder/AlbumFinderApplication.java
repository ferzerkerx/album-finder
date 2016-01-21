package com.ferzerkerx.album_finder;

import javax.sql.DataSource;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AlbumFinderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AlbumFinderApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SecurityWebConfig.class, AlbumFinderApplication.class);
    }

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .addScript("sql/create-db.sql")
            .addScript("sql/insert-data.sql").build();
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
            }
        };
    }
}
