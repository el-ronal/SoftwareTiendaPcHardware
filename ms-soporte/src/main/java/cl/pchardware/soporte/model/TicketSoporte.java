package cl.pchardware.soporte.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ticket_soporte")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket", nullable = false)
    private Integer idTicket;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "categoria", nullable = false, length = 30)
    private String categoria;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @CreatedDate
    @Column(name = "fecha_apertura", updatable = false)
    private LocalDateTime fechaApertura;

    @OneToMany(mappedBy = "ticketSoporte", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MensajeTicket> mensajes;

    @OneToOne(mappedBy = "ticketSoporte", cascade = CascadeType.ALL)
    private EncuestaSatisfaccion encuesta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketSoporte that = (TicketSoporte) o;
        return Objects.equals(idTicket, that.idTicket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket);
    }
}