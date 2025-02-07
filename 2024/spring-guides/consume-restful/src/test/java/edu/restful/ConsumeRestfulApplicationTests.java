package edu.restful;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

//@SpringBootTest
class ConsumeRestfulApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	@Test
	void test() {
		RestTemplate restTemplate = new RestTemplate();
		Quote forObject = restTemplate.getForObject("http://localhost:8080/api/random", Quote.class);
		System.out.println("forObject = " + forObject);
	}


}
