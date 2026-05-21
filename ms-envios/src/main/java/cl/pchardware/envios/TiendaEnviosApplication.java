package cl.pchardware.envios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cl.pchardware.envios.client")
public class TiendaEnviosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaEnviosApplication.class, args);
	}

}
