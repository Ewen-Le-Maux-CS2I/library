package com.library.entities;

import com.library.state.EnCours;
import com.library.state.EnRetard;
import com.library.state.EtatEmprunt;
import com.library.state.Perdu;
import com.library.state.Rendu;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Transient;
import java.time.LocalDate;

@Entity
public class Emprunt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate dateEmprunt = LocalDate.now();
  private LocalDate dateRetourPrevue;

  @ManyToOne
  @JoinColumn(name = "exemplaire_id")
  private Exemplaire exemplaire;

  @Transient private EtatEmprunt etatActuel;

  private String nomEtat = "En cours";

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDateEmprunt() {
    return dateEmprunt;
  }

  public void setDateEmprunt(LocalDate d) {
    this.dateEmprunt = d;
  }

  public LocalDate getDateRetourPrevue() {
    return dateRetourPrevue;
  }

  public void setDateRetourPrevue(LocalDate d) {
    this.dateRetourPrevue = d;
  }

  public Exemplaire getExemplaire() {
    return exemplaire;
  }

  public void setExemplaire(Exemplaire exemplaire) {
    this.exemplaire = exemplaire;
  }

  public String getNomEtat() {
    return nomEtat;
  }

  public void setEtatActuel(EtatEmprunt nouvelEtat) {
    this.etatActuel = nouvelEtat;
    this.nomEtat = nouvelEtat.getNom();
  }

  // Reconstitue l'état depuis le nom persisté en base
  @PostLoad
  @PostPersist
  public void initEtat() {
    switch (nomEtat == null ? "En cours" : nomEtat) {
      case "Rendu" -> this.etatActuel = new Rendu();
      case "Perdu" -> this.etatActuel = new Perdu();
      case "En retard" -> this.etatActuel = new EnRetard();
      default -> this.etatActuel = new EnCours();
    }
  }

  public void retourner() {
    if (etatActuel == null) {
      initEtat();
    }
    etatActuel.retourner(this);
  }

  public void declarerPerte() {
    if (etatActuel == null) {
      initEtat();
    }
    etatActuel.declarerPerte(this);
  }

  public void signalerRetard() {
    if (etatActuel == null) {
      initEtat();
    }
    etatActuel.signalerRetard(this);
  }
}
