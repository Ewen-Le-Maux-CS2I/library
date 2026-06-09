package com.library.controller;

import com.library.entities.Ouvrage;
import com.library.service.Bibliotheque;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

  private final Bibliotheque bibliotheque;

  public LivreController(Bibliotheque bibliotheque) {
    this.bibliotheque = bibliotheque;
  }

  /**
   * GET /api/livres — liste tous les ouvrages GET /api/livres?titre=dune — recherche par titre
   * (insensible à la casse) GET /api/livres?genre=Roman — recherche par genre exact
   */
  @GetMapping
  public List<Ouvrage> listerLivres(
      @RequestParam(required = false) String titre, @RequestParam(required = false) String genre) {
    if (titre != null) {
      return bibliotheque.rechercherParTitre(titre);
    }
    if (genre != null) {
      return bibliotheque.rechercherParGenre(genre);
    }
    return bibliotheque.listerOuvrages();
  }

  /** GET /api/livres/{id} */
  @GetMapping("/{id}")
  public Ouvrage getLivre(@PathVariable Long id) {
    return bibliotheque
        .trouverOuvrage(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livre introuvable"));
  }

  /** POST /api/livres */
  @PostMapping
  public ResponseEntity<Ouvrage> creerLivre(@RequestBody Map<String, String> body) {
    String titre = body.get("titre");
    String auteur = body.get("auteur");
    String genre = body.getOrDefault("genre", "Non classé");
    Ouvrage livre = bibliotheque.ajouterLivre(titre, genre, auteur);
    return ResponseEntity.status(HttpStatus.CREATED).body(livre);
  }

  /** DELETE /api/livres/{id} */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void supprimerLivre(@PathVariable Long id) {
    bibliotheque.supprimerOuvrage(id);
  }
}
