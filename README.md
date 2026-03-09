# 📦 Sales & Inventory Management System

Benvenuti nel repository del mio ultimo progetto freelance. Si tratta di una piattaforma gestionale Full-Stack progettata per ottimizzare la gestione del magazzino e il tracciamento delle vendite per piccole e medie imprese.

L'obiettivo principale era sostituire i processi manuali (spesso basati su fogli Excel) con un'applicazione robusta, capace di garantire l'integrità dei dati e fornire reportistica immediata.

---

## 💡 Perché questo progetto è diverso?

Oltre alle classiche operazioni CRUD, mi sono concentrato sulla risoluzione di problemi reali che colpiscono i gestionali:

* **Integrità Storica (Soft Delete):** Invece di eliminare fisicamente i prodotti dal database (causando la perdita dei riferimenti negli ordini passati), ho implementato una logica di "cancellazione logica". Questo permette di mantenere la coerenza contabile pur nascondendo i prodotti non più in vendita.
* **Business Intelligence di base:** La dashboard non si limita a mostrare dati, ma aiuta l'utente a prendere decisioni grazie agli alert visivi sulle scorte in esaurimento.
* **Automazione dei Documenti:** Ho integrato un motore di generazione PDF per creare ricevute d'acquisto al volo, riducendo i tempi burocratici post-vendita.



## 🛠️ Cosa ho utilizzato (Stack Tecnologico)

Per garantire scalabilità e manutenibilità, ho scelto tecnologie standard del settore:

* **Core:** Java 17 con Spring Boot 3.
* **Persistenza:** Spring Data JPA e MySQL (gestione delle relazioni 1:N tra Prodotti e Ordini).
* **UI/UX:** Thymeleaf per il rendering lato server e Bootstrap 5 per un'interfaccia responsive e pulita.
* **Reporting:** OpenPDF per la manipolazione di documenti PDF.

## 🚀 Funzionalità principali

1.  **Dashboard Intuitiva:** Monitoraggio dello stock con filtri automatici per i prodotti attivi.
2.  **Logica di Vendita:** Ogni acquisto aggiorna automaticamente le giacenze e calcola il fatturato totale in tempo reale.
3.  **Gestione Ordini:** Possibilità di annullare un'operazione errata con ripristino automatico dello stock (storno).
4.  **Esportazione:** Download immediato di ricevute PDF personalizzate.



## ⚙️ Come avviarlo in locale

1.  Assicurati di avere **Java 17** e **Maven** installati.
2.  Configura il tuo DB MySQL in `src/main/resources/application.properties`.
3.  Esegui il comando:
    ```bash
    mvn spring-boot:run
    ```
4.  Apri il browser su `http://localhost:8080/dashboard`.

---
### 👨‍💻 Chi sono
Sono uno sviluppatore appassionato di Java e architetture backend. Mi piace costruire strumenti che risolvono problemi reali e rendono il lavoro delle persone più semplice.

**Contatti:** www.linkedin.com/in/gianluca-pappalardo-3b39ab170