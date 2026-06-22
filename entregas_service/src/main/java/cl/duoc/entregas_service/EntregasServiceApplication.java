package cl.duoc.entregas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication

public class EntregasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntregasServiceApplication.class, args);
	}

}
