package cl.pchardware.notificaciones.event;

import cl.pchardware.common.event.DevolucionCreadaEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DevolucionEventConsumer {

    @KafkaListener(
        topics = "devolucion.creada",
        groupId = "ms-notificaciones",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onDevolucionCreada(DevolucionCreadaEvent event) {
        log.info("Notificaciones: DevolucionCreadaEvent → idDevolucion={} idPedido={}",
                event.getIdDevolucion(), event.getIdPedido());
        // Misma lógica: enriquecer con Feign para obtener idUsuario y encolar mensaje
    }
}
