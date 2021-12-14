package com.ferzerkerx.albumfinder.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:test_application.properties")
@Import({DbConfig.class})
public class DbTestConfig {

}
