package cl.pchardware.notificaciones.event;

import cl.pchardware.common.event.PagoAprobadoEvent;
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
public class PagoEventConsumer {

    private final MensajeRepository mensajeRepository;
    private final PlantillaCorreoRepository plantillaRepository;

    @KafkaListener(
        topics = "pago.aprobado",
        groupId = "ms-notificaciones",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPagoAprobado(PagoAprobadoEvent event) {
        log.info("Notificaciones: PagoAprobadoEvent → idPedido={}", event.getIdPedido());
        encolarMensaje(event.getIdUsuario(), "PEDIDO_PAGADO");
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
