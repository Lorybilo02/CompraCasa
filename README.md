# CompraCasa.it

An italian real estate website. 

### Credits:
- [JoCon03](https://github.com/JoCon03) --> Angular Components (announcement-screen, buy-screen, feedback, home-page, map-selector, publishes-screen), image.service.ts, property.service.ts, sharedService.ts, Javascripts
- [Lorybilo02](https://github.com/Lorybilo02) --> Angular Components (admin, feedback, login-screen, myannouncement, signin-screen, terms), auth.service.ts, data.service.ts, RegisterServlet, JwUtil
- [Sim23F](https://github.com/Sim23F) --> DAOs, DataBase, DBManager, map-selector-home Component, Model, Proxies, REST Controllers, Servlets 

The DataBase has not been committed in this repository.

---
## [ITA] Istruzioni

---
__Per Runnare il Progetto avviare la classe CompracasaApplication e Angular (ng serve)__

---
- [DataBase](#database)
  - [Configurare Postgres](#configurare-postgres)  
  - [Per eseguire comandi sul Database](#per-eseguire-comandi-sul-database)
  - [Creare il DataBase](#creare-il-database)
  - [Importare il DataBase](#importare-il-database)
  - [Esportare il DataBase](#esportare-il-database)
  - [Eliminare il DataBase](#eliminare-il-database)

---

## Database

### Configurare Postgres
Su Windows: installa postgresql, impostando "postgres" come password dell'utente postgres  

Su Linux (Mint):
- `sudo apt install postgresql postgresql-contrib`
- `sudo systemctl enable postgresql`
- `sudo systemctl start postgresql`
- `sudo su - postgres`
- `psql`
- `alter user postgres with password 'postgres';`
- `\q` (per uscire dalla console psql)
- `exit` (per uscire dal terminale dell'utente postgres)

### Per eseguire comandi sul Database
Su Windows apri un terminale nella cartella di postgres, di solito C:\Program Files\PostgreSQL\\{version}\bin  

Sul terminale Linux: `sudo su - postgres`

### Creare il Database 
`createdb -U postgres -O postgres wadb`

### Importare il Database
Su Linux copia prima il file wadb.dump nella cartella `/tmp/`   

Se ci sono divergenze tra il database locale e il dump da importare, eliminare e ricreare wadb.  

`pg_restore -U postgres -d wadb -v path/to/wadb.dump`  

### Esportare il DataBase
Su Windows apri un terminale con permessi Amministratore nella cartella di postgres    

`pg_dump -U postgres -F c -b -v -f wadb.dump wadb`  

Spostare il file `wadb.dump` dalla cartella corrente a quella del progetto (su Linux e' necessario passare da /tmp/) 

### Eliminare il DataBase
Aprira la console psql: `psql -U postgres`  
Droppare il database: `drop database wadb;`  
Per uscire dalla console psql: `\q`  
