package cl.pchardware.armado.event;

import cl.pchardware.common.event.PedidoCreadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Escucha pedidos recién creados.
 * En el contexto de PCHardware, un pedido de armado es aquel donde el cliente
 * contrató el servicio de ensamblaje. La lógica de detección se puede implementar
 * verificando si el pedido contiene un SKU de servicio de armado.
 */
@Slf4j
@Component
public class PedidoEventConsumer {

    @KafkaListener(
        topics = "pedido.creado",
        groupId = "ms-armado",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPedidoCreado(PedidoCreadoEvent event) {
        log.info("Armado: PedidoCreadoEvent → idPedido={} idUsuario={}",
                event.getIdPedido(), event.getIdUsuario());
        // Hook: verificar si el pedido incluye servicio de armado
        // y crear OrdenEnsamble automáticamente si aplica.
    }
}
