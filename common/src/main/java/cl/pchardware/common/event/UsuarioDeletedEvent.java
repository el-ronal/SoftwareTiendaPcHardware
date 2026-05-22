package cl.pchardware.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UsuarioDeletedEvent extends UsuarioEvent {
    private String email;
    private String nombreCompleto;
}

// Puedes crear UsuarioUpdatedEvent y UsuarioDeletedEvent de la misma forma