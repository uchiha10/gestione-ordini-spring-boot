package com.gianluca.gestione_ordini.model; // Assicurati che il nome del package sia uguale agli altri file

import jakarta.persistence.*;
import lombok.Data;

@Entity // Dice a Docker/MySQL: "Crea una tabella chiamata prodotto"
@Data   // Lombok crea automaticamente i metodi per leggere e scrivere i dati
public class Prodotto {

    @Id // numero identificativo unico (Chiave Primaria)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Si autoincrementa (1, 2, 3...)
    private Long id;
    private boolean attivo=true;

    private String nome;
    private Double prezzo;
    private int quantita=0;

}

