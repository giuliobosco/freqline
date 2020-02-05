# freqline - analysis

- analisi iniziale 16h
- analisi componenti da comprare 4h
  - telecomando
- connessione arduino raspberry (241) 50h
  - studio dell'arte
  - implementazione
- mettere tutto assieme 20h
  - java
  - interfaccia wifi - dhcp (dns)
- sistemare gli arrori
  - front-end migliorare - 16h (153, 240, 148, 193, 254)
    - controlli input
    - gestione navigazione
    - pop-up
  - back-end (punto 240) 16h
    - cambio frequenza
    - utente cambia i suoi dati
  - microfono - 4h
  - commenti (232) - 10h
- consegna finale 4h
- documentazione

## Sistemare problema comunicazione

IDEE

- YUN
  Cercare di trovare una soluzione sul funzionamento dell'arduino YUN.
- Usare arduino UNO con comunicazione seriale
  - comunicazione seriale tramite mini applicativo web
    programmino scritto in python per riutilizzare il codice gia scritto, servirebbe solamente riscrivere classe GlobalBridge e lato arduino.
  - comunicazione seriale direttamente in java
    bisognerebbe scrivere la comunicazione seriale che funzioni in java e lato arduino.

