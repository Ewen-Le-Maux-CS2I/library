

Application : Gestion de Bibliothèque Centrale (Client Angular)

Approche de Développement : TDD (Test-Driven Development)

Framework CSS : Tailwind CSS
1. Architecture du Projet

Le projet respecte une architecture modulaire et découplée où les composants UI ne connaissent pas la logique d'accès aux données (gérée par les services).

src/app/
├── models/                  # Interfaces de typage calquées sur le Backend
│   ├── ouvrage.model.ts     # Typage générique (Abstract Factory Backend)
│   ├── exemplaire.model.ts  # Copies physiques d'un ouvrage
│   └── emprunt.model.ts     # Liaison Adhérent <-> Exemplaire
├── services/                # Services gérant les requêtes HTTP (API Rest)
│   ├── ouvrage.service.ts
│   ├── exemplaire.service.ts
│   └── emprunt.service.ts
└── components/              # Composants Visuels (Interface utilisateur)
    ├── ouvrage-list/        # Consultation du catalogue et sélection
    └── emprunt-manager/     # Formulaire d'emprunt et tableau des encours

2. Description des Couches
A. Couche Modèle (/models)

Les modèles garantissent un typage strict pour la communication avec l'API Spring Boot.

    Ouvrage : Utilise un champ discriminant type: 'LIVRE' | 'REVUE'. Si c'est un livre, la propriété isbn est exploitée. Si c'est une revue, c'est la propriété numero. Cela mappe directement le Pattern Abstract Factory de votre backend.

    Exemplaire : Représente l'objet physique en stock possédant un codeBarre unique et un état booléen disponible.

    Emprunt : Représente la transaction associant un idAdherent et un Exemplaire physique, avec des dates de gestion.

B. Couche Service (/services)

Chaque service encapsule les appels HTTP vers l'API. Ils utilisent HttpClient et retournent des flux Observable.

    OuvrageService : GET /api/ouvrages (Récupère tout le catalogue).

    ExemplaireService : GET /api/ouvrages/{id}/exemplaires (Récupère les stocks d'un ouvrage).

    EmpruntService : POST /api/emprunts (Enregistre une réservation/un emprunt) et GET /api/emprunts/actifs.

C. Couche Composant (/components)

    OuvrageListComponent : Permet à l'utilisateur de parcourir la liste de tous les livres et revues. Au clic sur une carte, le composant appelle l'API pour charger dynamiquement les exemplaires associés dans un panneau latéral et propose un bouton "Réserver".

    EmpruntManagerComponent : Fournit une interface de gestion (généralement pour le personnel) permettant d'effectuer des emprunts manuels à l'aide d'un formulaire réactif (ReactiveFormsModule).