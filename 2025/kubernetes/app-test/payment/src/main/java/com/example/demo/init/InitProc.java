package com.example.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.ConfigurableWebEnvironment;

@Configuration
public class InitProc {

    @Autowired
    ConfigurableWebEnvironment env;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        printAllProperties();
    }

    public void printAllProperties() {
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            if (propertySource instanceof org.springframework.core.env.MapPropertySource) {
                MapPropertySource mapPropertySource = (MapPropertySource) propertySource;
                for (String propertyName : mapPropertySource.getSource().keySet()) {
                    String value = env.getProperty(propertyName);
                    System.out.println(propertyName + " = " + value);
                }
            }
        }
    }
}
