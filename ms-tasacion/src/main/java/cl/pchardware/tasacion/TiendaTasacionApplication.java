package cl.pchardware.tasacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cl.pchardware.tasacion.client")
@EnableJpaAuditing
@SpringBootApplication
public class TiendaTasacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaTasacionApplication.class, args);
	}

}
