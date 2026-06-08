package com.library.model;

import com.library.entities.Livre;
import com.library.entities.Ouvrage;
import com.library.factory.UsineOuvrage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsineProduitTest {

    private final UsineOuvrage usine = new UsineOuvrage();

    @Test
    void shouldCreateALivreWithCorrectAttributes() {
        String titre = "Le Seigneur des Anneaux";
        String auteur = "Tolkien";

        Ouvrage livre = usine.creerOuvrage(titre, "Heroic Fantasy", auteur);

        assertTrue(livre instanceof Livre);
        assertEquals(titre, livre.getTitre());
        assertEquals(auteur, ((Livre) livre).getAuteur());
    }

    @Test
    void shouldCreateAnOuvrageWithGenre() {
        Ouvrage ouvrage = usine.creerOuvrage("Dune", "Science-Fiction", "Frank Herbert");

        assertNotNull(ouvrage);
        assertEquals("Science-Fiction", ouvrage.getGenre());
    }
}
