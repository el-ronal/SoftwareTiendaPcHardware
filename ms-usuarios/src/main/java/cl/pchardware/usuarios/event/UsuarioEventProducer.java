package cl.pchardware.usuarios.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioEventProducer {

    private static final String TOPICO_USUARIO_REGISTRADO = "usuario-registrado-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(Long idUsuario, String email, String nombreCompleto) {
        String topico = "usuario-registrado-topic";
        
        // Construimos el JSON con los datos clave para que Notificaciones pueda armar el correo
        String payload = String.format("{\"idUsuario\": %d, \"email\": \"%s\", \"nombreCompleto\": \"%s\"}", 
                                       idUsuario, email, nombreCompleto);
        
        log.info("Notificando al ecosistema el registro de un nuevo usuario. Topico: {}, Payload: {}", topico, payload);
        
        // Enviamos el mensaje. Usamos el idUsuario como 'Key' para garantizar el orden en Kafka
        kafkaTemplate.send(topico, String.valueOf(idUsuario), payload);
    }
}