import javax.validation.Valid;
import javax.persistence.Entity;
import java.entities.ouvrage.Ouvrage;

@Entity
private class Livre extends Ouvrage {
    @Valid
    private String auteur;
    @Valid
    private Long isbn;
    @Valid
    private int datePublication;

    // Getters et setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return titre;
    }
    public void setTitle(String titre) {
        this.titre = titre;
    }
    public String getAuthor() {
        return auteur;
    }
    public void setAuthor(String auteur) {
        this.auteur = auteur;
    }

    public Long getIsbn() {
        return isbn;
    }
    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public int getDatePublication() {
        return datePublication;
    }
    public void setDatePublication(int datePublication) {
        this.datePublication = datePublication;
    }


}