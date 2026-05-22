package cl.pchardware.pagos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion", nullable = false)
    private Integer idTransaccion;

    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo", nullable = false, foreignKey = @ForeignKey(name = "fk_transaccion_metodo"))
    private MetodoPago metodoPago;

    @Column(name = "monto_clp", nullable = false)
    private Integer montoClp;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoTransaccion estado;

    @OneToOne(mappedBy = "transaccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reembolso reembolso;

    public enum EstadoTransaccion {
        PENDIENTE, APROBADA, RECHAZADA, REEMBOLSADA
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return idTransaccion != null && idTransaccion.equals(that.idTransaccion);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
