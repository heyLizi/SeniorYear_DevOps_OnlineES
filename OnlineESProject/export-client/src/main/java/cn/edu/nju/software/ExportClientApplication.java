package cn.edu.nju.software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ExportClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExportClientApplication.class, args);
	}
}
