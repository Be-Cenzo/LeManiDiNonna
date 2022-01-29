create table if not exists Prodotto(
`codice` int auto_increment primary key,
`tipo` varchar(30) not null,
`prezzo` float not null,
`colore` varchar(10) not null,
`descrizione` varchar(30) not null,
`marca` varchar(20),
`modello` varchar(20)
);