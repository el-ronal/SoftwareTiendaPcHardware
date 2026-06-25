package cl.pchardware.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class DetallePedidoResponse extends RepresentationModel<DetallePedidoResponse> {

    private Integer idDetalle;
    private Integer idPedido;
    private String skuProducto;
    private Integer cantidad;
    private Integer precioUnitario;
}
