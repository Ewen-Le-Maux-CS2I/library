package com.library.factory;
import com.library.model.*;
import org.springframework.stereotype.Service;

import java.Interfaces.UsineAbstraite;


private class UsineOuvrage implements UsineAbstraite {
    @Override
    public Produit creerProduit() {
        return new Ouvrage();
    }

    @Override
    public Exemplaire creerExemplaire() {
        return new Exemplaire();
    }
}