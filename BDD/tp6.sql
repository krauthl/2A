--PARTIE 1

--Ex 1 : Annulation de transaction

CREATE TABLE COMPTES(
  NC int primary key not null,
  Nom varchar(50) not null,
  Solde int not null check (Solde>=0)
);

INSERT INTO COMPTES
VALUES(1, 'Paul', 100);

INSERT INTO COMPTES
VALUES(2, 'Paul', 2135456);

SELECT SUM(Solde)
FROM COMPTES;

ROLLBACK;
SELECT SUM(Solde)
FROM COMPTES;

--Ex 2 : Validation de transaction

INSERT INTO COMPTES
VALUES(3, 'Pierre', 5463574854);

INSERT INTO COMPTES
VALUES(4, 'Pierre', 5465);

COMMIT;

SELECT SUM(Solde)
FROM COMPTES
WHERE Nom='Pierre';

SELECT SUM(Solde)
FROM COMPTES
WHERE Nom='Paul';

ROLLBACK;

SELECT SUM(Solde)
FROM COMPTES
WHERE Nom='Pierre'; --est nul !

SELECT SUM(Solde)
FROM COMPTES
WHERE Nom='Paul';

--Ex 3 : Validation automatique
SET AUTOCOMMIT ON;

INSERT INTO COMPTES
VALUES(5, 'Jacques', 74854);

INSERT INTO COMPTES
VALUES(6, 'Jacques', 754);

SELECT SUM(Solde)
FROM COMPTES;

ROLLBACK;

SELECT SUM(Solde)
FROM COMPTES; --La somme reste inchangée !

SET AUTOCOMMIT OFF;

--Ex 4 : Points de sauvegarde

INSERT INTO COMPTES
VALUES(7, 'Jean', 546574854);

INSERT INTO COMPTES
VALUES(8, 'Jean', 546);

SAVEPOINT DeuxInserts;

INSERT INTO COMPTES
VALUES(9, 'Jean', 2);

INSERT INTO COMPTES
VALUES(10, 'Jean', 546546345);

SELECT SUM(Solde)
FROM COMPTES
WHERE Nom='Jean';

ROLLBACK TO DeuxInserts;

SELECT SUM(Solde)
FROM COMPTES
WHERE Nom='Jean'; --Somme inchangée !

ROLLBACK;

SELECT SUM(Solde)
FROM COMPTES
WHERE Nom='Jean'; --Somme nulle !! On est revenu avant l'insertion des 4 comptes de Jean

--PARTIE 2

--Ex 1

INSERT INTO COMPTES
VALUES(11, 'Claude', 100);

INSERT INTO COMPTES
VALUES(12, 'Henri', 200);

--Incrémenter solde du compte de Henri de 50
UPDATE COMPTES
SET Solde = Solde + 50
WHERE Nom = 'Henri'; --OK ça marche

--Décrémenter solde Claude de 150
UPDATE COMPTES
SET Solde = Solde - 150
WHERE Nom = 'Claude'; --Pas possible car le solde du compte doit être >0

SELECT Solde
FROM COMPTES
WHERE Nom = 'Henri';

SELECT Solde
FROM COMPTES
WHERE Nom = 'Claude';

ROLLBACK;

SELECT * FROM COMPTES; --plus aucun comptes à Henri ou Claude

INSERT INTO COMPTES
VALUES(11, 'Claude', 100);

INSERT INTO COMPTES
VALUES(12, 'Henri', 200);

--Incrémenter solde du compte de Henri de 50
UPDATE COMPTES
SET Solde = Solde + 50
WHERE Nom = 'Henri'; --OK ça marche

--Décrémenter solde Claude de 150
UPDATE COMPTES
SET Solde = Solde - 150
WHERE Nom = 'Claude'; --Pas possible car le solde du compte doit être >0

SELECT Solde
FROM COMPTES
WHERE Nom = 'Henri';

SELECT Solde
FROM COMPTES
WHERE Nom = 'Claude';

COMMIT;

--PARTIE 3

--Ex 1 : read commited
