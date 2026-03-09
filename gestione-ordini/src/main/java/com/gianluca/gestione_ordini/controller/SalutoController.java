package com.gianluca.gestione_ordini.controller;

import com.gianluca.gestione_ordini.model.Ordine;
import com.gianluca.gestione_ordini.model.Prodotto;
import com.gianluca.gestione_ordini.repository.OrdineRepository;
import com.gianluca.gestione_ordini.repository.ProdottoRepository;
import com.gianluca.gestione_ordini.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class SalutoController {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @GetMapping("/test-db")
    public String testDatabase() {
        long conteggio = prodottoRepository.count();
        return "Connessione SQL riuscita! Ci sono " + conteggio + " prodotti nel database.";
    }
    @PostMapping("/nuovo-prodotto")
    public String creaProdotto(@RequestBody Prodotto p) {
        prodottoRepository.save(p);
        return "Prodotto " + p.getNome() + " creato con " + p.getQuantita() + " pezzi!";
    }
    @GetMapping("/lista")
    public java.util.List<Prodotto> mostraTutti() {
        return prodottoRepository.findAll(); // Recupera tutti i record della tabella
    }
    @GetMapping("/elimina/{id}")
    public String eliminaProdotto(@PathVariable Long id) {
        if (prodottoRepository.existsById(id)) {
            prodottoRepository.deleteById(id);
            return "Prodotto con ID " + id + " eliminato con successo!";
        } else {
            return "Errore: Il prodotto con ID " + id + " non esiste.";
        }
    }
    @GetMapping("/aggiorna-prezzo/{id}/{nuovoPrezzo}")
    public String aggiornaPrezzo(@PathVariable Long id, @PathVariable Double nuovoPrezzo) {

        // 1. Recuperiamo il prodotto dal DB usando l'ID (Read)
        Prodotto p = prodottoRepository.findById(id).orElseThrow();

        // 2. Modifichiamo solo il campo che ci interessa (Update in memoria)
        p.setPrezzo(nuovoPrezzo);

        // 3. Salviamo l'oggetto. Dato che 'p' ha già l'ID, Spring farà un UPDATE SQL
        prodottoRepository.save(p);

        return "Prezzo del prodotto " + p.getNome() + " aggiornato a " + nuovoPrezzo;
    }
    @Autowired
    private OrdineService ordineService;
    @PostMapping("/compra/{prodottoId}/{qta}")
    public String creaOrdine(@PathVariable Long prodottoId, @PathVariable Integer qta) {
        // Il controller delega tutto al service
        return ordineService.processaOrdine(prodottoId,qta);
    }
    @GetMapping("/lista-ordini")
    public java.util.List<Ordine> listaOrdini() {
        return ordineService.getAllOrdini();
    }
    @DeleteMapping("/annulla-ordine/{id}")
    public String annulla(@PathVariable Long id) {
        return ordineService.annullaOrdine(id);
    }
}