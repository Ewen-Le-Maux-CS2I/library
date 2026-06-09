package com.library.model;

import com.library.entities.Emprunt;
import com.library.entities.Exemplaire;
import com.library.entities.Ouvrage;
import com.library.factory.UsineOuvrage;
import com.library.repository.EmpruntRepository;
import com.library.repository.ExemplaireRepository;
import com.library.repository.OuvrageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Vérifie que @PostLoad reconstitue le bon état après rechargement depuis la base.
 */
@DataJpaTest
@ActiveProfiles("test")
@Import(UsineOuvrage.class)
class EtatEmpruntPostLoadTest {

    @Autowired private OuvrageRepository ouvrageRepo;
    @Autowired private ExemplaireRepository exemplaireRepo;
    @Autowired private EmpruntRepository empruntRepo;
    @Autowired private UsineOuvrage usineOuvrage;

    @Test
    void doitReconstituerEtatRenduApresRechargement() {
        Emprunt emprunt = creerEtSauvegarderEmprunt();
        emprunt.retourner();
        empruntRepo.save(emprunt);
        empruntRepo.flush();

        Emprunt recharge = empruntRepo.findById(emprunt.getId()).orElseThrow();
        assertEquals("Rendu", recharge.getNomEtat());
        // Vérifie que l'état transient est bien Rendu (pas EnCours par défaut)
        assertThrows(IllegalStateException.class, recharge::declarerPerte);
    }

    @Test
    void doitReconstituerEtatEnRetardApresRechargement() {
        Emprunt emprunt = creerEtSauvegarderEmprunt();
        emprunt.signalerRetard();
        empruntRepo.save(emprunt);
        empruntRepo.flush();

        Emprunt recharge = empruntRepo.findById(emprunt.getId()).orElseThrow();
        assertEquals("En retard", recharge.getNomEtat());
        // Depuis EnRetard on peut encore retourner
        assertDoesNotThrow(recharge::retourner);
    }

    @Test
    void doitReconstituerEtatPerduApresRechargement() {
        Emprunt emprunt = creerEtSauvegarderEmprunt();
        emprunt.declarerPerte();
        empruntRepo.save(emprunt);
        empruntRepo.flush();

        Emprunt recharge = empruntRepo.findById(emprunt.getId()).orElseThrow();
        assertEquals("Perdu", recharge.getNomEtat());
        assertThrows(IllegalStateException.class, recharge::retourner);
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Emprunt creerEtSauvegarderEmprunt() {
        Ouvrage ouvrage = ouvrageRepo.save(
                usineOuvrage.creerOuvrage("Test PostLoad", "Test", "Auteur"));
        Exemplaire ex = exemplaireRepo.save(
                usineOuvrage.creerExemplaire(ouvrage, "T-001"));
        Emprunt emprunt = new Emprunt();
        emprunt.setExemplaire(ex);
        return empruntRepo.save(emprunt);
    }
}
