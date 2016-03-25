package com.ferzerkerx.album_finder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:test_application.properties")
@ComponentScan(basePackages = {
    "com.ferzerkerx.album_finder",
})
@Import({DbConfig.class, SecurityWebConfig.class})
public class TestConfig  {


}
