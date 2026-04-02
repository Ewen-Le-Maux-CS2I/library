package com.library.state;

import com.library.model.Emprunt;

public class EnCours implements EtatEmprunt {
    @Override
    public void retourner(Emprunt e) {
        // Logique de retour
        System.out.println("Livre rendu à temps.");
    }

    @Override
    public void declarerPerte(Emprunt e) {
        // e.setEtat(new Perdu()); // On verra la transition après
    }

    @Override
    public String getNom() {
        return "En cours";
    }
}