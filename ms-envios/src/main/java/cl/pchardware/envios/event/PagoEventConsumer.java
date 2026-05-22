package cl.pchardware.envios.event;

import cl.pchardware.common.event.PagoAprobadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagoEventConsumer {

    @KafkaListener(
        topics = "pago.aprobado",
        groupId = "ms-envios",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPagoAprobado(PagoAprobadoEvent event) {
        log.info("Envios: PagoAprobadoEvent → idPedido={}", event.getIdPedido());
        log.info("Pedido {} aprobado, pendiente registrar dirección de envío.", event.getIdPedido());
        // La dirección se registra cuando el cliente hace POST /api/v1/direcciones
    }
}