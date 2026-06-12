

Worflow basé sur la bonne écriture des commit selon le _Conventional Commits_

| Fichier / Étape | Outil / Action | Ce qu'il valide |
| :--- | :--- | :--- |
| `Commitlint Workflow` | GitHub Actions | Déclenche l'analyse à chaque `push` ou `pull_request` vers `main` |
| `fetch-depth: 0` | Git Checkout | Télécharge l'historique complet pour permettre l'analyse des messages |
| `commitlint.config.cjs` | Configuration | Active l'extension standard `Conventional Commits` |

## Règle d'écriture des Commits

Pour être accepté par GitHub, chaque message de commit doit obligatoirement suivre cette structure :
```text
type(portee_optionnelle): description courte en minuscules