package com.library.entities;

import com.library.state.EtatEmprunt;
import com.library.state.Rendu;
import com.library.state.Perdu;
import com.library.state.EnRetard;
import com.library.state.EnCours;
import jakarta.persistence.*;
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

  // Nom de l'état persisté en base pour l'affichage
  private String nomEtat = "En cours";

  // ── Getters / Setters ────────────────────────────────────────────────────

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDateEmprunt() {
    return dateEmprunt;
  }

  public void setDateEmprunt(LocalDate dateEmprunt) {
    this.dateEmprunt = dateEmprunt;
  }

  public LocalDate getDateRetourPrevue() {
    return dateRetourPrevue;
  }

  public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
    this.dateRetourPrevue = dateRetourPrevue;
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

  // Appelé par les classes d'état pour changer l'état courant
  public void setEtatActuel(EtatEmprunt nouvelEtat) {
    this.etatActuel = nouvelEtat;
    this.nomEtat = nouvelEtat.getNom();
  }

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
    if (etatActuel == null) etatActuel = new EnCours();
    etatActuel.retourner(this);
  }

  public void declarerPerte() {
    if (etatActuel == null) etatActuel = new EnCours();
    etatActuel.declarerPerte(this);
  }

  public void signalerRetard() {
    if (etatActuel == null) etatActuel = new EnCours();
    etatActuel.signalerRetard(this);
  }
}
