package edu.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(JwtApplication.class, args);
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		int totalBeanCnt = 0;
		for (String beanDefinitionName : beanDefinitionNames) {
			totalBeanCnt++;
			System.out.println("beanDefinitionName = " + beanDefinitionName);
		}
		System.out.println("totalBeanCnt = " + totalBeanCnt);
	}

}
