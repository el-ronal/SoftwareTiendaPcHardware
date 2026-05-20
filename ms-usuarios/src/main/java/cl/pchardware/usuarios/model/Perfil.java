package cl.pchardware.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "perfil",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_perfil_usuario", columnNames = "id_usuario"),
        @UniqueConstraint(name = "uk_perfil_rut", columnNames = "rut")
    },
    indexes = {
        @Index(name = "idx_perfil_rut", columnList = "rut")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil", nullable = false)
    private Long idPerfil;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "rut", nullable = false, length = 12, unique = true)
    private String rut;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "telefono", length = 15)
    private String telefono;

    // --- Métodos vitales para el ciclo de vida en JPA ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return idPerfil != null && idPerfil.equals(perfil.getIdPerfil());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}