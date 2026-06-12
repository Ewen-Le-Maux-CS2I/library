
## Vue d'ensemble

Backend Spring Boot pour la gestion d'une bibliothèque. Il expose une API REST et applique deux design patterns : **Abstract Factory** pour la création des ouvrages, et **State** pour le cycle de vie des emprunts.

**Stack technique**
- Java 21
- Spring Boot 3.3
- Spring Data JPA + H2 (base en mémoire)
- Spring MVC (API REST)
- JUnit 5 (TDD)

---

## Architecture du projet

```
.
├── docker-compose.yml
└── backend/
    ├── Dockerfile
    ├── pom.xml
    └── src/
        ├── main/java/com/library/
        │   ├── LibraryApplication.java
        │   ├── controller/
        │   │   ├── LivreController.java        ← GET/POST/DELETE /api/livres
        │   │   ├── ExemplaireController.java   ← GET/POST /api/ouvrages/{id}/exemplaires
        │   │   └── EmpruntController.java      ← POST/PATCH /api/emprunts
        │   ├── exception/
        │   │   └── GlobalExceptionHandler.java ← @ControllerAdvice (404/409/500)
        │   ├── service/
        │   │   └── Bibliotheque.java
        │   ├── entities/
        │   │   ├── Ouvrage.java (abstraite)
        │   │   ├── Livre.java
        │   │   ├── Revue.java
        │   │   ├── Exemplaire.java
        │   │   └── Emprunt.java
        │   ├── factory/
        │   │   ├── UsineAbstraite.java
        │   │   └── UsineOuvrage.java
        │   ├── state/
        │   │   ├── EtatEmprunt.java (interface)
        │   │   ├── EnCours.java
        │   │   ├── EnRetard.java
        │   │   ├── Rendu.java
        │   │   └── Perdu.java
        │   └── repository/
        │       ├── OuvrageRepository.java
        │       ├── ExemplaireRepository.java
        │       └── EmpruntRepository.java
        ├── main/resources/
        │   └── application.properties          ← PostgreSQL (variables d'env)
        └── test/
            ├── java/com/library/model/
            │   ├── UsineProduitTest.java
            │   ├── EmpruntTest.java
            │   ├── EtatEmpruntPostLoadTest.java ← test reconstitution @PostLoad
            │   ├── BibliothequePersistanceTest.java
            │   ├── LivreControllerTest.java
            │   ├── LivreRechercheTest.java
            │   ├── ExemplaireControllerTest.java
            │   ├── EmpruntControllerTest.java
            │   └── DemoApplicationTests.java
            └── resources/
                └── application-test.properties ← H2 en mémoire pour les tests
```

---

## Où placer le GlobalExceptionHandler

**Fichier :** `src/main/java/com/library/exception/GlobalExceptionHandler.java`

C'est la convention Spring : un sous-package `exception/` (ou `advice/`) dédié,
séparé des controllers. L'annotation `@ControllerAdvice` le rend visible à tout
le contexte Spring sans configuration supplémentaire.

| Exception levée par | Mappée en |
|---|---|
| `IllegalArgumentException` | `404 Not Found` |
| `IllegalStateException` | `409 Conflict` |
| `Exception` (catch-all) | `500 Internal Server Error` |

Chaque réponse d'erreur a la structure :
```json
{
  "timestamp": "2026-06-08T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Ouvrage introuvable : 99"
}
```

---

## API REST complète

### Livres – `/api/livres`

| Méthode | URL | Description | Code |
|---|---|---|---|
| `GET` | `/api/livres` | Liste tous les ouvrages | 200 |
| `GET` | `/api/livres?titre=dune` | Recherche insensible à la casse | 200 |
| `GET` | `/api/livres?genre=SF` | Filtre par genre exact | 200 |
| `GET` | `/api/livres/{id}` | Un ouvrage par id | 200 / 404 |
| `POST` | `/api/livres` | Crée un livre | 201 |
| `DELETE` | `/api/livres/{id}` | Supprime un ouvrage | 204 |

**POST /api/livres**
```json
{ "titre": "Dune", "auteur": "Frank Herbert", "genre": "SF" }
```

---

### Exemplaires – `/api/ouvrages/{id}/exemplaires`

