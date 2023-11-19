# kurzAufenthalt_Datenbank_Projekt
#Test_Daten

-- Tabellen und ihre Abhängigkeiten löschen, falls vorhanden
DROP TABLE T_Nachrichten CASCADE CONSTRAINTS;
DROP TABLE T_Zahlungen CASCADE CONSTRAINTS;
DROP TABLE T_Bewertung CASCADE CONSTRAINTS;
DROP TABLE T_Buchung CASCADE CONSTRAINTS;
DROP TABLE T_Wohnung CASCADE CONSTRAINTS;
DROP TABLE T_Benutzer CASCADE CONSTRAINTS;

-- Benutzer-Tabelle 
CREATE TABLE T_Benutzer (
    BenutzerID INTEGER PRIMARY KEY,
    NachName VARCHAR(35) NOT NULL,
    VorName VARCHAR(35) NOT NULL,
    anrede CHAR(4) CHECK (anrede IN ('FRAU','HERR')),
    Benutzername VARCHAR(255) NOT NULL,
    EMail VARCHAR(255) NOT NULL CHECK (EMail LIKE '%@%.%'), 
    Passwort VARCHAR(255) NOT NULL,
    strasse VARCHAR(100),
    ort VARCHAR(100), 
    plz NUMBER(5),
    Geburtsdatum DATE NOT NULL,
    ReferenzBenutzerID INT,
    Verifiziert CHAR(1) CHECK (Verifiziert IN ('J','N')),
    FOREIGN KEY (ReferenzBenutzerID) REFERENCES T_Benutzer(BenutzerID)
);


-- Wohnung-Tabelle
CREATE TABLE T_Wohnung (
    WohnungID INT PRIMARY KEY,
    EigentuemerID INT,
    strasse VARCHAR(100) NOT NULL,
    ort VARCHAR(100) NOT NULL, 
    plz NUMBER(5) NOT NULL,
    preisProNacht NUMERIC(7,2) NOT NULL,
    Beschreibung VARCHAR(500),
    Verfuegbarkeit CHAR(1) CHECK (Verfuegbarkeit IN ('J','N')) NOT NULL,
    FOREIGN KEY (EigentuemerID) REFERENCES T_Benutzer(BenutzerID) ON DELETE CASCADE
);


-- Buchung-Tabelle
CREATE TABLE T_Buchung (
    BuchungID INT PRIMARY KEY,
    MieterID INT NOT NULL,
    WohnungID INT NOT NULL,
    Buchungsdatum DATE NOT NULL,
    Startdatum DATE NOT NULL,
    Enddatum DATE NOT NULL,
    CHECK ((Enddatum - Startdatum) <= 365),
    CHECK ((Startdatum - Buchungsdatum) <= 90),
    FOREIGN KEY (MieterID) REFERENCES T_Benutzer(BenutzerID) ON DELETE SET NULL,
    FOREIGN KEY (WohnungID) REFERENCES T_Wohnung(WohnungID)
);

-- Bewertung-Tabelle
CREATE TABLE T_Bewertung (
    BewertungID INT PRIMARY KEY,
    BuchungID INT,
    Bewertungstext VARCHAR(500) NOT NULL,
    Sternebewertung INT CHECK (Sternebewertung BETWEEN 1 AND 5),
    FOREIGN KEY (BuchungID) REFERENCES T_Buchung(BuchungID) ON DELETE CASCADE
);

-- Zahlungen-Tabelle
CREATE TABLE T_Zahlungen (
    ZahlungsID INTEGER PRIMARY KEY,
    BuchungID INTEGER NOT NULL,
    Betrag NUMERIC(10, 2) NOT NULL,
    Zahlungsdatum DATE NOT NULL,
    zahlungsart VARCHAR(15) CHECK (zahlungsart in ('VorOrt','Lastschrift','Visa')) NOT NULL,
    FOREIGN KEY (BuchungID) REFERENCES T_Buchung(BuchungID)
);

-- Nachrichten-Tabelle
CREATE TABLE T_Nachrichten (
    NachrichtenID INT PRIMARY KEY,
    SenderID INT,
    EmpfaengerID INT,
    Nachrichtentext VARCHAR(500),
    Zeitstempel TIMESTAMP ,
    FOREIGN KEY (SenderID) REFERENCES T_Benutzer(BenutzerID),
    FOREIGN KEY (EmpfaengerID) REFERENCES T_Benutzer(BenutzerID)
);

INSERT INTO T_Benutzer VALUES (1, 'Müller', 'Max', 'HERR', 'max.mueller', 'max.mueller@example.com', 'max12345', 'Beispielstr. 1', 'Beispielstadt', 12345, '01-01-1990', NULL, 'J');
INSERT INTO T_Benutzer VALUES (2, 'Schmidt', 'Julia', 'FRAU', 'julia.schmidt', 'julia.schmidt@example.com', 'julia6789', 'Musterweg 2', 'Musterstadt', 23456, '02-02-1992', 1, 'N');
INSERT INTO T_Benutzer VALUES (3, 'Fischer', 'Anna', 'FRAU', 'anna.fischer', 'anna.fischer@example.com', 'anna1234', 'Testallee 3', 'Teststadt', 34567, '03-03-1994', 2, 'J');

INSERT INTO T_Wohnung VALUES (1, 1, 'Musterstraße 1', 'Musterstadt', 12345, 50.00, '3 Zimmer, Küche, Bad', 'J');
INSERT INTO T_Wohnung VALUES (2, 2, 'Beispielweg 2', 'Beispielstadt', 23456, 75.00, '2 Zimmer, Küche, Bad', 'N');
INSERT INTO T_Wohnung VALUES (3, 3, 'Testallee 3', 'Teststadt', 34567, 65.00, '4 Zimmer, Küche, Bad, Garten', 'J');

INSERT INTO T_Buchung VALUES (1, 2, 1, '01-01-2023', '01-02-2023', '01-03-2023'); 
INSERT INTO T_Buchung VALUES (2, 3, 2, '15-01-2023', '15-02-2023', '15-04-2023');
INSERT INTO T_Buchung VALUES (3, 1, 3, '01-02-2023', '01-03-2023', '01-04-2023');

INSERT INTO T_Bewertung VALUES (1, 1, 'Sehr sauber und gemütlich.', 5);
INSERT INTO T_Bewertung VALUES (2, 2, 'Gute Lage, aber etwas laut.', 3);
INSERT INTO T_Bewertung VALUES (3, 3, 'Perfekt für Familien.', 4);

INSERT INTO T_Zahlungen VALUES (1, 1, 500.00, '01-02-2023', 'Visa');
INSERT INTO T_Zahlungen VALUES (2, 2, 750.00, '15-03-2023', 'Lastschrift');
INSERT INTO T_Zahlungen VALUES (3, 3, 600.00, '01-04-2023', 'VorOrt');

INSERT INTO T_Nachrichten VALUES (1, 1, 2, 'Können wir den Mietzeitraum anpassen?', '01-01-2023 10:00:00');
INSERT INTO T_Nachrichten VALUES (2, 2, 3, 'Gibt es einen Parkplatz?', '02-02-2023 15:30:00');
INSERT INTO T_Nachrichten VALUES (3, 3, 1, 'Ist die Wohnung noch verfügbar?', '03-03-2023 20:45:00');
COMMIT;
