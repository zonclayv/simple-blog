package by.haidash.blog.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SimpleBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBlogApplication.class, args);
	}
}
