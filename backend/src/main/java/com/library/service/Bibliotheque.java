package com.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.entities.Emprunt;
import com.library.entities.Exemplaire;
import com.library.entities.Ouvrage;
import com.library.factory.UsineOuvrage;
import com.library.repository.EmpruntRepository;
import com.library.repository.ExemplaireRepository;
import com.library.repository.OuvrageRepository;

@Service
@Transactional
public class Bibliotheque {

  private final OuvrageRepository ouvrageRepository;
  private final ExemplaireRepository exemplaireRepository;
  private final EmpruntRepository empruntRepository;
  private final UsineOuvrage usineOuvrage;

  public Bibliotheque(
      OuvrageRepository ouvrageRepository,
      ExemplaireRepository exemplaireRepository,
      EmpruntRepository empruntRepository,
      UsineOuvrage usineOuvrage) {
    this.ouvrageRepository = ouvrageRepository;
    this.exemplaireRepository = exemplaireRepository;
    this.empruntRepository = empruntRepository;
    this.usineOuvrage = usineOuvrage;
  }

  // ── Ouvrages ─────────────────────────────────────────────────────────────

  public Ouvrage ajouterLivre(String titre, String genre, String auteur) {
    Ouvrage livre = usineOuvrage.creerOuvrage(titre, genre, auteur);
    return ouvrageRepository.save(livre);
  }

  @Transactional(readOnly = true)
  public List<Ouvrage> listerOuvrages() {
    return ouvrageRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Ouvrage> trouverOuvrage(Long id) {
    return ouvrageRepository.findById(id);
  }

  public void supprimerOuvrage(Long id) {
    ouvrageRepository.deleteById(id);
  }

  // ── Exemplaires ───────────────────────────────────────────────────────────

  public Exemplaire ajouterExemplaire(Long ouvrageId, String cote) {
    Ouvrage ouvrage =
        ouvrageRepository
            .findById(ouvrageId)
            .orElseThrow(() -> new IllegalArgumentException("Ouvrage introuvable : " + ouvrageId));
    Exemplaire ex = usineOuvrage.creerExemplaire(ouvrage, cote);
    return exemplaireRepository.save(ex);
  }

  // ── Emprunts ──────────────────────────────────────────────────────────────

  public Emprunt emprunter(Long exemplaireId) {
    Exemplaire ex =
        exemplaireRepository
            .findById(exemplaireId)
            .orElseThrow(
                () -> new IllegalArgumentException("Exemplaire introuvable : " + exemplaireId));
    if (!ex.isDisponible()) {
      throw new IllegalStateException("Cet exemplaire n'est pas disponible.");
    }
    ex.setDisponible(false);
    exemplaireRepository.save(ex);

    Emprunt emprunt = new Emprunt();
    emprunt.setExemplaire(ex);
    return empruntRepository.save(emprunt);
  }

  public Emprunt retourner(Long empruntId) {
    Emprunt emprunt =
        empruntRepository
            .findById(empruntId)
            .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable : " + empruntId));
    emprunt.retourner();
    emprunt.getExemplaire().setDisponible(true);
    return empruntRepository.save(emprunt);
  }

  public Emprunt declarerPerte(Long empruntId) {
    Emprunt emprunt =
        empruntRepository
            .findById(empruntId)
            .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable : " + empruntId));
    emprunt.declarerPerte();
    return empruntRepository.save(emprunt);
  }

    public Emprunt signalerRetard(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ouvrage> rechercherParTitre(String titre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Ouvrage> rechercherParGenre(String genre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
