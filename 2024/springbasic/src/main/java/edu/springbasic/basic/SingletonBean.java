package edu.springbasic.basic;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class SingletonBean {

    @PostConstruct
    public void init() {
        System.out.println("SingletonBean.init");
    }

    @PreDestroy
    public void close() {
        System.out.println("SingletonBean.close");
    }
}
