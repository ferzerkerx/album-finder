package com.ferzerkerx.album_finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ferzerkerx.album_finder.model.Album;
import com.ferzerkerx.album_finder.model.Artist;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class})
@WebAppConfiguration
@Transactional(transactionManager="transactionManager")
@Rollback
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseIntegrationTest {


    //TODO move to a fixture class?
    protected static Artist createArtist() {
        Artist artist = new Artist();
        artist.setName("someArtist");
        artist.setId(1);
        return artist;
    }

    protected Album createAlbum(Artist artist) {
        Album album = createAlbum();
        album.setArtist(artist);
        return album;
    }

    protected static Album createAlbum() {
        Album album = new Album();
        album.setTitle("some title");
        album.setYear("2016");
        album.setId(1);
        return album;
    }

    public static <T> void setBinding(ApplicationContext applicationContext, Class<?> interfaceType, T singleton) {

        if (!(interfaceType.isAssignableFrom(singleton.getClass()))) {
            throw new RuntimeException(
                "Cannot bind object of type >" + singleton.getClass().getName() + "< to type >" + interfaceType.getName() + "<.");
        }

        BeanDefinitionRegistry factory = getBeanDefinitionRegistry(applicationContext);
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) factory;

        List<String> beanNames = new ArrayList<>();
        beanNames.addAll(Arrays.asList(applicationContext.getBeanNamesForType(interfaceType)));

        if (beanNames.isEmpty()) {
            final String className = interfaceType.getSimpleName();
            final String interfaceBeanName = className.substring(0, 1).toLowerCase() + className.substring(1);
            if (!beanNames.contains(interfaceBeanName)) {
                beanNames.add(interfaceBeanName);
            }
        }

        for (int i = 0; i < beanNames.size(); i++) {
            final String beanName = beanNames.get(i);
            if (factory.containsBeanDefinition(beanName)) {
                factory.removeBeanDefinition(beanName);
            }
            if (listableBeanFactory.containsBean(beanName)) {
                listableBeanFactory.destroySingleton(beanName);
            }
            final GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(interfaceType);
            beanDefinition.setPrimary(i == 0);
            factory.registerBeanDefinition(beanName, beanDefinition);
            listableBeanFactory.registerSingleton(beanName, singleton);
        }
    }

    private static BeanDefinitionRegistry getBeanDefinitionRegistry(ApplicationContext webApplicationContext) {
        return (BeanDefinitionRegistry) webApplicationContext.getAutowireCapableBeanFactory();
    }

}
