package edu.springbasic.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.support.GenericWebApplicationContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BasicTest {

    @Autowired
    ApplicationContext ac;

    @Test
    void acClassTest() {
        assertThat(ac).isInstanceOf(GenericWebApplicationContext.class);
    }

    @Test
    void beanTest() {
        BasicBean basicBean = ac.getBean(BasicBean.class);
//        ProdBean prodBean = ac.getBean(ProdBean.class);
        assertThatThrownBy(() -> ac.getBean(ProdBean.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);

        assertThat(basicBean).isInstanceOf(BasicBean.class);
    }

    @Test
    void beanNameTest() {
        Object bean = ac.getBean("basicBean");
        System.out.println("bean = " + bean.getClass());
    }

    @Configuration
    static class BeanConfig {

        @Bean
        public BasicBean basicBean() {
            return new BasicBean();
        }

        @Bean
        @Profile("prod")
        public ProdBean prodBean() {
            return new ProdBean();
        }
    }
}
