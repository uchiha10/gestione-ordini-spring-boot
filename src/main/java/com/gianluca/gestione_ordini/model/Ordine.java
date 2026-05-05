package com.gianluca.gestione_ordini.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Data
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataOrdine;
    private Integer quantita;
    private Double SpesaTotale;

    // Qui creiamo il collegamento: Molti ordini possono riferirsi a UN solo prodotto
    @ManyToOne
    @JoinColumn(name = "prodotto_id") // Questa sarà la colonna "ponte" nel database
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Prodotto prodotto;
}