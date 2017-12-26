package com.ferzerkerx.albumfinder;

import com.ferzerkerx.albumfinder.service.AlbumFinderService;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:test_application.properties")
@ComponentScan(basePackages = {
    "com.ferzerkerx.albumfinder",
})
@Import({DbConfig.class, SecurityWebConfig.class})
public class TestConfig  {


}
