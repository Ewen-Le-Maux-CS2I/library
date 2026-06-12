# README

## Stack technique

| Composant | Technologie |
|---|---|
| Langage | Java 21 |
| Framework | Spring Boot 3.3 |
| Persistance | Spring Data JPA + MariaDB (prod) / H2 (tests) |
| API | Spring MVC REST |
| Tests | JUnit 5, @SpringBootTest, @DataJpaTest |
| Build | Maven 3.9 |
| Conteneurs | Docker + Docker Compose |

---

## Lancer le projet

```bash
# Depuis la racine du projet
    make up
```

L'API est disponible sur `http://localhost:8080`.
La doc de l'API sur `http://localhost:8080/swagger-ui/index.html`.
Le Front sur `http://localhost:4200`. 
La documentation du projet `https://ewen-le-maux-cs2i.github.io/library/`

