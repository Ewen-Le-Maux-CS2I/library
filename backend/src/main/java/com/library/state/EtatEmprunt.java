package com.library.state;

import com.library.entities.Emprunt;

public interface EtatEmprunt {
  void retourner(Emprunt e);

  void declarerPerte(Emprunt e);

  void signalerRetard(Emprunt e);

  String getNom();
}
