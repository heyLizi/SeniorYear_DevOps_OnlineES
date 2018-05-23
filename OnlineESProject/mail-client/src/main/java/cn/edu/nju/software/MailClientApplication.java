package cn.edu.nju.software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MailClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailClientApplication.class, args);
	}
}
