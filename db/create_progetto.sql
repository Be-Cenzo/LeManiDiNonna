drop schema if exists eCommerce;

create schema eCommerce;
use eCommerce;

drop user if exists 'applicazione'@'localhost';
create user 'applicazione'@'localhost';
grant all on eCommerce.* to 'applicazione'@'localhost';

create table Account(
email varchar(50) primary key,
nome varchar(30) not null,
cognome varchar(30) not null,
dataNascita date not null,
password varchar(32) not null,
nomeIG varchar(30)
);

create table NumTel(
num varchar(13),
email varchar(50),
primary key (num, email),
foreign key (email) references Account(email) on update cascade
);

create table Indirizzo(
ID int,
email varchar(50),
provincia varchar(50) not null,
comune varchar(50) not null,
via varchar(50) not null,
civico int not null,
cap varchar(5) not null,
primary key (ID, email),
foreign key (email) references Account(email) on update cascade
);

create table Corriere(
nome varchar(30) primary key,
tempo varchar(30) not null,
costoSped float not null
);

create table Ordine(
ID int primary key auto_increment,
data timestamp not null,
prezzo float not null,
costoSped float not null,
note varchar(100),
stato varchar(15) not null,
email varchar(50) not null,
indirizzo int not null,
corriere varchar(30) not null,
foreign key (email) references Account(email) on update cascade,
foreign key (indirizzo, email) references Indirizzo(ID, email) on update cascade,
foreign key (corriere) references Corriere(nome) on update cascade
);

create table Fattura(
ID int primary key auto_increment,
data timestamp not null,
ammontare float not null,
modalità varchar(30) not null,
ordine int not null,
foreign key (ordine) references Ordine(ID)
);

create table Prodotto(
codice int auto_increment primary key,
tipo varchar(30) not null,
prezzo float not null,
colore varchar(10) not null,
descrizione varchar(30) not null,
marca varchar(20),
modello varchar(20),
imgurl varchar(50)
);

create table Taglie(
prodotto int,
taglia varchar(1),
primary key (prodotto, taglia),
foreign key (prodotto) references Prodotto(codice) on update cascade
);

create table Relato(
prodotto int,
taglia varchar(1),
ordine int,
quantità int not null,
primary key (prodotto, ordine, taglia),
foreign key (prodotto, taglia) references Taglie(prodotto, taglia) on update cascade,
foreign key (ordine) references Ordine(ID) on update cascade
);

create table Deposito(
ID int auto_increment primary key,
luogo varchar(30) not null
);

create table Conservato(
deposito int,
prodotto int,
taglia varchar(1),
disponibilità int not null,
primary key (deposito, prodotto),
foreign key (deposito) references Deposito(ID) on update cascade,
foreign key (prodotto, taglia) references Taglie(prodotto, taglia) on update cascade
);

create table Fornitore(
nome varchar(20) primary key
);

create table Fornitura(
ID int auto_increment,
fornitore varchar(20),
data timestamp not null,
quantità int not null,
prodotto int not null,
prezzo float not null,
primary key (ID, fornitore),
foreign key (fornitore) references Fornitore(nome) on update cascade,
foreign key (prodotto) references Prodotto(codice) on update cascade
);

insert into Account
values	("vincenzo.offertucci@gmail.com", "Vincenzo", "Offertucci", "2000-10-31", MD5("canenzo"), "BeCenzo"),
		("christian.gambardella@gmail.com", "Christian", "Gambardella", "2000-12-11", MD5("ilcane"), "UnruhMaker"),
        ("antonio.ruggiero@gmail.com", "Antonio", "Ruggiero", "2001-01-21", MD5("bassotto"), "Cane"),
        ("salvatore.sirica@gmail.com", "Salvatore", "Sirica", "2000-12-23", MD5("parentopoli"), "Parente"),
        ("vittorio.armenante@gmail.com", "Vittorio", "Armenante", "2000-09-11", MD5("konghino"), "Peppe$on"),
        ("giovanni.sicilia@gmail.com", "Giovanni", "Sicilia", "2000-10-31", MD5("sigaretta69"), "GioGio");

insert into Indirizzo
values	(1, "vincenzo.offertucci@gmail.com", "Napoli", "Ottaviano", "Caracelli", "8", "80044"),
		(2, "vincenzo.offertucci@gmail.com", "Milano", "Milano", "Montenapoleone", "27", "20121"),
        (1, "christian.gambardella@gmail.com", "Napoli", "Poggiomarino", "Giovanni Iervolino", "4", "80040"),
        (2, "christian.gambardella@gmail.com", "Salerno", "Scafati", "Cortile Cocco", "3", "80044"),
        (1, "antonio.ruggiero@gmail.com", "Napoli", "Santa Maria La Scala", "Falangone", "11", "80047"),
        (1, "salvatore.sirica@gmail.com", "Salerno", "Sarno", "De 'I Parenti'", "1", "84087"),
        (1, "vittorio.armenante@gmail.com", "Salerno", "Mercato San Severino", "Fenizia e Marotta", "5", "84085"),
        (1, "giovanni.sicilia@gmail.com", "Salerno", "Salerno", "Fratte", "17", "84135");

insert into NumTel
values	("+393358782855", "vincenzo.offertucci@gmail.com"),
		("+393125642855", "christian.gambardella@gmail.com"),
        ("+393125641111", "christian.gambardella@gmail.com"),
        ("+393358782258", "salvatore.sirica@gmail.com"),
        ("+393279782855", "antonio.ruggiero@gmail.com"),
        ("+393336964855", "vittorio.armenante@gmail.com"),
        ("+393312193955", "giovanni.sicilia@gmail.com");

