package cl.duoc.integrantes_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IntegrantesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrantesServiceApplication.class, args);
	}

}
