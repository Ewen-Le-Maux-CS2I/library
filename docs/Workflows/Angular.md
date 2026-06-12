# Workflow Frontend (Angular CI)

| Fichier / Étape | Outil / Action | Ce qu'il valide / exécute |
| :--- | :--- | :--- |
| `Angular Workflow` | GitHub Actions | Déclenche le pipeline à chaque `push` ou `pull_request` vers `main` |
| `working-directory: frontend` | Configuration | Force l'exécution de toutes les étapes dans le sous-dossier de l'application Angular |
| `Configuration de Node.js` | Node Setup | Installe l'environnement Node.js (v26) et met en cache les paquets `npm` pour accélérer les futurs builds |
| `Installation des dépendances` | NPM Engine | Installe proprement, de manière stricte et reproductible les packages du projet (`npm ci`) |
| `Audit et Sécurité` | NPM Audit | Détecte et applique automatiquement les correctifs de sécurité sur les dépendances du projet (`npm audit fix`) |
| `Vérification de la compilation` | Angular Build | Compile l'application en mode production pour s'assurer qu'il n'y a aucune erreur TypeScript ou de template |
| `Exécution des tests unitaires` | Vitest / Playwright | Lance l'intégralité de la suite de tests dans un navigateur Chromium invisible (`npm run test:ci`) |

Lancer manuellement ces étapes en local (dans le dossier frontend) :
```bash
# Pour installer les dépendances proprement
npm ci

# Pour vérifier la sécurité et corriger les failles
npm audit fix

# Pour compiler le projet et lancer les tests
npm run build -- --configuration=production && npm run test:ci

