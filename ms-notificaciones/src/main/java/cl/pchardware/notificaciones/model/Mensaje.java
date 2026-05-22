package cl.pchardware.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mensaje")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Integer idMensaje;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla", nullable = false)
    private PlantillaCorreo plantilla;

    @Column(name = "estado_mensaje", nullable = false, length = 20)
    @Builder.Default
    private String estadoMensaje = "PENDIENTE";

    @CreatedDate
    @Column(name = "fecha_generacion", updatable = false)
    private LocalDateTime fechaGeneracion;

    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL)
    private List<RegistroEnvio> registrosEnvio;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Mensaje mensaje = (Mensaje) o;
        return Objects.equals(idMensaje, mensaje.idMensaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMensaje);
    }
}