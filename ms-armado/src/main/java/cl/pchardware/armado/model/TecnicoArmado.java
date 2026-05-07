package cl.pchardware.armado.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tecnico_armado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoArmado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Integer idTecnico;

    @Column(name = "id_usuario", nullable = false, unique = true)
    private Integer idUsuario;

    @Column(name = "especialidad", nullable = false, length = 30)
    private String especialidad;

    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    @OneToMany(mappedBy = "tecnico")
    private List<OrdenEnsamble> ordenes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TecnicoArmado that = (TecnicoArmado) o;
        return Objects.equals(idTecnico, that.idTecnico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTecnico);
    }
}