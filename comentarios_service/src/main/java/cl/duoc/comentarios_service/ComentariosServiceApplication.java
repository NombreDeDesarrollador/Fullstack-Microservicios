package cl.duoc.comentarios_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ComentariosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComentariosServiceApplication.class, args);
	}

}
