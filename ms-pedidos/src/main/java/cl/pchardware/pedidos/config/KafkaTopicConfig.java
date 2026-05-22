package cl.pchardware.pedidos.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicPedidoCreado() {
        return TopicBuilder.name("pedido.creado").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicPedidoActualizado() {
        return TopicBuilder.name("pedido.actualizado").partitions(1).replicas(1).build();
    }
}
