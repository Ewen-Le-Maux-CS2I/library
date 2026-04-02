package com.library.factory;
import com.library.Entities.*;
import org.springframework.stereotype.Service;

import java.com.library.Interfaces.UsineAbstraite;


public class UsineOuvrage implements UsineAbstraite {
    @Override
    public Produit creerProduit() {
        return new Ouvrage();
    }

    @Override
    public Exemplaire creerExemplaire() {
        return new Exemplaire();
    }
}