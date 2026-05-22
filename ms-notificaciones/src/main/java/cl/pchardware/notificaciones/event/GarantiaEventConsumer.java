package cl.pchardware.notificaciones.event;

import cl.pchardware.common.event.GarantiaCreadaEvent;
import cl.pchardware.notificaciones.model.Mensaje;
import cl.pchardware.notificaciones.model.PlantillaCorreo;
import cl.pchardware.notificaciones.repository.MensajeRepository;
import cl.pchardware.notificaciones.repository.PlantillaCorreoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GarantiaEventConsumer {

    private final MensajeRepository mensajeRepository;
    private final PlantillaCorreoRepository plantillaRepository;

    @KafkaListener(
        topics = "garantia.creada",
        groupId = "ms-notificaciones",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onGarantiaCreada(GarantiaCreadaEvent event) {
        log.info("Notificaciones: GarantiaCreadaEvent → idTicket={}", event.getIdTicket());
        // En este caso no tenemos idUsuario directo; se podría enriquecer
        // con un Feign call a ms-pedidos para obtener el idUsuario del pedido.
        log.info("Garantía {} creada para pedido {}", event.getIdTicket(), event.getIdPedido());
    }
}
