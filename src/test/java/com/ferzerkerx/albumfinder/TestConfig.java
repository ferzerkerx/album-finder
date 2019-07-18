package com.ferzerkerx.albumfinder;

import com.ferzerkerx.albumfinder.config.SecurityWebConfig;
import com.ferzerkerx.albumfinder.repository.AlbumRepository;
import com.ferzerkerx.albumfinder.repository.ArtistRepository;
import com.ferzerkerx.albumfinder.service.ResponseEntityService;
import com.ferzerkerx.albumfinder.service.ResponseEntityServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:test_application.properties")
@ComponentScan(basePackages = {
    "com.ferzerkerx.albumfinder",
})
@Import({SecurityWebConfig.class})
public class TestConfig  {

    @Bean
    public ResponseEntityService responseEntityService() {
        return new ResponseEntityServiceImpl();
    }


    @Bean
    public ArtistRepository artistRepository() {
        return new ArtistRepository();
    }

    @Bean
    public AlbumRepository albumRepository() {
        return new AlbumRepository();
    }

}
