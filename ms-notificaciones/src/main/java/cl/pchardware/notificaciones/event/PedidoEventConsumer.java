package cl.pchardware.notificaciones.event;

import cl.pchardware.common.event.PedidoCreadoEvent;
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
public class PedidoEventConsumer {

    private final MensajeRepository mensajeRepository;
    private final PlantillaCorreoRepository plantillaRepository;

    @KafkaListener(
        topics = "pedido.creado",
        groupId = "ms-notificaciones",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPedidoCreado(PedidoCreadoEvent event) {
        log.info("Notificaciones: PedidoCreadoEvent → idPedido={} idUsuario={}",
                event.getIdPedido(), event.getIdUsuario());
        encolarMensaje(event.getIdUsuario(), "PEDIDO_CREADO");
    }

    private void encolarMensaje(Integer idUsuario, String codigoEvento) {
        Optional<PlantillaCorreo> plantilla = plantillaRepository.findByCodigoEvento(codigoEvento);
        if (plantilla.isEmpty()) {
            log.warn("No se encontró plantilla para evento '{}'", codigoEvento);
            return;
        }
        Mensaje mensaje = Mensaje.builder()
                .idUsuario(idUsuario)
                .plantilla(plantilla.get())
                .estadoMensaje("PENDIENTE")
                .build();
        mensajeRepository.save(mensaje);
        log.info("Mensaje encolado para usuario {} con plantilla '{}'", idUsuario, codigoEvento);
    }
}
