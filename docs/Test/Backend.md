---

---

Documentation des Tests Unitaires : UsineProduitTest

Cette documentation détaille les tests unitaires pour le composant UsineOuvrage. L'objectif de ces tests est de garantir que la création d'objets (livres, ouvrages) via l'usine (Factory) respecte les spécifications métiers.
1. Structure du Test

Le test utilise le framework JUnit 5 et suit le patron de conception AAA (Arrange, Act, Assert).

    Composant testé : UsineOuvrage

    Objectif : Vérifier la spécialisation des objets créés et l'intégrité de leurs attributs.

2. Détail du cas de test
shouldCreateALivreWithCorrectAttributes

Ce test vérifie que l'usine est capable de générer un objet de type Livre à partir de paramètres textuels.
Étapes du test :

    Initialisation (Arrange) :
    On définit les données d'entrée nécessaires à la création d'un ouvrage :

        Titre : "Le Seigneur des Anneaux"

        Auteur : "Tolkien"

        Genre : "Heroic Fantasy"

    Exécution (Act) :
    On appelle la méthode usine.creerOuvrage(). C'est ici que la logique de l'usine est sollicitée pour décider quel type d'objet instancier.

    Vérification (Assert) :
    Trois points de contrôle sont effectués pour valider le résultat :

        Type d'objet : On vérifie que l'objet retourné est bien une instance de la classe Livre.

        Titre : On s'assure que le titre stocké correspond bien à celui envoyé.

        Auteur : On vérifie que l'attribut spécifique "Auteur" (propre à la classe Livre) a été correctement assigné.

  