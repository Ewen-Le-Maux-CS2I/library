# Backend

## Vue d'ensemble

Backend Spring Boot pour la gestion d'une bibliothèque. Il expose une API REST et applique deux design patterns : **Abstract Factory** pour la création des ouvrages, et **State** pour le cycle de vie des emprunts.

**Stack technique**
- Java 21
- Spring Boot 3.3
- Spring Data JPA + H2 (base en mémoire)
- Spring MVC (API REST)
- JUnit 5 (TDD)

---

## Architecture

```
com.library
├── LibraryApplication.java       ← Point d'entrée Spring Boot
├── controller/
│   ├── LivreController.java      ← REST /api/livres
│   └── EmpruntController.java    ← REST /api/emprunts
├── service/
│   └── Bibliotheque.java         ← Logique métier
├── entities/
│   ├── Ouvrage.java              ← Classe abstraite (racine JPA)
│   ├── Livre.java                ← Sous-type d'Ouvrage
│   ├── Revue.java                ← Sous-type d'Ouvrage
│   ├── Exemplaire.java           ← Exemplaire physique d'un ouvrage
│   └── Emprunt.java              ← Emprunt avec machine d'états
├── factory/
│   ├── UsineAbstraite.java       ← Abstract Factory (contrat)
│   └── UsineOuvrage.java         ← Implémentation concrète
├── state/
│   ├── EtatEmprunt.java          ← Interface State
│   ├── EnCours.java
│   ├── EnRetard.java
│   ├── Rendu.java
│   └── Perdu.java
└── repository/
    ├── OuvrageRepository.java
    ├── ExemplaireRepository.java
    └── EmpruntRepository.java
```

---

## Modèle de données

### Hiérarchie des ouvrages

`Ouvrage` est la classe abstraite

| Champ | Type | Description |
|---|---|---|
| `id` | `Long` | Identifiant auto-généré |
| `titre` | `String` | Titre de l'ouvrage |
| `genre` | `String` | Genre littéraire |
| `caution` | `Integer` | Montant de la caution |

**Livre** étend `Ouvrage` et ajoute :

| Champ | Type |
|---|---|
| `auteur` | `String` |
| `isbn` | `Long` |
| `datePublication` | `Integer` |

**Revue** étend `Ouvrage` et ajoute :

| Champ | Type |
|---|---|
| `numero` | `String` |
| `dateParution` | `LocalDate` |

### Exemplaire

Représente un exemplaire physique d'un ouvrage en rayon.

| Champ | Type | Description |
|---|---|---|
| `id` | `Long` | Identifiant auto-généré |
| `cote` | `String` | Cote de classement |
| `disponible` | `boolean` | `true` si empruntable |
| `ouvrage` | `Ouvrage` | Référence à l'ouvrage (ManyToOne) |

### Emprunt

