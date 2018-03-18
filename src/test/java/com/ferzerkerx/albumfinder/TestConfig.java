package com.ferzerkerx.albumfinder;

import com.ferzerkerx.albumfinder.config.DbConfig;
import com.ferzerkerx.albumfinder.config.SecurityWebConfig;
import com.ferzerkerx.albumfinder.service.ResponseEntityService;
import com.ferzerkerx.albumfinder.service.ResponseEntityServiceImpl;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:test_application.properties")
@ComponentScan(basePackages = {
    "com.ferzerkerx.albumfinder",
})
@Import({DbConfig.class, SecurityWebConfig.class})
public class TestConfig  {

    @Bean
    public ResponseEntityService responseEntityService() {
        return new ResponseEntityServiceImpl();
    }

}
