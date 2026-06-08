---

---

# Documentation des Tests Unitaires

## Backend

| Fichier | Type | Ce qu'il teste |
|---|---|---|
| `UsineProduitTest` | Unitaire | Création d'un `Livre` via `UsineOuvrage` |
| `EmpruntTest` | Unitaire | Machine d'états (toutes les transitions) |
| `BibliothequePersistanceTest` | `@DataJpaTest` | Persistance JPA avec H2 |
| `LivreControllerTest` | `@SpringBootTest` | Endpoints HTTP POST / GET / 404 |
| `DemoApplicationTests` | `@SpringBootTest` | Démarrage du contexte Spring |

Lancer les tests :
```bash
mvn clean test
```

  