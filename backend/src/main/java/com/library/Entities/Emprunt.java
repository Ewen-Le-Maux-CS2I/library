package com.library.model;

import com.library.state.EtatEmprunt;
import com.library.state.EnCours;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Emprunt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient 
    private EtatEmprunt etatActuel = new EnCours(); 

    public String getNomEtat() {
        return etatActuel.getNom();
    }
}