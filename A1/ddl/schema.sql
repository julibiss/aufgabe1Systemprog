CREATE TABLE Patient(
    ID int  PRIMARY KEY ,
    vorname varchar (50) NOT NULL,
    nachname varchar (30) NOT NULL,
    geburtsdatum Date NOT NULL,
    krankenkasse varchar (50),
    versicherungsnummer int
);

CREATE TABLE Aufenthalt(
    ID int PRIMARY KEY ,
    P_ID int REFERENCES Patient(ID) ON DELETE RESTRICT,
    S_ID int REFERENCES Station(ID) ON DELETE RESTRICT,
    aufnahmedatum date NOT NULL,
    entlassdatum date
);

CREATE TABLE Arzt(
    ID int PRIMARY KEY,
    V_ID int REFERENCES Arzt(ID) ON DELETE RESTRICT,
	S_ID int REFERENCES Station(ID) ON DELETE RESTRICT,
    AB_ID int REFERENCES Abteilung(ID) ON DELETE RESTRICT,
    vorname varchar (50) NOT NULL,
    nachname varchar (30) NOT NULL,
    geburtsdatum Date NOT NULL,
    akGrad varchar (20) NOT NULL
);

CREATE TABLE Pflegekraft(
    ID int PRIMARY KEY,
	PF_ID int REFERENCES Pflegekraft(ID) ON DELETE RESTRICT,
	S_ID int REFERENCES Station(ID) ON DELETE RESTRICT,
    vorname varchar NOT NULL,
    nachname varchar NOT NULL,
    geburtsdatum Date NOT NULL
);

CREATE TABLE Abteilung(
    ID int PRIMARY KEY,
    bezeichnung varchar (15) NOT NULL
);

CREATE TABLE Station(
    ID int PRIMARY KEY ,
	AB_ID int REFERENCES Abteilung(ID) ON DELETE RESTRICT,
    bezeichnung varchar (15) NOT NULL,
    bettenzahl int NOT NULL CHECK (bettenzahl > 0)
);

CREATE TABLE Untersuchungsverfahren(
    ID int PRIMARY KEY,
	U_ID int REFERENCES Untersuchungsverfahren(ID) ON DELETE RESTRICT,
    bezeichnung varchar (30) NOT NULL,
    koerperregion varchar (30) NOT NULL
);

CREATE TABLE Klinischesuntersuchungsverfahren(
    ID INT PRIMARY KEY,
	FOREIGN KEY (ID) REFERENCES Untersuchungsverfahren(ID) ON DELETE CASCADE,
    bezeichnung varchar (30) NOT NULL,
    koerperregion varchar (30) NOT NULL
) inherits(Untersuchungsverfahren);

CREATE TABLE Apparativesuntersuchungsverfahren(
    ID INT PRIMARY KEY,
	FOREIGN KEY (ID) REFERENCES Untersuchungsverfahren(ID) ON DELETE CASCADE,
    bezeichnung varchar (30) NOT NULL
) inherits(Untersuchungsverfahren);

CREATE TABLE Labortest(
    ID INT PRIMARY KEY,
	FOREIGN KEY (ID) REFERENCES Untersuchungsverfahren(ID) ON DELETE CASCADE,	
    probenart varchar (30) NOT NULL,
    normwert int  CHECK (normwert <= 1.5 AND normwert >=0.5)
) inherits(Untersuchungsverfahren);

CREATE TABLE Untersuchungsergebnis(
    ID int PRIMARY KEY,
    UN_ID int REFERENCES Untersuchungsverfahren(ID) ON DELETE CASCADE,
	B_ID int REFERENCES Befund(ID) ON DELETE RESTRICT,
    ergebniszusammenfassung varchar,
    anforderungsdatum date NOT NULL,
    ergebnisdatum date
);

CREATE TABLE Diagnose(
    ID int PRIMARY KEY,
    icdcode int NOT NULL,
    diagnosetext varchar
);

CREATE TABLE Befund(
    ID int PRIMARY KEY,
    A_ID int REFERENCES Arzt(ID) ON DELETE RESTRICT,
    P_ID int REFERENCES Patient(ID) ON DELETE RESTRICT,
    datum date NOT NULL,
    zusammenfassung varchar
);

CREATE TABLE Diagnoseliste(
	B_ID int REFERENCES Befund(ID) ON DELETE RESTRICT,
	D_ID int REFERENCES Diagnose(ID) ON DELETE RESTRICT
)
