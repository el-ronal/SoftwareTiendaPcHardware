package cl.pchardware.usuarios.event;

import cl.pchardware.common.event.UsuarioEvent;
import cl.pchardware.common.event.UsuarioCreatedEvent;
import cl.pchardware.common.event.UsuarioUpdatedEvent;
import cl.pchardware.common.event.UsuarioDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioEventProducer {

    private static final String TOPIC_BASE = "ecosistema.usuario";
    private static final String ID_NOT_NULL = "El ID del usuario no puede ser null";
    private static final String TOPIC_NOT_NULL = "El topic no puede ser null";

    // Enviamos directamente el objeto base 'UsuarioEvent'
    private final KafkaTemplate<String, UsuarioEvent> kafkaTemplate;

    // Método privado centralizado y genérico
    private void send(UsuarioEvent event, String eventType) {
        String topic = Objects.requireNonNull(String.format("%s.%s", TOPIC_BASE, eventType), TOPIC_NOT_NULL);
        // Usamos el idUsuario como Key
        String idUsuarioKey = Objects.requireNonNull(String.valueOf(event.getIdUsuario()), ID_NOT_NULL);

        log.debug("********************************");
        log.debug("Enviando evento Kafka -> topic: {}, key: {}", topic, idUsuarioKey);
        log.debug("********************************");

        kafkaTemplate.send(topic, idUsuarioKey, event);
    }

    // Métodos públicos fuertemente tipados
    public void sendCreated(UsuarioCreatedEvent event) {
        send(event, "created");
    }

    public void sendUpdated(UsuarioUpdatedEvent event) {
        send(event, "updated");
    }

    public void sendDeleted(UsuarioDeletedEvent event) {
        send(event, "deleted");
    }
}