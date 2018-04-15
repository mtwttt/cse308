package cse308.zsyj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "cse308.zsyj.*")
@EntityScan(basePackages = "objects")
@SpringBootApplication
public class ZsyjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZsyjApplication.class, args);
	}
}
