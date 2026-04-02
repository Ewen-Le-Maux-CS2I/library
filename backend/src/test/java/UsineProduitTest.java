class UsineProduitTest {

    private final UsineOuvrage usine = new UsineOuvrage();

    @Test
    void shouldCreateALivreWithCorrectAttributes() {
        // Données de test
        String titre = "Le Seigneur des Anneaux";
        String auteur = "Tolkien";
        
        // 
        Ouvrage livre = usine.creerOuvrage(titre, "Heroic Fantasy", auteur);

        // THEN
        assertTrue(livre instanceof Livre);
        assertEquals(titre, livre.getTitre());
        assertEquals(auteur, ((Livre) livre).getAuteur());
    }
}