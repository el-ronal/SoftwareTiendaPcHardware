package cl.pchardware.envios.model;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "despacho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despacho", nullable = false)
    private Integer idDespacho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_direccion", nullable = false, foreignKey = @ForeignKey(name = "fk_despacho_direccion"))
    private DireccionEnvio direccionEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_courier", nullable = false, foreignKey = @ForeignKey(name = "fk_despacho_courier"))
    private Courier courier;

    @Column(name = "codigo_seguimiento", unique = true, length = 50)
    private String codigoSeguimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_logistico", nullable = false, length = 20)
    private EstadoLogistico estadoLogistico;

    public enum EstadoLogistico {
        PREPARACION, TRANSITO, REPARTO, ENTREGADO, EXTRAVIADO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Despacho despacho = (Despacho) o;
        return idDespacho != null && idDespacho.equals(despacho.idDespacho);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
