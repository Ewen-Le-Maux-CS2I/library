package com.library.entities;

import jakarta.persistence.*;

@Entity
public class Exemplaire {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String cote;
  private boolean disponible = true;

  @ManyToOne
  @JoinColumn(name = "ouvrage_id")
  private Ouvrage ouvrage;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCote() {
    return cote;
  }

  public void setCote(String cote) {
    this.cote = cote;
  }

  public boolean isDisponible() {
    return disponible;
  }

  public void setDisponible(boolean disponible) {
    this.disponible = disponible;
  }

  public Ouvrage getOuvrage() {
    return ouvrage;
  }

  public void setOuvrage(Ouvrage ouvrage) {
    this.ouvrage = ouvrage;
  }
}
