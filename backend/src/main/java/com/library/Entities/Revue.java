import jakarta.validation.Valid;
import jakarta.persistence.Entity;
import java..com.library.Entities.Ouvrage;

@Entity
public class Revue extends Ouvrage {
    private String numero;
    private LocalDate dateParution;
}