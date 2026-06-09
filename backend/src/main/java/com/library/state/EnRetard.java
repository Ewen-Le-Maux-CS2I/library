package com.library.state;

import com.library.entities.Emprunt;

public class EnRetard implements EtatEmprunt {

  @Override
  public void retourner(Emprunt e) {
    e.setEtatActuel(new Rendu());
  }

  @Override
  public void declarerPerte(Emprunt e) {
    e.setEtatActuel(new Perdu());
  }

  @Override
  public void signalerRetard(Emprunt e) {
    // Déjà en retard, aucune transition
  }

  @Override
  public String getNom() {
    return "En retard";
  }
}
