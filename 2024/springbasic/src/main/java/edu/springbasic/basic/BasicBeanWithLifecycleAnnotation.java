package edu.springbasic.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicBeanWithLifecycleAnnotation {

    public void init() {
        log.info("BasicBeanWithLifecycleAnnotation.init");
    }

    public void close() {
        log.info("BasicBeanWithLifecycleAnnotation.close");
    }
}
