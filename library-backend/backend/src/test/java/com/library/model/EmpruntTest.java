package com.library.model;

import com.library.entities.Emprunt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmpruntTest {

    @Test
    void doitEtreEnCoursParDefaut() {
        Emprunt emprunt = new Emprunt();
        assertEquals("En cours", emprunt.getNomEtat());
    }

    @Test
    void doitPasserARenduApresRetour() {
        Emprunt emprunt = new Emprunt();
        emprunt.retourner();
        assertEquals("Rendu", emprunt.getNomEtat());
    }

    @Test
    void doitPasserAPerduApresDeclaration() {
        Emprunt emprunt = new Emprunt();
        emprunt.declarerPerte();
        assertEquals("Perdu", emprunt.getNomEtat());
    }

    @Test
    void doitPasserEnRetardDepuisEnCours() {
        Emprunt emprunt = new Emprunt();
        emprunt.signalerRetard();
        assertEquals("En retard", emprunt.getNomEtat());
    }

    @Test
    void rendreUnLivreEnRetardDoitPasserARendu() {
        Emprunt emprunt = new Emprunt();
        emprunt.signalerRetard();
        emprunt.retourner();
        assertEquals("Rendu", emprunt.getNomEtat());
    }
}
