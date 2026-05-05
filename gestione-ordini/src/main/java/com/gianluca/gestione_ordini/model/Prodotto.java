package com.gianluca.gestione_ordini.model; // Assicurati che il nome del package sia uguale agli altri file

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity // Dice a Docker/MySQL: "Crea una tabella chiamata prodotto"
@Data   // Lombok crea automaticamente i metodi per leggere e scrivere i dati
public class Prodotto {

    @Id // numero identificativo unico (Chiave Primaria)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Si autoincrementa (1, 2, 3...)
    private Long id;
    private boolean attivo=true;

    @NotBlank(message = "Il nome del prodotto è obbligatorio")
    private String nome;

    @NotNull(message = "La quantità è obbligatoria")
    @Min(value = 1, message = "La quantità deve essere almeno 1")
    private double prezzo;

    @NotNull(message = "Il prezzo è obbligatorio")
    @Min(value = 0, message = "Il prezzo non può essere negativo")
    private int quantita=0;

}

