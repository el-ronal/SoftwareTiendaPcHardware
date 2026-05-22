package cl.pchardware.pagos.event;

import cl.pchardware.common.event.PagoAprobadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagoEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPagoAprobado(PagoAprobadoEvent event) {
        log.info("Publicando PagoAprobadoEvent → idTransaccion={} idPedido={}",
                event.getIdTransaccion(), event.getIdPedido());
        kafkaTemplate.send("pago.aprobado", String.valueOf(event.getIdPedido()), event);
    }
}
