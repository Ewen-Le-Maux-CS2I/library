package com.library.entities;

import com.library.state.EnCours;
import com.library.state.EtatEmprunt;
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

  // ── Initialisation de l'état transient après chargement JPA ─────────────

  @PostLoad
  @PostPersist
  public void initEtat() {
    if (etatActuel == null) {
      etatActuel = new EnCours();
    }
  }

  // ── Méthodes métier (délégation au State pattern) ────────────────────────

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
