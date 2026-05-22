package cl.pchardware.armado.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer; // Importante agregar esta interfaz
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ms-armado");
        
        // Declaramos explícitamente usando la interfaz Deserializer
        Deserializer<String> keyDeserializer = new StringDeserializer();
        
        JsonDeserializer<Object> valueDeserializer = new JsonDeserializer<>(Object.class);
        valueDeserializer.addTrustedPackages("cl.pchardware.common.event");
        valueDeserializer.setUseTypeMapperForKey(false);
        valueDeserializer.setUseTypeHeaders(false); 

        // Especificamos <String, Object> explícitamente en vez de usar el diamante <>
        return new DefaultKafkaConsumerFactory<String, Object>(props, keyDeserializer, valueDeserializer);
    }

    @Bean
    @SuppressWarnings("null") 
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}