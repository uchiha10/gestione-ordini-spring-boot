package com.gianluca.gestione_ordini;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gianluca.gestione_ordini.model.Ordine;
import com.gianluca.gestione_ordini.model.Prodotto;
import com.gianluca.gestione_ordini.repository.OrdineRepository;
import com.gianluca.gestione_ordini.repository.ProdottoRepository;
import com.gianluca.gestione_ordini.service.OrdineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrdineServiceTest {

	@Mock
	private OrdineRepository ordineRepository;

	@Mock
	private ProdottoRepository prodottoRepository;

	@InjectMocks
	private OrdineService ordineService;

	@Test
	void testProcessaOrdineConSconto() {
		// GIVEN: Un prodotto che costa 100€ e ne abbiamo 10 in magazzino
		Prodotto p = new Prodotto();
		p.setId(1L);
		p.setNome("Laptop");
		p.setPrezzo(100.0);
		p.setQuantita(10);

		when(prodottoRepository.findById(1L)).thenReturn(Optional.of(p));

		// WHEN: Ordiniamo 5 pezzi (scatta lo sconto del 10%)
		// 500€ - 10% = 450€
		String risultato = ordineService.processaOrdine(1L, 5);

		// THEN: Verifichiamo che il totale sia corretto nel messaggio
		assertTrue(risultato.contains("450.0"));
		assertEquals(5, p.getQuantita()); // Il magazzino deve essere sceso a 5
		verify(ordineRepository, times(1)).save(any(Ordine.class));
	}

	@Test
	void testProcessaOrdineDisponibilitaInsufficiente() {
		// GIVEN: Solo 2 pezzi disponibili
		Prodotto p = new Prodotto();
		p.setId(1L);
		p.setQuantita(2);
		when(prodottoRepository.findById(1L)).thenReturn(Optional.of(p));

		// WHEN: Ne ordiniamo 10
		String risultato = ordineService.processaOrdine(1L, 10);

		// THEN: Deve restituire l'errore
		assertTrue(risultato.contains("disponibilità insufficiente"));
		verify(ordineRepository, never()).save(any(Ordine.class));
	}
}