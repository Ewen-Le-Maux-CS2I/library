package com.library.factory;

import com.library.entities.Exemplaire;
import com.library.entities.Ouvrage;

public abstract class UsineAbstraite {
    public abstract Ouvrage creerOuvrage(String titre, String genre, String auteurOuNumero);
    public abstract Exemplaire creerExemplaire(Ouvrage ouvrage, String cote);
}
