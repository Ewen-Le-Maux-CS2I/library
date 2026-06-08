package com.library.controller;

import com.library.entities.Ouvrage;
import com.library.service.Bibliotheque;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

  private final Bibliotheque bibliotheque;

  public LivreController(Bibliotheque bibliotheque) {
    this.bibliotheque = bibliotheque;
  }

  @GetMapping
  public List<Ouvrage> listerLivres() {
    return bibliotheque.listerOuvrages();
  }

  @GetMapping("/{id}")
  public Ouvrage getLivre(@PathVariable Long id) {
    return bibliotheque
        .trouverOuvrage(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livre introuvable"));
  }

  @PostMapping
  public ResponseEntity<Ouvrage> creerLivre(@RequestBody Map<String, String> body) {
    String titre = body.get("titre");
    String auteur = body.get("auteur");
    String genre = body.getOrDefault("genre", "Non classé");
    Ouvrage livre = bibliotheque.ajouterLivre(titre, genre, auteur);
    return ResponseEntity.status(HttpStatus.CREATED).body(livre);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void supprimerLivre(@PathVariable Long id) {
    bibliotheque.supprimerOuvrage(id);
  }
}
