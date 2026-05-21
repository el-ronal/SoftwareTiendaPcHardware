package cl.pchardware.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cl.pchardware.pagos.client")
public class TiendaPagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaPagosApplication.class, args);
	}

}
