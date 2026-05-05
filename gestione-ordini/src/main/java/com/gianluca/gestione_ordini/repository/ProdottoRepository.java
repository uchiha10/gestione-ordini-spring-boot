package com.gianluca.gestione_ordini.repository;

import com.gianluca.gestione_ordini.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

// Questa interfaccia produce tutti i comandi SQL (save, findAll, delete) senza scrivere codice
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
}