| Méthode | URL | Description | Code |
|---|---|---|---|
| `GET` | `/api/ouvrages/{id}/exemplaires` | Liste les exemplaires d'un ouvrage | 200 |
| `POST` | `/api/ouvrages/{id}/exemplaires` | Ajoute un exemplaire | 201 / 404 |

**POST /api/ouvrages/1/exemplaires**
```json
{ "cote": "SF-001" }
```

---

### Emprunts – `/api/emprunts`

| Méthode | URL | Description | Code |
|---|---|---|---|
| `POST` | `/api/emprunts` | Crée un emprunt | 201 / 409 |
| `PATCH` | `/api/emprunts/{id}/retourner` | Retourne un emprunt | 200 |
| `PATCH` | `/api/emprunts/{id}/retard` | Signale un retard | 200 |
| `PATCH` | `/api/emprunts/{id}/perte` | Déclare une perte | 200 |

**POST /api/emprunts**
```json
{ "exemplaireId": 1 }
```

---

## Machine d'états des emprunts

```
            signalerRetard()
 EnCours ──────────────────► EnRetard
    │                             │
    │ retourner()                 │ retourner()
    ▼                             ▼
  Rendu ◄──────────────────── Rendu

 EnCours  ─── declarerPerte() ──► Perdu
 EnRetard ─── declarerPerte() ──► Perdu
```

Les transitions invalides (ex : `retourner()` sur un emprunt `Perdu`) lèvent
une `IllegalStateException` → retournée en `409 Conflict` par le handler.

**Reconstitution après rechargement JPA :** le champ `nomEtat` est persisté en base.
`@PostLoad` reconstruit l'objet `EtatEmprunt` correct via un `switch` sur ce nom.

---

## Design patterns

### Abstract Factory

`UsineAbstraite` définit le contrat. `UsineOuvrage` est l'implémentation Spring.

```java
Ouvrage livre     = usineOuvrage.creerOuvrage("Dune", "SF", "Herbert");
Exemplaire ex     = usineOuvrage.creerExemplaire(livre, "SF-001");
```

### State pattern

Chaque état (`EnCours`, `EnRetard`, `Rendu`, `Perdu`) implémente `EtatEmprunt`
et gère ses propres transitions. `Emprunt` délègue et met à jour `nomEtat`.

---

## Tests

Lancer tous les tests (H2 en mémoire, pas besoin de Docker) :
```bash
cd backend && mvn test
```

| Fichier de test | Type | Ce qu'il couvre |
|---|---|---|
| `UsineProduitTest` | Unitaire | Création Livre via UsineOuvrage |
| `EmpruntTest` | Unitaire | Toutes les transitions d'état |
| `EtatEmpruntPostLoadTest` | `@DataJpaTest` | Reconstitution état après @PostLoad |
| `BibliothequePersistanceTest` | `@DataJpaTest` | Persistance JPA |
| `LivreControllerTest` | `@SpringBootTest` | CRUD livres HTTP |
| `LivreRechercheTest` | `@SpringBootTest` | Recherche par titre/genre |
| `ExemplaireControllerTest` | `@SpringBootTest` | CRUD exemplaires HTTP + 404 |
| `EmpruntControllerTest` | `@SpringBootTest` | Emprunt, retour, retard, perte, 409, 404 |
| `DemoApplicationTests` | `@SpringBootTest` | Démarrage contexte Spring |

Les tests utilisent le profil `test` (`@ActiveProfiles("test")`) qui active H2
via `src/test/resources/application-test.properties`.

---

## Configuration

### Production (Docker)

Les variables d'environnement sont injectées par Docker Compose :

| Variable | Valeur par défaut | Description |
|---|---|---|
| `DB_HOST` | `localhost` | Hôte PostgreSQL |
| `DB_PORT` | `5432` | Port PostgreSQL |
| `DB_NAME` | `librarydb` | Nom de la base |
| `DB_USER` | `library` | Utilisateur |
| `DB_PASSWORD` | `library` | Mot de passe |

### Tests (H2)

`src/test/resources/application-test.properties` — activé automatiquement
via `@ActiveProfiles("test")` sur chaque classe de test.

