# Workflow Documentation (GitHub Pages Deployment)

| Fichier / Étape | Outil / Action | Ce qu'il valide / exécute |
| :--- | :--- | :--- |
| `Documentation Workflow` | GitHub Actions | Déclenche le pipeline à chaque `push` sur les branches `main` ou `master` |
| `permissions` | Sécurité GitHub | Accorde les droits nécessaires pour générer des jetons d'authentification et publier du contenu sur GitHub Pages |
| `Set up Python 3.x` | Python Setup | Installe l'environnement Python requis pour l'outil de génération de documentation |
| `pip install zensical` | Zensical Engine | Installe le générateur de site statique spécialisé pour compiler les fichiers de documentation |
| `zensical build --clean` | Compilation Doc | Nettoie les anciens caches et compile les fichiers Markdown en un site web HTML statique (dossier `site`) |
| `upload-pages-artifact` | GitHub Artifacts | Compresse et télécharge le dossier généré (`site`) pour préparer sa mise en ligne |
| `deploy-pages` | GitHub Deployment | Publie officiellement le site web de documentation sur la plateforme d'hébergement gratuite **GitHub Pages** |

Lancer manuellement la génération du site en local :
```bash
# Installez l'outil de documentation
pip install zensical

# Compilez la documentation localement
zensical build --clean