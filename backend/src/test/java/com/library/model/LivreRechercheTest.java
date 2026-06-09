package com.library.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    var resp =
        restTemplate.exchange(
            "/api/livres?titre=dune",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {});

    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertEquals(2, resp.getBody().size());
  }

  @Test
  void doitRechercherParGenre() {
    var resp =
        restTemplate.exchange(
            "/api/livres?genre=SF",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {});

    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertEquals(2, resp.getBody().size());
  }

  @Test
  void doitRetournerTousLesLivresSansFiltres() {
    var resp =
        restTemplate.exchange(
            "/api/livres",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {});

    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertTrue(resp.getBody().size() >= 3);
  }
}
