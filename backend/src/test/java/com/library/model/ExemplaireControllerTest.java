package com.library.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExemplaireControllerTest {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void doitAjouterExemplaireAUnOuvrage() {
    Long livreId = creerLivre("Fondation", "Isaac Asimov");

    var resp =
        restTemplate.postForEntity(
            "/api/ouvrages/" + livreId + "/exemplaires", Map.of("cote", "SF-001"), Map.class);

    assertEquals(HttpStatus.CREATED, resp.getStatusCode());
    assertEquals("SF-001", resp.getBody().get("cote"));
    assertEquals(true, resp.getBody().get("disponible"));
  }

  @Test
  void doitListerExemplairesParOuvrage() {
    Long livreId = creerLivre("Dune", "Frank Herbert");
    restTemplate.postForEntity(
        "/api/ouvrages/" + livreId + "/exemplaires", Map.of("cote", "SF-002"), Map.class);
    restTemplate.postForEntity(
        "/api/ouvrages/" + livreId + "/exemplaires", Map.of("cote", "SF-003"), Map.class);

    var resp = restTemplate.getForEntity("/api/ouvrages/" + livreId + "/exemplaires", List.class);

    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertEquals(2, resp.getBody().size());
  }

  @Test
  void doitRetourner404SiOuvrageInexistant() {
    var resp =
        restTemplate.postForEntity(
            "/api/ouvrages/99999/exemplaires", Map.of("cote", "XX-000"), Map.class);
    assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
  }

  private Long creerLivre(String titre, String auteur) {
    var resp =
        restTemplate.postForEntity(
            "/api/livres", Map.of("titre", titre, "auteur", auteur, "genre", "Roman"), Map.class);
    return ((Number) resp.getBody().get("id")).longValue();
  }
}
