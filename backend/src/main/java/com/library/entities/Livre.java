package com.library.entities;

import jakarta.persistence.Entity;

@Entity
public class Livre extends Ouvrage {

    private String auteur;
    private Long isbn;
    private Integer datePublication;

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public Long getIsbn() { return isbn; }
    public void setIsbn(Long isbn) { this.isbn = isbn; }

    public Integer getDatePublication() { return datePublication; }
    public void setDatePublication(Integer datePublication) { this.datePublication = datePublication; }
}
