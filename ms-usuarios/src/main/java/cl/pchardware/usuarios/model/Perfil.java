package cl.pchardware.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "perfil", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id_usuario"),
    @UniqueConstraint(columnNames = "rut")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Integer idPerfil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "rut", nullable = false, length = 12)
    private String rut;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Perfil)) return false;
        Perfil perfil = (Perfil) o;
        return idPerfil != null && idPerfil.equals(perfil.idPerfil);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
