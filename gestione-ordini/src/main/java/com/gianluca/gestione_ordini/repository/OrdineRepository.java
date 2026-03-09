package com.gianluca.gestione_ordini.repository;

import com.gianluca.gestione_ordini.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
}