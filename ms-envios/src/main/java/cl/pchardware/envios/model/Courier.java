package cl.pchardware.envios.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courier", uniqueConstraints = {
    @UniqueConstraint(columnNames = "codigo")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_courier")
    private Integer idCourier;

    @Column(name = "codigo", nullable = false, length = 15)
    private String codigo;

    @Column(name = "nombre_empresa", nullable = false, length = 50)
    private String nombreEmpresa;

    @Column(name = "url_rastreo", length = 255)
    private String urlRastreo;

    @OneToMany(mappedBy = "courier")
    private List<Despacho> despachos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return Objects.equals(idCourier, courier.idCourier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCourier);
    }
}