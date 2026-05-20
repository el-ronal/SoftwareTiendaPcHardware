package cl.pchardware.soporte.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncuestaSatisfaccionRequest {

    private Integer idTicket;
    private Integer estrellas;
    private String comentario;
}