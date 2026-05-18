package cl.pchardware.envios.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_courier", nullable = false)
    private Integer idCourier;

    @Column(name = "codigo", nullable = false, unique = true, length = 15)
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
