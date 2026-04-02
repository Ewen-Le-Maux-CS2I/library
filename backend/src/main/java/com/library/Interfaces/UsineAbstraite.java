package com.library.factory;
import com.library.entities.Produit;
import com.library.entities.Exemplaire;

public abstract class UsineAbstraite {
    public abstract Produit creerProduit();
    public abstract Exemplaire creerExemplaire();

}