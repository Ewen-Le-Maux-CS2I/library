package com.library.factory;

import com.library.entities.Exemplaire;
import com.library.entities.Livre;
import com.library.entities.Ouvrage;
import org.springframework.stereotype.Component;

@Component
public class UsineOuvrage extends UsineAbstraite {

    /**
     * Crée un Livre avec les attributs fournis.
     *
     * @param titre  titre de l'ouvrage
     * @param genre  genre littéraire
     * @param auteur nom de l'auteur
     */
    @Override
    public Ouvrage creerOuvrage(String titre, String genre, String auteur) {
        Livre livre = new Livre();
        livre.setTitre(titre);
        livre.setGenre(genre);
        livre.setAuteur(auteur);
        return livre;
    }

    @Override
    public Exemplaire creerExemplaire(Ouvrage ouvrage, String cote) {
        Exemplaire ex = new Exemplaire();
        ex.setOuvrage(ouvrage);
        ex.setCote(cote);
        ex.setDisponible(true);
        return ex;
    }
}
