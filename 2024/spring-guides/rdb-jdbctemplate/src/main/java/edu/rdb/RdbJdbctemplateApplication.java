package edu.rdb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Customer {
	private long id;
	private String firstName, lastName;

	public Customer(long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}

@Slf4j
@SpringBootApplication
public class RdbJdbctemplateApplication {

	@Bean
	ApplicationRunner applicationRunner(JdbcTemplate jdbcTemplate) {
		return args -> {
			jdbcTemplate.execute("CREATE TABLE customers (id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
			List<Object[]> names = List.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
					.stream()
					.map(name -> name.split(" "))
					.collect(Collectors.toList());

			jdbcTemplate.batchUpdate("INSERT INTO customers (first_name, last_name) VALUES (?,?)", names);

			List<Customer> query = jdbcTemplate.query(
					"SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
					new Object[]{ "Josh" },
					(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
			query.forEach(customer -> log.info("customer = {}", customer));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(RdbJdbctemplateApplication.class, args);
	}

}
