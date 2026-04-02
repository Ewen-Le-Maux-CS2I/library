import org.springframework.data.jpa.repository.JpaRepository;
import com.library.Entities.Produit;
public interface ProduitRepository extends JpaRepository<Produit, Long> {
}