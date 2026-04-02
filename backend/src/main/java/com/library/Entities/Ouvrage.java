package com.library.Entities;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ouvrage implements Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String genre;

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
}