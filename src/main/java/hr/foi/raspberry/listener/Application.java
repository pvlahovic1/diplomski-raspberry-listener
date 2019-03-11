package hr.foi.raspberry.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}

