package edu.springbasic.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ScopeTest {

    @Autowired
    ApplicationContext ac;

    @Test
    void prototypeScope() {
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        assertThat(bean1).isNotSameAs(bean2);
    }

    @Test
    void singletonScope() {
        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        assertThat(bean1).isSameAs(bean2);
    }

    @Test
    void destroyTest() {
        AnnotationConfigApplicationContext aac = new AnnotationConfigApplicationContext(TestConfig.class);
        SingletonBean singletonBean = aac.getBean(SingletonBean.class);
        PrototypeBean prototypeBean = aac.getBean(PrototypeBean.class);
        aac.close();
    }

    @Test
    void objectFactoryTest() {
        ClientBean clientBean = ac.getBean(ClientBean.class);
        PrototypeBean bean1 = clientBean.logic();
        PrototypeBean bean2 = clientBean.logic();
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
    }

    @Configuration
    static class TestConfig {

        @Bean
        @Scope("prototype")
        public PrototypeBean prototypeBean() {
            return new PrototypeBean();
        }

        @Bean
        @Scope("singleton")
        public SingletonBean singletonBean() {
            return new SingletonBean();
        }

        @Bean
        public ClientBean clientBean() {
            return new ClientBean();
        }
    }
}
