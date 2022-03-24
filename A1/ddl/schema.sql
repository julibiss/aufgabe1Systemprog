CREATE TABLE Patient(
    ID int  PRIMARY KEY ,
    vorname varchar NOT NULL,
    nachname varchar NOT NULL,
    geburtsdatum Date,
    krankenkasse varchar,
    versicherungsnummer int
);

CREATE TABLE Station(
    ID int PRIMARY KEY ,
    bezeichnung varchar NOT NULL,
    bettenzahl int NOT NULL
);


CREATE TABLE Aufenthalt(
    ID int PRIMARY KEY ,
    P_ID int,
    S_ID int,
    FOREIGN KEY (P_ID) REFERENCES Patient(ID),
    FOREIGN KEY (S_ID) REFERENCES Station(ID)
);

CREATE TABLE Arzt(
    ID int PRIMARY KEY,
	vorname varchar,
	nachname varchar,
	geburtsdatum Date,
	akGrad varchar,
    V_ID int REFERENCES Arzt(ID)
);

CREATE TABLE Pflegekraft(
	ID int PRIMARY KEY,
	vorname varchar,
	nachname varchar,
	geburtsdatum Date,
	PF_ID int REFERENCES Pflegekraft(ID)
);

CREATE TABLE Abteilung(
	ID int PRIMARY KEY,
	S_ID int REFERENCES Station(ID),
	A_ID int REFERENCES Arzt(ID),
	PF_ID int REFERENCES Pflegekraft(ID),	
	bezeichnung varchar NOT NULL
);

CREATE TABLE Untersuchungsergebnis(

);

CREATE TABLE Untersuchungsverfahren(
	ID int PRIMARY KEY,
	bezeichnung varchar NOT NULL,
	koerperregion varchar NOT NULL
);

CREATE TABLE Klinischesuntersuchungsverfahren(
	ID INT PRIMARY KEY,
	bezeichnung varchar NOT NULL,
	koerperregion varchar NOT NULL
) inherits(Untersuchungsverfahren);

CREATE TABLE Apparativesuntersuchungsverfahren(
	ID INT PRIMARY KEY,
	bezeichnung varchar NOT NULL
) inherits(Untersuchungsverfahren);

CREATE TABLE Labortest(
	ID INT PRIMARY KEY,
	probenart varchar NOT NULL,
	normwert int  CHECK (normwert <= 1.5 AND normwert >=0.5)
) inherits(Untersuchungsverfahren);

