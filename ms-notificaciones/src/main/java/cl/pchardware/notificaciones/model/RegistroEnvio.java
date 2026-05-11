package cl.pchardware.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "registro_envio")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer idRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mensaje", nullable = false)
    private Mensaje mensaje;

    @Column(name = "proveedor_smtp", nullable = false, length = 30)
    private String proveedorSmtp;

    @Column(name = "intentos", nullable = false)
    @Builder.Default
    private Integer intentos = 1;

    @LastModifiedDate
    @Column(name = "fecha_ultimo_intento")
    private LocalDateTime fechaUltimoIntento;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegistroEnvio that = (RegistroEnvio) o;
        return Objects.equals(idRegistro, that.idRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegistro);
    }
}