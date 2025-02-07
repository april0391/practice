package edu.springbasic.basic;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class PrototypeBean {

    @PostConstruct
    public void init() {
        System.out.println("PrototypeBean.init");
    }

    @PreDestroy
    public void close() {
        System.out.println("PrototypeBean.close");
    }
}
