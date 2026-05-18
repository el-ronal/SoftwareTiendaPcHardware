package cl.pchardware.tasacion.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "solicitud_tasacion")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudTasacion {

    public enum EstadoSolicitud {

        PENDIENTE,
        EN_REVISION,
        TASADO,
        RECHAZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud", nullable = false)
    private Integer idSolicitud;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @Column(name = "hardware_descripcion", nullable = false, length = 255)
    private String hardwareDescripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_solicitud", nullable = false, length = 20)
    private EstadoSolicitud estadoSolicitud;

    @CreatedDate
    @Column(name = "fecha_ingreso", updatable = false)
    private LocalDateTime fechaIngreso;

    @OneToOne(mappedBy = "solicitudTasacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EvaluacionTecnica evaluacionTecnica;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SolicitudTasacion that = (SolicitudTasacion) o;
        return idSolicitud != null && Objects.equals(idSolicitud, that.idSolicitud);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}