package com.library.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class EmpruntTest {

    @Test
    void doitEtreEnCours() {
        Emprunt emprunt = new Emprunt();
        
        //
        assertEquals("En cours", emprunt.getNomEtat());
    }
}