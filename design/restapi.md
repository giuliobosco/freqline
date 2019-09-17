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

- getAll    (GET request `/users`)
- get       (GET request with id `/users/1`)
- add       (PUT request `/users`)
- update    (PUT request with id `/users/1`)
- remove    (DELETE request `/users/1`)

Prima delle api, bisogna inserire la stringa di richiesta come segue:

```
http://host:port/api/v0/folder
```

Quando non vi si anno i permessi per la richiesta effettuata viene ritornato lo stato http:
`403 FORBIDDEN`.  
Se la richiesta &egrave; di tipo richiesta di dati (come `getAll` o `get`) la risposta deve essere
`200 OK`.  Se l'elemento richiesto non esiste la risposta sar&agrave; `400 BAD REQUEST`.  
Se la richiesta &egrave; di tipo invio di dati e viene eseguita correttamente la risposta
sar&agrave; `201 CREATED`. Se nella richiesta vi sono i dati la risposta sar&agrave;
`204 NO CONTENT`. Se invece i dati non sono validi `400 BAD REQUEST`.

### getAll

La API `getAll`, non ha parametri, ma pu&ograve; ritornare errore nel caso in
cui non si abbiano i permessi per richiedere quei dati (403 FORBIDDEN).

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
