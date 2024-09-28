package edu.demo.config;

import edu.demo.util.MemoryUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final ApplicationContext ac;
    private final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            int beanCnt = 0;
            String[] beanDefinitionNames = ac.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                beanCnt++;
                System.out.println("beanDefinitionName = " + beanDefinitionName);
            }
            log.info("total bean cnt={}", beanCnt);
            MemoryUtil.printMemory();
        };
    }
}
