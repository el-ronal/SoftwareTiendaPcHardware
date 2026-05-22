package cl.pchardware.envios.model;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
    private List<Despacho> despachos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return idCourier != null && idCourier.equals(courier.idCourier);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
