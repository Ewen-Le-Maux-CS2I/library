package com.library.controller;

import com.library.entities.Emprunt;
import com.library.service.Bibliotheque;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emprunts")
public class EmpruntController {

  private final Bibliotheque bibliotheque;

  public EmpruntController(Bibliotheque bibliotheque) {
    this.bibliotheque = bibliotheque;
  }

  /** POST /api/emprunts — crée un emprunt pour un exemplaire disponible */
  @PostMapping
  public ResponseEntity<Emprunt> emprunter(@RequestBody Map<String, Long> body) {
    Long exemplaireId = body.get("exemplaireId");
    Emprunt emprunt = bibliotheque.emprunter(exemplaireId);
    return ResponseEntity.status(HttpStatus.CREATED).body(emprunt);
  }

  /** PATCH /api/emprunts/{id}/retourner — retourne un emprunt */
  @PatchMapping("/{id}/retourner")
  public Emprunt retourner(@PathVariable Long id) {
    return bibliotheque.retourner(id);
  }

  /** PATCH /api/emprunts/{id}/retard — signale un retard */
  @PatchMapping("/{id}/retard")
  public Emprunt signalerRetard(@PathVariable Long id) {
    return bibliotheque.signalerRetard(id);
  }

  /** PATCH /api/emprunts/{id}/perte — déclare un emprunt perdu */
  @PatchMapping("/{id}/perte")
  public Emprunt declarerPerte(@PathVariable Long id) {
    return bibliotheque.declarerPerte(id);
  }
}
