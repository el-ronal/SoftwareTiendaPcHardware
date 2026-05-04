package cl.pchardware.soporte.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "mensaje_ticket")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje", nullable = false)
    private Integer idMensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket", nullable = false)
    private TicketSoporte ticketSoporte;

    @Column(name = "remitente", nullable = false, length = 20)
    private String remitente;

    @Column(name = "contenido", nullable = false, length = 500)
    private String contenido;

    @CreatedDate
    @Column(name = "fecha_envio", updatable = false)
    private LocalDateTime fechaEnvio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensajeTicket that = (MensajeTicket) o;
        return Objects.equals(idMensaje, that.idMensaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMensaje);
    }
}