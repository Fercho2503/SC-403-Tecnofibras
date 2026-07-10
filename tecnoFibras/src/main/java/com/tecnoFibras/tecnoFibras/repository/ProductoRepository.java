import com.tiendaTech.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
    // Para mostrar solo los productos activos en la tienda
    List<Producto> findByActivoTrue();
    
    // Súper útil para filtrar el catálogo: Bañeras, Pilas o Bases de ducha
    List<Producto> findByIdCategoria(Integer idCategoria);
    
    // Combina ambos filtros: productos activos de una categoría específica
    List<Producto> findByIdCategoriaAndActivoTrue(Integer idCategoria);
}
