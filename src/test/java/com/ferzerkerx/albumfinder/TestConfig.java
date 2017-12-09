package com.ferzerkerx.albumfinder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:test_application.properties")
@ComponentScan(basePackages = {
    "com.ferzerkerx.albumfinder",
})
@Import({DbConfig.class, SecurityWebConfig.class})
public class TestConfig  {


}
