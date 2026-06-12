package com.library.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.library.entities.Livre;
import com.library.repository.OuvrageRepository;

@Configuration
@Profile("!test")
@Component
public class DataInitializer implements CommandLineRunner {

  private final OuvrageRepository ouvrageRepository;

  public DataInitializer(OuvrageRepository ouvrageRepository) {
    this.ouvrageRepository = ouvrageRepository;
  }

  @Override
  public void run(String... args) {
    if (ouvrageRepository.count() > 0) {
      return;
    }

    ouvrageRepository.saveAll(
        List.of(
            createLivre(
                "Le Nom de la Rose", "Polar historique", "Umberto Eco", 9782264027757L, 1980, 12),
            createLivre("Dune", "Science-fiction", "Frank Herbert", 9782266151580L, 1965, 15),
            createLivre(
                "Clean Code", "Informatique", "Robert C. Martin", 9780132350884L, 2008, 20),
            createLivre(
                "Le Petit Prince", "Conte", "Antoine de Saint-Exupéry", 9782070612758L, 1943, 8),
            createLivre(
                "L'Appel de Cthulhu", "Horreur", "H. P. Lovecraft", 9782253002029L, 1928, 10)));
  }

  private Livre createLivre(
      String titre, String genre, String auteur, Long isbn, Integer datePublication, Integer caution) {
    Livre livre = new Livre();
    livre.setTitre(titre);
    livre.setGenre(genre);
    livre.setAuteur(auteur);
    livre.setIsbn(isbn);
    livre.setDatePublication(datePublication);
    livre.setCaution(caution);
    return livre;
  }
}