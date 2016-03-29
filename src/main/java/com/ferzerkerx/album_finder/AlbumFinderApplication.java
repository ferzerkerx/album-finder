package com.ferzerkerx.album_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AlbumFinderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AlbumFinderApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DbConfig.class, WebConfig.class, SecurityWebConfig.class);
    }

}
