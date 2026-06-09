package com.library.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LivreRechercheTest {

  @Autowired private TestRestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    restTemplate.postForEntity(
        "/api/livres", Map.of("titre", "Dune", "auteur", "Herbert", "genre", "SF"), Map.class);
    restTemplate.postForEntity(
        "/api/livres",
        Map.of("titre", "Dune Messiah", "auteur", "Herbert", "genre", "SF"),
        Map.class);
    restTemplate.postForEntity(
        "/api/livres",
        Map.of("titre", "Clean Code", "auteur", "Martin", "genre", "Informatique"),
        Map.class);
  }

  @Test
  void doitRechercherParTitre() {
    var resp = restTemplate.getForEntity("/api/livres?titre=dune", List.class);
    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertEquals(2, resp.getBody().size());
  }

  @Test
  void doitRechercherParGenre() {
    var resp = restTemplate.getForEntity("/api/livres?genre=SF", List.class);
    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertEquals(2, resp.getBody().size());
  }

  @Test
  void doitRetournerTousLesLivresSansFiltres() {
    var resp = restTemplate.getForEntity("/api/livres", List.class);
    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertTrue(resp.getBody().size() >= 3);
  }
}
