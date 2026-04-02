package com.library.State;

import com.library.model.Emprunt;

public interface EtatEmprunt {
    void retourner(Emprunt e);
    void declarerPerte(Emprunt e);
    String getNom();
}

