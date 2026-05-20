package cl.pchardware.soporte.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncuestaSatisfaccionResponse {

    private Integer idEncuesta;
    private Integer idTicket;
    private Integer estrellas;
    private String comentario;
    private LocalDateTime fechaRespuesta;
}