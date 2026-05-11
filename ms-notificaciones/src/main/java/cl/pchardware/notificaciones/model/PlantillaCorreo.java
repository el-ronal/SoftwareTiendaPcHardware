package cl.pchardware.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plantilla_correo", uniqueConstraints = {
    @UniqueConstraint(columnNames = "codigo_evento")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantillaCorreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Integer idPlantilla;

    @Column(name = "codigo_evento", nullable = false, length = 30)
    private String codigoEvento;

    @Column(name = "asunto", nullable = false, length = 100)
    private String asunto;

    @Column(name = "cuerpo_html", nullable = false, length = 255)
    private String cuerpoHtml;

    @OneToMany(mappedBy = "plantilla", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantillaCorreo that = (PlantillaCorreo) o;
        return Objects.equals(idPlantilla, that.idPlantilla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlantilla);
    }
}