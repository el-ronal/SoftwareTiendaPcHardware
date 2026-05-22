package cl.pchardware.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Evento genérico para solicitar el envío de una notificación.
 * Cualquier microservicio puede publicarlo; ms-notificaciones lo consume.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionEvent {
    private Integer idUsuario;
    private String codigoEvento;   // Debe coincidir con un PlantillaCorreo.codigoEvento
    private String contexto;       // JSON o texto libre con datos para personalizar el cuerpo
}
