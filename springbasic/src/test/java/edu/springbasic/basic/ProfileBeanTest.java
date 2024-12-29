package edu.springbasic.basic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;

//@TestPropertySource(properties = "spring.profiles.active=prod")
@SpringBootTest(properties = "spring.profiles.active=prod")
public class ProfileBeanTest {

    @Autowired
    ApplicationContext ac;

    @Test
    void beanTest() {
        BasicBean basicBean = ac.getBean(BasicBean.class);
        ProdBean prodBean = ac.getBean(ProdBean.class);

        assertThat(basicBean).isInstanceOf(BasicBean.class);
        assertThat(prodBean).isInstanceOf(ProdBean.class);
    }

    @Test
    void destroyTest() {
        ConfigurableApplicationContext cac = new AnnotationConfigApplicationContext(BeanConfig.class);
        BasicBeanWithLifecycleAnnotation bean = cac.getBean(BasicBeanWithLifecycleAnnotation.class);
        cac.close();
    }

    @Configuration
    static class BeanConfig {

        @Bean
        public BasicBean basicBean() {
            return new BasicBean();
        }

//        @Bean(initMethod = "init", destroyMethod = "close")
//        @Bean(initMethod = "init", destroyMethod = "")
        @Bean(initMethod = "init")
        public BasicBeanWithLifecycleAnnotation basicBeanWithLifecycleAnnotation() {
            return new BasicBeanWithLifecycleAnnotation();
        }

        @Bean
        @Profile("prod")
        public ProdBean prodBean() {
            return new ProdBean();
        }
    }
}