| Champ | Type | Description |
|---|---|---|
| `id` | `Long` | Identifiant auto-généré |
| `dateEmprunt` | `LocalDate` | Date de création (défaut : aujourd'hui) |
| `dateRetourPrevue` | `LocalDate` | Date de retour prévue |
| `exemplaire` | `Exemplaire` | Exemplaire emprunté (ManyToOne) |
| `nomEtat` | `String` | État courant persisté (`"En cours"` par défaut) |

> L'état courant (`etatActuel`) est `@Transient` : il n'est pas persisté, mais reconstruit à chaque chargement JPA via `@PostLoad`.

---

## Design patterns

### Abstract Factory

`UsineAbstraite` définit le contrat de création. `UsineOuvrage` est l'implémentation concrète enregistrée comme bean Spring (`@Component`).

```java
// Créer un livre
Ouvrage livre = usineOuvrage.creerOuvrage("Dune", "Science-Fiction", "Frank Herbert");

// Créer un exemplaire
Exemplaire ex = usineOuvrage.creerExemplaire(livre, "SF-001");
```

**Ce qui manque / à implémenter si besoin :** une `UsineRevue` pour créer des `Revue` via la même interface.

### State – Machine d'états d'un Emprunt

```
         signalerRetard()
EnCours ─────────────────► EnRetard
   │                           │
   │ retourner()               │ retourner()
   ▼                           ▼
Rendu ◄─────────────────── Rendu

EnCours ──declarerPerte()──► Perdu
EnRetard ─declarerPerte()──► Perdu
```

Transitions autorisées :

| État initial | Action | État final |
|---|---|---|
| `EnCours` | `retourner()` | `Rendu` |
| `EnCours` | `signalerRetard()` | `EnRetard` |
| `EnCours` | `declarerPerte()` | `Perdu` |
| `EnRetard` | `retourner()` | `Rendu` |
| `EnRetard` | `declarerPerte()` | `Perdu` |
| `Rendu` | toute action | `IllegalStateException` |
| `Perdu` | toute action | `IllegalStateException` |

---

## API REST

### Livres – `/api/livres`

#### `GET /api/livres`
Retourne la liste de tous les ouvrages.

**Réponse** `200 OK`
```json
[
  { "id": 1, "titre": "Clean Code", "genre": "Informatique", "auteur": "Robert C. Martin" }
]
```

#### `GET /api/livres/{id}`
Retourne un ouvrage par son id.

**Réponses**
- `200 OK` – ouvrage trouvé
- `404 Not Found` – ouvrage inexistant

#### `POST /api/livres`
Crée un nouveau livre.

**Corps de la requête**
```json
{
  "titre": "Clean Code",
  "auteur": "Robert C. Martin",
  "genre": "Informatique"
}
```

**Réponse** `201 Created`
```json
{ "id": 1, "titre": "Clean Code", "genre": "Informatique" }
```

#### `DELETE /api/livres/{id}`
Supprime un ouvrage.

**Réponse** `204 No Content`

---

### Emprunts – `/api/emprunts`

#### `POST /api/emprunts`
Crée un emprunt pour un exemplaire disponible.

**Corps de la requête**
```json
{ "exemplaireId": 1 }
```

**Réponses**
- `201 Created` – emprunt créé, exemplaire passé à `disponible = false`
- `500` – si l'exemplaire n'est pas disponible (`IllegalStateException`)

#### `PATCH /api/emprunts/{id}/retourner`
Retourne un emprunt. Passe l'état à `Rendu` et l'exemplaire à `disponible = true`.

**Réponse** `200 OK`

#### `PATCH /api/emprunts/{id}/perte`
Déclare un emprunt comme perdu. Passe l'état à `Perdu`.

**Réponse** `200 OK`

---

## Ce qui manque (à implémenter)

### Fonctionnalités non couvertes

**Gestion des Revues**
- Aucun endpoint REST ni méthode de service pour créer/lister des `Revue`.
- `UsineOuvrage.creerOuvrage()` crée toujours un `Livre`. Il faudrait soit un paramètre de type, soit une `UsineRevue` dédiée.

**Gestion des Exemplaires via l'API**
- La méthode `Bibliotheque.ajouterExemplaire()` existe mais aucun controller ne l'expose.
- Il manque : `POST /api/ouvrages/{id}/exemplaires` et `GET /api/ouvrages/{id}/exemplaires`.

**Transition `signalerRetard` non exposée**
- `Emprunt.signalerRetard()` existe en Java mais il n'y a pas d'endpoint `PATCH /api/emprunts/{id}/retard`.

**Gestion des erreurs (exception handler)**
- Les `IllegalArgumentException` et `IllegalStateException` du service remontent en `500`. Il manque un `@ControllerAdvice` pour les mapper proprement en `404` / `400` / `409`.

**Pagination et recherche**
- `OuvrageRepository` expose `findByTitreContainingIgnoreCase` et `findByGenre` mais ces méthodes ne sont pas appelées depuis le service ni exposées en REST.

**Tests manquants**
- Aucun test pour `EmpruntController` (retourner, déclarer perte).
- Aucun test pour les transitions d'état `EnRetard → Rendu` depuis l'API.
- Aucun test pour les cas d'erreur (exemplaire indisponible, emprunt inexistant).

---



## Configuration

`src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:h2:mem:librarydb;DB_CLOSE_DELAY=-1
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

La console H2 est accessible à `http://localhost:8080/h2-console` en développement (JDBC URL : `jdbc:h2:mem:librarydb`).
