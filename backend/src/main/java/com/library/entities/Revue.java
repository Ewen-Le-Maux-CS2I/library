package com.library.entities;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Revue extends Ouvrage {

    private String numero;
    private LocalDate dateParution;

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public LocalDate getDateParution() { return dateParution; }
    public void setDateParution(LocalDate dateParution) { this.dateParution = dateParution; }
}
