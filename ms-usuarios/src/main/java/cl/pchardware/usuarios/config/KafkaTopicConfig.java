package cl.pchardware.usuarios.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    // Extraemos el nombre a una constante (debe coincidir con el del Producer)
    public static final String TOPICO_USUARIO = "ecosistema.usuario";

    @Bean
    public NewTopic usuarioTopic() {
        return TopicBuilder.name(TOPICO_USUARIO)
                .partitions(3) // 3 particiones es un buen estándar para balancear carga
                .replicas(1)   // 1 réplica para entorno local (en Prod suele ser 3)
                .build();
    }
}