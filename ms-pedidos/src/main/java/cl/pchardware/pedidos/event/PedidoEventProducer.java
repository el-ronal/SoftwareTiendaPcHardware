package cl.pchardware.pedidos.event;

import cl.pchardware.common.event.PedidoActualizadoEvent;
import cl.pchardware.common.event.PedidoCreadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPedidoCreado(PedidoCreadoEvent event) {
        log.info("Publicando PedidoCreadoEvent → idPedido={}", event.getIdPedido());
        kafkaTemplate.send("pedido.creado", String.valueOf(event.getIdPedido()), event);
    }

    public void sendPedidoActualizado(PedidoActualizadoEvent event) {
        log.info("Publicando PedidoActualizadoEvent → idPedido={} estado={}→{}",
                event.getIdPedido(), event.getEstadoAnterior(), event.getEstado());
        kafkaTemplate.send("pedido.actualizado", String.valueOf(event.getIdPedido()), event);
    }
}
