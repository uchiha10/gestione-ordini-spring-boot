package com.gianluca.gestione_ordini.service;

import com.gianluca.gestione_ordini.model.Ordine;
import com.gianluca.gestione_ordini.model.Prodotto;
import com.gianluca.gestione_ordini.repository.OrdineRepository;
import com.gianluca.gestione_ordini.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    public String processaOrdine(Long prodottoId, Integer qta) {
        // Controllo sulla quantità
        if (qta <= 0) {
            return "Errore: La quantità deve essere almeno 1!";
        }

        // Cerchiamo il prodotto
        Prodotto p = prodottoRepository.findById(prodottoId).orElse(null);
        if (p == null) {
            return "Errore: Prodotto non trovato!";
        }
        // Calcolo del totale e applicazione sconto in caso di quantità maggiore di 5
        double totale=p.getPrezzo() * qta;
        if (qta>=5) {
            double sconto=totale*10/100;
            totale=totale-sconto;
        }
        if (p.getQuantita()<qta) {
            return "Errore: disponibilità insufficiente! Rimanenti: " + p.getQuantita();
        }
        else {
            p.setQuantita(p.getQuantita()-qta);
        }


        // Salvataggio
        Ordine o = new Ordine();
        o.setProdotto(p);
        o.setQuantita(qta);
        o.setDataOrdine(LocalDateTime.now());
        o.setSpesaTotale(totale);
        ordineRepository.save(o);
        prodottoRepository.save(p);

        return "Ordine completato! Hai comprato " + qta + " " + p.getNome() +
                ". Totale speso: " + totale + "€";
    }
    public java.util.List<Ordine> getAllOrdini() {
        return ordineRepository.findAll();
    }
    public String annullaOrdine(Long ordineId) {
        // 1. Cerchiamo l'ordine
        Ordine o = ordineRepository.findById(ordineId).orElse(null);
        if (o == null) return "Errore: Ordine non trovato!";

        // 2. Recuperiamo il prodotto collegato e restituiamo la quantità
        Prodotto p = o.getProdotto();
        p.setQuantita(p.getQuantita() + o.getQuantita());

        // 3. Salviamo il prodotto aggiornato
        prodottoRepository.save(p);

        // 4. Cancelliamo l'ordine dal database
        ordineRepository.delete(o);

        return "Ordine #" + ordineId + " annullato. " + o.getQuantita() + " pezzi tornati in magazzino.";
    }
}