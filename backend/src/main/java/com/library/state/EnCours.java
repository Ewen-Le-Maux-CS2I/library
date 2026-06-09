package com.library.state;

import com.library.entities.Emprunt;

public class EnCours implements EtatEmprunt {

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
    e.setEtatActuel(new EnRetard());
  }

  @Override
  public String getNom() {
    return "En cours";
  }
}
