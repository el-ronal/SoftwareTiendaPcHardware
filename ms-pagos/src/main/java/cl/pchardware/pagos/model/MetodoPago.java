package cl.pchardware.pagos.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "metodo_pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo", nullable = false)
    private Integer idMetodo;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @OneToMany(mappedBy = "metodoPago", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetodoPago that = (MetodoPago) o;
        return idMetodo != null && idMetodo.equals(that.idMetodo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
