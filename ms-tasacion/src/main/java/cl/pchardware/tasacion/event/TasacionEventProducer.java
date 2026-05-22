package cl.pchardware.tasacion.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TasacionEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void enviarEventoTasacionCreada(Integer idSolicitud, Integer idUsuario, String hardware) {
        String topico = "solicitud-tasacion-creada-topic";
        String payload = String.format("{\"idSolicitud\": %d, \"idUsuario\": %d, \"hardware\": \"%s\"}", 
                                       idSolicitud, idUsuario, hardware);
        
        log.info("Emitiendo evento Kafka: Topico: {}, Payload: {}", topico, payload);
        String key = idSolicitud != null ? idSolicitud.toString() : "";
        kafkaTemplate.send(topico, Objects.requireNonNull(key, "key must not be null"),
            Objects.requireNonNull(payload, "payload must not be null"));
    }
}