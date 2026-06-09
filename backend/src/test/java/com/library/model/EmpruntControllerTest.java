package com.library.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EmpruntControllerTest {

  @Autowired private TestRestTemplate restTemplate;

  /** Crée un livre + exemplaire, emprunte — vérifie 201 et état "En cours" */
  @Test
  void doitCreerUnEmprunt() {
    Long exId = creerExemplaire("Test-Emprunt", "A-001");

    var resp = restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);

    assertEquals(HttpStatus.CREATED, resp.getStatusCode());
    assertEquals("En cours", resp.getBody().get("nomEtat"));
  }

  /** Retourner un emprunt → état "Rendu" */
  @Test
  void doitRetournerUnEmprunt() {
    Long empruntId = creerEmpruntComplet("Test-Retour", "B-001");

    Map<?, ?> resp = patch("/api/emprunts/" + empruntId + "/retourner");
    assertEquals("Rendu", resp.get("nomEtat"));
  }

  /** Signaler un retard → état "En retard" */
  @Test
  void doitSignalerRetard() {
    Long empruntId = creerEmpruntComplet("Test-Retard", "C-001");

    Map<?, ?> resp = patch("/api/emprunts/" + empruntId + "/retard");
    assertEquals("En retard", resp.get("nomEtat"));
  }

  /** Déclarer une perte → état "Perdu" */
  @Test
  void doitDeclarerPerte() {
    Long empruntId = creerEmpruntComplet("Test-Perte", "D-001");

    Map<?, ?> resp = patch("/api/emprunts/" + empruntId + "/perte");
    assertEquals("Perdu", resp.get("nomEtat"));
  }

  /** Emprunter un exemplaire déjà emprunté → 409 Conflict */
  @Test
  void doitRetourner409SiExemplaireIndisponible() {
    Long exId = creerExemplaire("Test-Conflit", "E-001");

    restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);
    var resp = restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);

    assertEquals(HttpStatus.CONFLICT, resp.getStatusCode());
  }

  /** Emprunt inexistant → réponse 404 dans le body */
  @Test
  void doitRetourner404SiEmpruntInexistant() {
    Map<?, ?> resp = patch("/api/emprunts/99999/retourner");
    assertEquals(404, resp.get("status"));
  }

  // ── Helpers ───────────────────────────────────────────────────────────────

  /**
   * Envoie un PATCH via HttpComponentsClientHttpRequestFactory (le client HTTP par défaut de Spring
   * ne supporte pas PATCH).
   */
  @SuppressWarnings("unchecked")
  private Map<?, ?> patch(String url) {
    TestRestTemplate patchTemplate =
        new TestRestTemplate(
            new RestTemplateBuilder()
                .requestFactory(
                    () -> new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault())));
    // Récupère le port du serveur de test
    String baseUrl = restTemplate.getRootUri();
    return patchTemplate.patchForObject(baseUrl + url, null, Map.class);
  }

  private Long creerExemplaire(String titre, String cote) {
    var livre =
        restTemplate.postForEntity(
            "/api/livres", Map.of("titre", titre, "auteur", "Auteur", "genre", "Test"), Map.class);
    Long livreId = ((Number) livre.getBody().get("id")).longValue();

    var ex =
        restTemplate.postForEntity(
            "/api/ouvrages/" + livreId + "/exemplaires", Map.of("cote", cote), Map.class);
    assertNotNull(ex.getBody(), "La création d'exemplaire a échoué pour l'ouvrage " + livreId);
    return ((Number) ex.getBody().get("id")).longValue();
  }

  private Long creerEmpruntComplet(String titre, String cote) {
    Long exId = creerExemplaire(titre, cote);
    var emprunt =
        restTemplate.postForEntity("/api/emprunts", Map.of("exemplaireId", exId), Map.class);
    return ((Number) emprunt.getBody().get("id")).longValue();
  }
}
