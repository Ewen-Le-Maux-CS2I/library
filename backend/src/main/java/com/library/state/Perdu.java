package com.library.state;

import com.library.entities.Emprunt;

public class Perdu implements EtatEmprunt {

  @Override
  public void retourner(Emprunt e) {
    throw new IllegalStateException("Un livre perdu ne peut pas être rendu directement.");
  }

  @Override
  public void declarerPerte(Emprunt e) {
    // Déjà perdu, rien à faire
  }

  @Override
  public void signalerRetard(Emprunt e) {
    throw new IllegalStateException("Un livre perdu ne peut pas être en retard.");
  }

  @Override
  public String getNom() {
    return "Perdu";
  }
}
