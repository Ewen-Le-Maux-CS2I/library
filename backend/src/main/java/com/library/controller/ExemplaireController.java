package com.library.controller;

import com.library.entities.Exemplaire;
import com.library.service.Bibliotheque;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ouvrages/{ouvrageId}/exemplaires")
public class ExemplaireController {

  private final Bibliotheque bibliotheque;

  public ExemplaireController(Bibliotheque bibliotheque) {
    this.bibliotheque = bibliotheque;
  }

  /** GET /api/ouvrages/{ouvrageId}/exemplaires — liste tous les exemplaires d'un ouvrage */
  @GetMapping
  public List<Exemplaire> listerExemplaires(@PathVariable Long ouvrageId) {
    return bibliotheque.listerExemplaires(ouvrageId);
  }

  /** POST /api/ouvrages/{ouvrageId}/exemplaires — ajoute un exemplaire à un ouvrage */
  @PostMapping
  public ResponseEntity<Exemplaire> ajouterExemplaire(
      @PathVariable Long ouvrageId, @RequestBody Map<String, String> body) {
    String cote = body.getOrDefault("cote", "");
    Exemplaire ex = bibliotheque.ajouterExemplaire(ouvrageId, cote);
    return ResponseEntity.status(HttpStatus.CREATED).body(ex);
  }
}
