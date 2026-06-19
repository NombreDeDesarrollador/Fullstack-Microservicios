package cl.duoc.roles_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RolesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RolesServiceApplication.class, args);
	}

}
