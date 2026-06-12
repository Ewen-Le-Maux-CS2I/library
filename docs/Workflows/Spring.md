# Spring

| Fichier / Étape | Outil / Action | Ce qu'il valide / execute |
| :--- | :--- | :--- |
| `Spring Workflow` | GitHub Actions | Déclenche le pipeline à chaque `push` ou `pull_request` vers `main` |
| `working-directory: backend` | Configuration | Force l'exécution de toutes les étapes dans le sous-dossier du projet Spring |
| `Set up JDK 21 (Temurin)` | Java Setup | Installe l'environnement Java 21 et met en cache les dépendances Maven pour accélérer les builds |
| `Run Linter` | Spotless Formatter | Formate automatiquement le code (`spotless:apply`) et vérifie le respect des normes graphiques (`spotless:check`) |
| `Build and Run Tests (TDD)` | Maven Engine | Nettoie, compile l'application et exécute l'intégralité de la suite de tests unitaires et d'intégration (`mvn clean verify`) |

Lancer manuellement ces étapes en local :
```bash
# Pour formater et vérifier le code
mvn spotless:apply && mvn spotless:check

# Pour compiler et lancer tous les tests
mvn clean verify