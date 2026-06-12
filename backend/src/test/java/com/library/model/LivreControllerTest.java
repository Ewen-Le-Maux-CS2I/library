package com.library.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LivreControllerTest {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void doitCreerLivre() {
    var bookRequest =
        Map.of(
            "titre", "Clean Code",
            "auteur", "Robert C. Martin",
            "genre", "Informatique");

    var response = restTemplate.postForEntity("/api/livres", bookRequest, Map.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody().get("id"));
  }

  @Test
  void doitListerLesLivres() {
    var response = restTemplate.getForEntity("/api/livres", Object.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void doitRetournerNotFoundSiLivreInexistant() {
    var response = restTemplate.getForEntity("/api/livres/99999", Map.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
