import jakarta.validation.Valid;
import jakarta.persistence.*;
import java.com.library.Entities.Ouvrage;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private Integer caution;
}