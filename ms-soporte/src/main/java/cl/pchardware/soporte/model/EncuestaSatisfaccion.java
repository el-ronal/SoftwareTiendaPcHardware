package cl.pchardware.soporte.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "encuesta_satisfaccion")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncuestaSatisfaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encuesta", nullable = false)
    private Integer idEncuesta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket", referencedColumnName = "id_ticket", unique = true, nullable = false)
    private TicketSoporte ticketSoporte;

    @Column(name = "estrellas", nullable = false)
    private Integer estrellas;

    @Column(name = "comentario", length = 255)
    private String comentario;

    @CreatedDate
    @Column(name = "fecha_respuesta", updatable = false)
    private LocalDateTime fechaRespuesta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncuestaSatisfaccion that = (EncuestaSatisfaccion) o;
        return Objects.equals(idEncuesta, that.idEncuesta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEncuesta);
    }
}