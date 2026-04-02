@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void doitCreerLivre() {
        //Génération des infos du livre à créer
        var bookRequest = Map.of("titre", "Clean Code", "autheur", "Robert C. Martin");

        //Envoie de la requete POST
        var response = restTemplate.postForEntity("/api/books", bookRequest, Map.class);

        //Vérification de la réponse (200 attendue et id du livre créé)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().get("id"));
    }
}