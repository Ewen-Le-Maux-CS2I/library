package com.library.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EmpruntControllerTest {

  @Autowired private TestRestTemplate restTemplate;

  /** Crée un livre + exemplaire, puis emprunte — vérifie 201 et état "En cours" */
  @Test
  void doitCreerUnEmprunt() {
    // 1. Créer un livre
    var livre =
        restTemplate.postForEntity(
            "/api/livres", Map.of("titre", "Test", "auteur", "A", "genre", "Roman"), Map.class);
    Long livreId = ((Number) livre.getBody().get("id")).longValue();

    // 2. Ajouter un exemplaire
    var exResp =
        restTemplate.postForEntity(
            "/api/ouvrages/" + livreId + "/exemplaires", Map.of("cote", "T-001"), Map.class);
    assertEquals(HttpStatus.CREATED, exResp.getStatusCode());
    Long exId = ((Number) exResp.getBody().get("id")).longValue();

    // 3. Emprunter
    var empruntResp =
        restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);
    assertEquals(HttpStatus.CREATED, empruntResp.getStatusCode());
    assertEquals("En cours", empruntResp.getBody().get("nomEtat"));
  }

  /** Retourner un emprunt → état "Rendu" */
  @Test
  void doitRetournerUnEmprunt() {
    Long empruntId = creerEmpruntComplet("Retour-Test", "B-001");

    var resp =
        restTemplate.patchForObject("/api/emprunts/" + empruntId + "/retourner", null, Map.class);
    assertEquals("Rendu", resp.get("nomEtat"));
  }

  /** Signaler un retard → état "En retard" */
  @Test
  void doitSignalerRetard() {
    Long empruntId = creerEmpruntComplet("Retard-Test", "C-001");

    var resp =
        restTemplate.patchForObject("/api/emprunts/" + empruntId + "/retard", null, Map.class);
    assertEquals("En retard", resp.get("nomEtat"));
  }

  /** Déclarer une perte → état "Perdu" */
  @Test
  void doitDeclarerPerte() {
    Long empruntId = creerEmpruntComplet("Perte-Test", "D-001");

    var resp =
        restTemplate.patchForObject("/api/emprunts/" + empruntId + "/perte", null, Map.class);
    assertEquals("Perdu", resp.get("nomEtat"));
  }

  /** Emprunter un exemplaire déjà emprunté → 409 Conflict */
  @Test
  void doitRetourner409SiExemplaireIndisponible() {
    Long exId = creerExemplaire("Conflit-Test", "E-001");

    // Premier emprunt → OK
    restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);

    // Deuxième emprunt → 409
    var resp = restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);
    assertEquals(HttpStatus.CONFLICT, resp.getStatusCode());
  }

  /** Emprunt inexistant → 404 */
  @Test
  void doitRetourner404SiEmpruntInexistant() {
    var resp = restTemplate.patchForObject("/api/emprunts/99999/retourner", null, Map.class);
    assertEquals(404, resp.get("status"));
  }

  // ── Helpers ───────────────────────────────────────────────────────────────

  private Long creerExemplaire(String titre, String cote) {
    var livre =
        restTemplate.postForEntity(
            "/api/livres", Map.of("titre", titre, "auteur", "Auteur", "genre", "Test"), Map.class);
    Long livreId = ((Number) livre.getBody().get("id")).longValue();
    var ex =
        restTemplate.postForEntity(
            "/api/ouvrages/" + livreId + "/exemplaires", Map.of("cote", cote), Map.class);
    return ((Number) ex.getBody().get("id")).longValue();
  }

  private Long creerEmpruntComplet(String titre, String cote) {
    Long exId = creerExemplaire(titre, cote);
    var emprunt =
        restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);
    return ((Number) emprunt.getBody().get("id")).longValue();
  }
}
