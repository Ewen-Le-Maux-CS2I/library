import javax.validation.Valid;
import javax.persistence.Entity;
import java.entities.ouvrage.Ouvrage;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private Integer caution;
}