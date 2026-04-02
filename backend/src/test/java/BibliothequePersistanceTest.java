@DataJpaTest 
class BibliothequePersistanceTest {

    @Autowired
    private ProduitRepository repository;

    private UsineOuvrage usineOuvrage = new UsineOuvrage();

    @Test

    void doitPersisterLivre() {

        // Creation d'un ouvrage via l'usine
        Ouvrage nouveauLivre = usineOuvrage.creerOuvrage("Titre", "Science-Fiction", "Auteur X");

        // Persistance de l'ouvrage
        Ouvrage saved = repository.save(nouveauLivre);

        // Verification de la persistance
        assertNotNull(saved.getId());
        assertTrue(saved instanceof Livre);
    }
}