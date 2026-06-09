package com.library.model;

import static org.junit.jupiter.api.Assertions.*;

import com.library.entities.Livre;
import com.library.entities.Ouvrage;
import com.library.factory.UsineOuvrage;
import com.library.repository.OuvrageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class BibliothequePersistanceTest {

  @Autowired private OuvrageRepository repository;

  private final UsineOuvrage usineOuvrage = new UsineOuvrage();

  @Test
  void doitPersisterLivre() {
    Ouvrage nouveauLivre = usineOuvrage.creerOuvrage("Titre", "Science-Fiction", "Auteur X");

    Ouvrage saved = repository.save(nouveauLivre);

    assertNotNull(saved.getId());
    assertTrue(saved instanceof Livre);
  }

  @Test
  void doitRetrouverleLivreParId() {
    Ouvrage livre = usineOuvrage.creerOuvrage("1984", "Dystopie", "Orwell");
    Ouvrage saved = repository.save(livre);

    Ouvrage found = repository.findById(saved.getId()).orElseThrow();
    assertEquals("1984", found.getTitre());
  }

  @Test
  void doitListerTousLesOuvrages() {
    repository.save(usineOuvrage.creerOuvrage("Livre A", "Roman", "Auteur A"));
    repository.save(usineOuvrage.creerOuvrage("Livre B", "Polar", "Auteur B"));

    assertEquals(2, repository.findAll().size());
  }
}
