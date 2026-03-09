package com.gianluca.gestione_ordini.controller;

import com.gianluca.gestione_ordini.model.Ordine;
import com.gianluca.gestione_ordini.repository.OrdineRepository;
import com.gianluca.gestione_ordini.repository.ProdottoRepository;
import com.gianluca.gestione_ordini.service.OrdineService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.gianluca.gestione_ordini.model.Prodotto;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ProdottoRepository prodottoRepository;

    @GetMapping("/dashboard")
    public String mostraDashboard(Model model) {
        // Mostriamo solo i prodotti attivi nella dashboard
        List<Prodotto> prodottiAttivi = prodottoRepository.findAll()
                .stream()
                .filter(Prodotto::isAttivo)
                .toList();
        model.addAttribute("prodotti", prodottiAttivi);
        return "dashboard";
    }

    @PostMapping("/nuovo-prodotto-web")
    public String aggiungiProdotto(@RequestParam String nome,
                                   @RequestParam Double prezzo,
                                   @RequestParam Integer quantita) {
        Prodotto p = new Prodotto();
        p.setNome(nome);
        p.setPrezzo(prezzo);
        p.setQuantita(quantita);

        prodottoRepository.save(p);

        return "redirect:/dashboard"; // Dopo il salvataggio, ricarica la pagina aggiornata
    }
    @GetMapping("/elimina-prodotto-web/{id}")
    public String eliminaProdotto(@PathVariable Long id) {
        Prodotto p = prodottoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setAttivo(false); // Non cancelliamo, disattiviamo
            prodottoRepository.save(p);
        }
        return "redirect:/dashboard";
    }
    @Autowired
    private OrdineRepository ordineRepository; // Se non l'hai già aggiunto sopra

    @GetMapping("/visualizza-ordini")
    public String mostraOrdini(Model model) {
        Iterable<Ordine> ordini = ordineRepository.findAll();
        double guadagnoTotale = 0;

        for (Ordine o : ordini) {
            if (o.getSpesaTotale() != null) {
                guadagnoTotale += o.getSpesaTotale();
            }
        }

        model.addAttribute("ordini", ordini);
        model.addAttribute("totaleIncassato", guadagnoTotale);
        return "ordini";
    }
    @Autowired
    private OrdineService ordineService;

    @GetMapping("/annulla-ordine-web/{id}")
    public String annullaOrdine(@PathVariable Long id) {
        ordineService.annullaOrdine(id);
        return "redirect:/visualizza-ordini";
    }

    @PostMapping("/compra-web")
    public String compraWeb(@RequestParam Long prodottoId, @RequestParam Integer quantita) {
        // Usiamo la logica che hai già scritto nel Service!
        ordineService.processaOrdine(prodottoId, quantita);

        // Dopo l'acquisto, portami a vedere lo storico ordini
        return "redirect:/visualizza-ordini";
    }

    @GetMapping("/ordine/pdf/{id}")
    public void generaRicevuta(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Ordine ordine = ordineRepository.findById(id).orElse(null);
        if (ordine == null) return;

        // Impostiamo il tipo di file e il nome
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ricevuta_" + id + ".pdf");

        // Creiamo il documento
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        // Font personalizzati
        Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fontTesto = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Contenuto del PDF
        documento.add(new Paragraph("RICEVUTA D'ACQUISTO", fontTitolo));
        documento.add(new Paragraph("--------------------------------------------------"));
        documento.add(new Paragraph("ID Ordine: " + ordine.getId(), fontTesto));
        documento.add(new Paragraph("Prodotto: " + ordine.getProdotto().getNome(), fontTesto));
        documento.add(new Paragraph("Quantità: " + ordine.getQuantita(), fontTesto));
        documento.add(new Paragraph("Prezzo Unitario: " + ordine.getProdotto().getPrezzo() + " €", fontTesto));
        documento.add(new Paragraph("--------------------------------------------------"));
        documento.add(new Paragraph("TOTALE PAGATO: " + ordine.getSpesaTotale() + " €", fontTitolo));

        documento.close();
    }

}