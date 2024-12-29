package edu.springbasic.basic;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicBean {

    @PostConstruct
    public void init() {
        log.info("BasicBean.init");
    }

    @PreDestroy
    public void close() {
        log.info("BasicBean.close");
    }
}
