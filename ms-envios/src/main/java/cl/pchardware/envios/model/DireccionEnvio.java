package cl.pchardware.envios.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "direccion_envio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DireccionEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion", nullable = false)
    private Integer idDireccion;

    @Column(name = "id_pedido", nullable = false, unique = true)
    private Integer idPedido;

    @Column(name = "calle_numero", nullable = false, length = 100)
    private String calleNumero;

    @Column(name = "comuna", nullable = false, length = 50)
    private String comuna;

    @Column(name = "region", nullable = false, length = 50)
    private String region;

    @OneToMany(mappedBy = "direccionEnvio", cascade = CascadeType.ALL)
    private List<Despacho> despachos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DireccionEnvio that = (DireccionEnvio) o;
        return idDireccion != null && idDireccion.equals(that.idDireccion);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
