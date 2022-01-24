
create table if not exists Account(
`email` varchar(50) primary key,
`nome` varchar(30) not null,
`cognome` varchar(30) not null,
`dataNascita` date not null,
`password` varchar(32) not null,
`nomeIG` varchar(30)
);

create table if not exists NumTel(
`num` varchar(13),
`email` varchar(50),
primary key (`num`, `email`)
--foreign key (email) references Account(email) on update cascade
);

create table if not exists Indirizzo(
`ID` int,
`email` varchar(50),
`provincia` varchar(50) not null,
`comune` varchar(50) not null,
`via` varchar(50) not null,
`civico` int not null,
`cap` varchar(5) not null,
primary key (`ID`, `email`)
--foreign key (email) references Account(email) on update cascade
);

create table if not exists Corriere(
`nome` varchar(30) primary key,
`tempo` varchar(30) not null,
`costoSped` float not null
);

create table if not exists Ordine(
`ID` int primary key auto_increment,
`data` timestamp not null,
`prezzo` float not null,
`costoSped` float not null,
`note` varchar(100),
`stato` varchar(15) not null,
`email` varchar(50) not null,
`indirizzo` int not null,
`corriere` varchar(30) not null
--foreign key (email) references Account(email) on update cascade,
--foreign key (indirizzo, email) references Indirizzo(ID, email) on update cascade,
--foreign key (corriere) references Corriere(nome) on update cascade
);

create table if not exists Prodotto(
`codice` int auto_increment primary key,
`tipo` varchar(30) not null,
`prezzo` float not null,
`colore` varchar(10) not null,
`descrizione` varchar(30) not null,
`marca` varchar(20),
`modello` varchar(20),
`imgurl` mediumblob
);

create table if not exists Taglie(
`prodotto` int,
`taglia` varchar(1),
primary key (`prodotto`, `taglia`)
--foreign key (prodotto) references Prodotto(codice) on update cascade
);

create table if not exists Relato(
`prodotto` int,
`taglia` varchar(1),
`ordine` int,
`quantita` int not null,
primary key (`prodotto`, `ordine`, `taglia`)
--foreign key (prodotto, taglia) references Taglie(prodotto, taglia) on update cascade,
--foreign key (ordine) references Ordine(ID) on update cascade
);

create table if not exists Deposito(
`ID` int auto_increment primary key,
`luogo` varchar(30) not null
);

create table if not exists Conservato(
`deposito` int,
`prodotto` int,
`taglia` varchar(1),
`disponibilita` int not null,
primary key (`deposito`, `prodotto`, `taglia`)
--foreign key (deposito) references Deposito(ID) on update cascade,
--foreign key (prodotto, taglia) references Taglie(prodotto, taglia) on update cascade
);
