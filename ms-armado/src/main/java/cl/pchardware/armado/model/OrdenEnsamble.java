package cl.pchardware.armado.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orden_ensamble")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenEnsamble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    @Column(name = "id_pedido", nullable = false, unique = true)
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", nullable = false)
    private TecnicoArmado tecnico;

    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "EN_COLA";

    @CreatedDate
    @Column(name = "fecha_inicio", updatable = false)
    private LocalDateTime fechaInicio;

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private ResultadoTesting resultadoTesting;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrdenEnsamble that = (OrdenEnsamble) o;
        return Objects.equals(idOrden, that.idOrden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrden);
    }
}