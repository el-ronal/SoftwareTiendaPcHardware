package cl.pchardware.pagos.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicPagoAprobado() {
        return TopicBuilder.name("pago.aprobado").partitions(1).replicas(1).build();
    }
}