insert into Corriere
values	("DHL", "7 giorni", 7.00),
		("Bartolini", "4 giorni", 10.00);

insert into Deposito (luogo)
values	("San Gennarello"),
		("Mercanto San Severino");

insert into Prodotto (tipo, prezzo, colore, descrizione, marca, modello, imgurl)
values	("t-shirt", 15, "bianco", "Hakuna Matata T-Shirt", "hz", "normale", "hakunamatata.jpg"),
		("t-shirt", 15, "bianco", "Aereoplano T-Shirt", "hz", "normale", "aereoplano.jpg"),
		("t-shirt", 15, "nero", "Harry Potter T-Shirt", "hz", "normale", "harrypotter.jpg"),
		("t-shirt", 15, "bianco", "Aquarius T-Shirt", "hz", "normale", "acquario.jpg"),
		("t-shirt", 15, "bianco", "McDonalds T-Shirt", "hz", "normale", "mcdonald.jpg"),
		("t-shirt", 15, "nero", "Pavone T-Shirt", "hz", "normale", "pavone.jpg"),
		("t-shirt", 15, "blue", "Rock 'n Roll T-Shirt", "hz", "normale", "rocknroll.jpg"),
        ("felpa", 25, "beige", "Rosa Felpa", "hz", "hoodie", "rosa-felpa-beige.jpg"),
        ("felpa", 25, "grigio", "Live Ride Felpa", "hz", "hoodie", "live.jpg"),
        ("borsello", 8, "bianco", "Coffee Lover", null, null, "coffee-borsello.jpg"),
        ("borsello", 8, "bianco", "Renna", null, null, "renna-borsello.jpg"),
        ("borsello", 8, "bianco", "Rosa", null, null, "rosa-borsello.jpg"),
        ("cappello", 8, "nero", "Cloud", null, null, "cloud-cap.jpg"),
        ("cappello", 8, "nero", "Moon", null, null, "moon-cap.jpg"),
        ("shopper", 5, "bianca", "Disney Shopper", null, null, "disney-shopper.jpg"),
        ("shopper", 5, "bianca", "Peas Shopper", null, null, "peas-shopper.jpg"),
        ("grembiule", 5, "bianca", "Cucina Bene Grembiule", null, null, "cucina-grembiule.jpg"),
        ("grembiule", 5, "bianca", "Dogs Grembiule", null, null, "dogs-grembiule.jpg");
        
insert into Taglie (prodotto, taglia)
values  (1, "S"),
		(1, "M"),
		(1, "L"),
		(2, "S"),
		(2, "M"),
		(2, "L"),
		(3, "S"),
		(3, "M"),
		(3, "L"),
		(4, "S"),
		(4, "M"),
		(4, "L"),
		(5, "S"),
		(5, "M"),
		(5, "L"),
		(6, "S"),
		(6, "M"),
		(6, "L"),
		(7, "S"),
		(7, "M"),
		(7, "L"),
		(8, "S"),
		(8, "M"),
		(8, "L"),
		(9, "S"),
		(9, "M"),
		(9, "L");

insert into Conservato 
values	(1, 1, "S", 15),
		(2, 1, "M", 30),
        (2, 2, "S", 40),
        (1, 3, "L", 12),
        (1, 4, "S", 27),
        (2, 3, "S", 20);

insert into Fornitore
values	("Giuseppe Armenante"),
		("Nonna Franca");

insert into Fornitura (fornitore, data, quantità, prodotto, prezzo)
values 	("Giuseppe Armenante", current_timestamp(), 10, 4, 25.00),
		("Giuseppe Armenante", "2020-05-31 12:51:04", 20, 3, 100.00),
        ("Giuseppe Armenante", "2021-01-28 16:30:22", 20, 1, 200.00);

insert into Ordine(data, prezzo, costoSped, note, stato, email, indirizzo, corriere)
values	("2020-11-30 09:15:54", 63, 7, null, "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "DHL"),
		("2021-01-11 15:37:14", 30, 10, null, "Contabilizzato", "christian.gambardella@gmail.com", 1, "Bartolini"),
        ("2020-07-27 12:22:05", 36, 7, null, "Contabilizzato", "vincenzo.offertucci@gmail.com", 2, "DHL"),
        ("2020-10-15 16:57:26", 24, 10, null, "Contabilizzato", "salvatore.sirica@gmail.com", 1, "Bartolini"),
        ("2021-01-22 11:31:18", 47, 7, null, "Annullato", "giovanni.sicilia@gmail.com", 1, "DHL"),
        (current_timestamp(), 5, 10, null, "Contabilizzato", "vincenzo.offertucci@gmail.com", 1, "Bartolini");

insert into Relato
values	(1, "S", 1, 3),
		(2, "S", 1, 3),
        (1, "M", 2, 2),
        (2, "L", 3, 2),
        (3, "S", 3, 3),
        (3, "M", 4, 3),
        (4, "S", 5, 4),
        (2, "S", 5, 2),
        (1, "L", 5, 1),
		(4, "S", 6, 1);


insert into fattura (data, ammontare, modalità, ordine)
values	("2020-11-30 10:15:54", 70, "Paypal", 1),
		("2021-01-11 16:37:14", 40, "Paypal", 2),
        ("2020-07-27 13:22:05", 43, "Paypal", 3),
        ("2020-10-15 17:57:26", 34, "Paypal", 4),
        (current_timestamp(), 15, "Paypal", 4);

