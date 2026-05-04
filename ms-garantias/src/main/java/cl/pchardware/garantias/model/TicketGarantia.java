package cl.pchardware.garantias.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "ticket_garantia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketGarantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket", nullable = false)
    private Integer idTicket;

    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @Column(name = "sku_producto", nullable = false, length = 30)
    private String skuProducto;

    @Column(name = "motivo_cliente", nullable = false, length = 255)
    private String motivoCliente;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @OneToOne(mappedBy = "ticketGarantia", cascade = CascadeType.ALL)
    private InspeccionTecnica inspeccionTecnica;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketGarantia that = (TicketGarantia) o;
        return Objects.equals(idTicket, that.idTicket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket);
    }
}