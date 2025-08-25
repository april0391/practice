package edu.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

   /* @Bean
    public FilterRegistrationBean<Filter> filterFilterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new DemoJwtFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");
        return bean;
    }*/

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
