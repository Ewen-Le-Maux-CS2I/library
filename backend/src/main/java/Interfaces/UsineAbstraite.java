package com.library.factory;
import com.library.entities.Produit;
import com.library.entities.Exemplaire;

private abstract class UsineAbstraite {
    public abstract Produit creerProduit();
    public abstract Exemplaire creerExemplaire();

}