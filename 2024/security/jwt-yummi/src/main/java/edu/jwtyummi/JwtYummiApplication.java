package edu.jwtyummi;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@SpringBootApplication
public class JwtYummiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(JwtYummiApplication.class, args);
		FilterChainProxy bean = ac.getBean(FilterChainProxy.class);
		List<SecurityFilterChain> filterChains = bean.getFilterChains();
		filterChains.forEach(chain -> {
			System.out.println("Filter chain: " + chain);
			chain.getFilters().forEach(filter -> {
				System.out.println("Filter: " + filter.getClass().getName());
			});
		});
	}

}
