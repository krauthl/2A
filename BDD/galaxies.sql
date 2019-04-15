CREATE TABLE Vaisseau(
  noVaisseau int primary key not null,
  rayonAction int,
  nbPilotes int check (nbPilotes>0),
  vitesseMax int check (vitesseMax>0)
);

CREATE TABLE VaisseauCombat(
  noVaisseau int primary key not null,
  tailleEquipMin int,
  tailleEquipMax int
);

CREATE TABLE VaisseauTransport(
  noVaisseau int primary key not null,
  capacite int
);

CREATE TABLE Pilote(
  noPilote int,
  nomPilote varchar(50),
  agePilote int,
  grade varchar(50)
);

CREATE TABLE TypeMission(
  nomMission varchar(50) primary key
);

CREATE TABLE Galaxie(
  noGalaxie int primary key,
  nomGalaxie varchar(50),
  distance int check(distance>0)
);

CREATE TABLE Composition(
  milieu varchar(50) primary key
);

CREATE TABLE Equipage(
  noEquipage int primary key,
  nbPersonnes int check(nbPersonnes>=0)
);

CREATE TABLE Mission(
  noMission int primary key,
  dateMission int,
  noVaisseau int,
  vitesseMin int check(vitesseMin>0),
  rayonEngagement int
);

CREATE TABLE Planete(
  noGalaxie int,
  noPlanete int,
  nomPlanete varchar(50),
  vitesseLiberation int check(vitesseLiberation>0),
  soumise char(1),
  milieu varchar(50),
  primary key(noGalaxie, noPlanete)
);

CREATE TABLE Cible(
  noMission int,
  noPlanete int,
  noGalaxie int
);

CREATE TABLE Resiste(
  noVaisseau int primary key,
  milieu varchar(50)
);

CREATE TABLE FormationPilote(
  noPilote int not null,
  nomFormation varchar(50) not null,
  primary key(noPilote, nomFormation)
);

CREATE TABLE FormationEquipe(
  noEquipe int not null primary key,
  nomFormation varchar(50)
);

CREATE TABLE AffectationPilote(
  milieu varchar(50) not null,
  noPilote int not null,
  noVaisseau int not null,
  primary key(milieu, noPilote, noVaisseau)
);

CREATE TABLE AffectationEquipe(
  milieu varchar(50) not null,
  noEquipe int not null,
  noVaisseau int not null,
  primary key(milieu, noEquipe, noVaisseau)
);
