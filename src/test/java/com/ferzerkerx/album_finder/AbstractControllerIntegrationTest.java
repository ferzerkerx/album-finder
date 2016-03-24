package com.ferzerkerx.album_finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class})
@WebAppConfiguration
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = createMockMvc();
    }


    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    protected static byte[] toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }

    protected WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    private MockMvc createMockMvc() {
        return MockMvcBuilders.webAppContextSetup(getWebApplicationContext()).build();
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
        // If the interface type does not uniquely distinguish a bean, make sure we add a bean that can match by name.
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
