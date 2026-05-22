package cl.pchardware.catalogo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "categoria",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_categoria_slug", columnNames = "slug")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    @Column(name = "slug", nullable = false, length = 50, unique = true)
    private String slug;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return idCategoria != null && idCategoria.equals(categoria.getIdCategoria());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}