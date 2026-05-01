package cl.pchardware.pagos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "monto_clp", nullable = false)
    private Integer montoClp;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @OneToOne(mappedBy = "transaccion", cascade = CascadeType.ALL)
    private Reembolso reembolso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(idTransaccion, that.idTransaccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaccion);
    }
}