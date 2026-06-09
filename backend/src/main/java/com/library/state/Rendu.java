package com.library.state;

import com.library.entities.Emprunt;

public class Rendu implements EtatEmprunt {

  @Override
  public void retourner(Emprunt e) {
    // Déjà rendu, rien à faire
  }

  @Override
  public void declarerPerte(Emprunt e) {
    throw new IllegalStateException("Un livre rendu ne peut pas être déclaré perdu.");
  }

  @Override
  public void signalerRetard(Emprunt e) {
    throw new IllegalStateException("Un livre rendu ne peut pas être en retard.");
  }

  @Override
  public String getNom() {
    return "Rendu";
  }
}
