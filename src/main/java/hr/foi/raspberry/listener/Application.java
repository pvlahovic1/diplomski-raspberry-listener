package hr.foi.raspberry.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		for (String s : args) {
			if (s.equals("--forceDeviceConfiguration=true") || s.equals("--forceDeviceConfiguration")) {
				System.setProperty("forceConfiguration", "true");
			}
		}

		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

}

