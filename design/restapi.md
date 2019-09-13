# RestAPI - Design

Ecco l'elenco delle API, che c'&egrave; bisogno per il corretto funzionamento del
progetto.

## APIs Personalizzata

- login

## Management APIs

- permissions type
- groups
- users (favorite)
- speaker
- favorites

Per ogni API, vi possono essere diverse richieste da fare.

## Login

La API `login`, serve per effettuare il login sulla piattaforma.

La richiest&agrave; dovr&agrave; contenere:

```
{
    "login": {
        "username": "<username>",
        "password": "<password>"
    }
}
```

Mentre la risposta pu&ograve; essere di 2 tipi, o esito positivo o negativo:

Positiva:

```
{
    "login": {
        "status": "OK",
        "message": "<username> has been logged in."
    }
}
```

Negativa:

```
{
    "login": {
        "status": "ERROR",
        "message": "Username or password are wrong"
    }
}
```

## Management APIs

Per ogni API di gestione, vi sono dei metodi ricorrenti:

- getAll
- get
- add
- update
- remove
- search

le richieste venono strutturate, come segue:

```
http://host:port/api/v0/folder/<api_name>/<action>[/<parameter>]
```

### getAll

La API `getAll`, non ha parametri, ma pu&ograve; ritornare errore nel caso in
cui non si abbiano i permessi per richiedere quei dati.

### get

La API `get`, &egrave; molto simile a quella `getAll`, con la differenza che ha bisogno
del parametro per richiedere l'elemento da ritornare. Pu&ograve; ritornare errore nel caso in
cui non si abbiano i permessi per richiedere quei dati.

### add

La API `add` serve per aggiungere un dato, il quale necessita come parametro una
stringa JSON con i dati da aggiungere. Pu&ograve; ritornare errore nel caso in
cui non si abbiano i permessi per richiedere quei dati, oppure i dati non sono valevoli.

### update

La API `update` serve per aggiornare un dato, il quale necessita come parametro (POST) una
stringa JSON con i dati da aggiungere e quale dato aggiornare, quindi il suo id.
Pu&ograve; ritornare errore nel caso in cui non si abbiano i permessi per richiedere
quei dati, oppure i dati non sono valevoli.

### remove

La API `remove` serve per eliminare un dato, la quale necessita come parametro
l'id dell'elemento da eliminare. Pu&ograve; ritornare errore nel caso in cui non
si abbiano i permessi per richiedere quei dati, oppure i dati non sono valevoli.

### search

La API `search`, serve per cercare le parole passate come argomenti nella tabella specificata.
