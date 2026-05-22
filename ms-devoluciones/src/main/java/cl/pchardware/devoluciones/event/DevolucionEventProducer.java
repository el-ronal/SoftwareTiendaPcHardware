package cl.pchardware.devoluciones.event;

import cl.pchardware.common.event.DevolucionCreadaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DevolucionEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendDevolucionCreada(DevolucionCreadaEvent event) {
        log.info("Publicando DevolucionCreadaEvent → idDevolucion={}", event.getIdDevolucion());
        kafkaTemplate.send("devolucion.creada", String.valueOf(event.getIdDevolucion()), event);
    }
}
