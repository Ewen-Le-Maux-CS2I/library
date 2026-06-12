---
Title: Frontend
---

# Frontend

# Frontend

| Fichier | Type | Ce qu'il teste |
| :--- | :--- | :--- |
| `app.spec.ts` | Unitaire | Démarrage et instanciation du contexte racine `App` |
| `ouvrage.service.spec.ts` | Unitaire (HTTP) | Requête `GET /api/ouvrages` et désérialisation du catalogue |
| `emprunt.service.spec.ts` | Unitaire (HTTP) | Requêtes `POST /api/emprunts` et cycle d'appels d'API |
| `ouvrage-list.component.spec.ts` | Intégration DOM | Rendu du catalogue, sélection et présence des badges Tailwind (`LIVRE` / `REVUE`) |
| `emprunt-manager.component.spec.ts` | Intégration DOM | Remplissage, validation et soumission du formulaire réactif d'emprunt |

Lancer les tests :
```bash
npm run test:ci