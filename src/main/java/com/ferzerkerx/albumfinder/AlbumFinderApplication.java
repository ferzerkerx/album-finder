package com.ferzerkerx.albumfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AlbumFinderApplication {

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(corsFilter());
        registration.addUrlPatterns("*");
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }

    private CorsFilter corsFilter() {
        return new CorsFilter();
    }


    public static void main(String[] args) {
        SpringApplication.run(AlbumFinderApplication.class, args);
    }

}
