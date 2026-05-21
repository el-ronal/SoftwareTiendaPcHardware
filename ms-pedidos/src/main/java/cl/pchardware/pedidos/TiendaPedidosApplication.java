package cl.pchardware.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients (basePackages = "cl.pchardware.pedidos.client")
@SpringBootApplication
public class TiendaPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaPedidosApplication.class, args);
	}

}
