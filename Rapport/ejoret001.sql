drop table PARTIE;
drop table SCOREPARTIE;
drop table RESUMEPARTIE;
drop table JOUEUR;
drop table STATISTIQUE;

CREATE TABLE PARTIE
(
    Code_Partie INT, /*PK*/
    Code_Joueur1 INT, /*FK -> JOUEUR*/
    Code_Joueur2 INT, /*FK -> JOUEUR*/
    Code_Joueur3 INT, /*FK -> JOUEUR*/
    Code_Joueur4 INT, /*FK -> JOUEUR*/
    Code_Joueur5 INT, /*FK -> JOUEUR*/
    Code_Joueur6 INT, /*FK -> JOUEUR*/
    Termine CHAR(1)
);

CREATE TABLE SCOREPARTIE
(
    Code_Partie INT, /*PK composé 1ère clé + FK -> PARTIE*/
    Code_Joueur INT, /*PK composé 2nd clé*/
    Score INT,
    Nb_Suite_G INT,
    Nb_ChouVel_P INT
);

CREATE TABLE RESUMEPARTIE
(
    Code_Partie INT, /*PK composé 1ère clé + FK -> PARTIE*/
    Num_Lance_Des INT, /*PK composé 2nd clé*/
    Des_1 INT,
    Des_2 INT,
    Des_3 INT
);

CREATE TABLE JOUEUR
(
    Code_Joueur INT, /*PK + FK -> STATISTIQUE*/
    Pseudo VARCHAR(30),
    Mdp VARCHAR(40),
    Age INT,
    Sexe CHAR(1),
    Ville VARCHAR(30)
);

CREATE TABLE STATISTIQUE
(
    Code_Joueur INT, /*PK*/
    Nb_Partie INT,
    Nb_Victoire INT,
    Nb_Victoire_Moyenne NUMBER(4,3),
    Nb_Pts_Tot INT,
    Score_Moyen NUMBER(4,3),
    Nb_Suite INT,
    Suite_Moyen_G NUMBER(4,3),
    Nb_ChouVel INT,
    ChouVel_Moyen_P NUMBER(4,3)
);

ALTER TABLE PARTIE ADD CONSTRAINT PK_Code_Partie PRIMARY KEY (Code_Partie);
ALTER TABLE SCOREPARTIE ADD CONSTRAINT PK_SCORE_Code_Partie_Joueur PRIMARY KEY (Code_Partie, Code_Joueur);
ALTER TABLE RESUMEPARTIE ADD CONSTRAINT PK_RESUME_Code_Partie_Num PRIMARY KEY (Code_Partie, Num_Lance_Des);
ALTER TABLE JOUEUR ADD CONSTRAINT PK_Code_Joueur PRIMARY KEY (Code_Joueur);
ALTER TABLE STATISTIQUE ADD CONSTRAINT PK_Code_Joueur_Stat PRIMARY KEY (Code_Joueur);

ALTER TABLE PARTIE ADD CONSTRAINT FK_Code_Joueur1 FOREIGN KEY(Code_Joueur1) REFERENCES JOUEUR(Code_Joueur);
ALTER TABLE PARTIE ADD CONSTRAINT FK_Code_Joueur2 FOREIGN KEY(Code_Joueur2) REFERENCES JOUEUR(Code_Joueur);
ALTER TABLE PARTIE ADD CONSTRAINT FK_Code_Joueur3 FOREIGN KEY(Code_Joueur3) REFERENCES JOUEUR(Code_Joueur);
ALTER TABLE PARTIE ADD CONSTRAINT FK_Code_Joueur4 FOREIGN KEY(Code_Joueur4) REFERENCES JOUEUR(Code_Joueur);
ALTER TABLE PARTIE ADD CONSTRAINT FK_Code_Joueur5 FOREIGN KEY(Code_Joueur5) REFERENCES JOUEUR(Code_Joueur);
ALTER TABLE PARTIE ADD CONSTRAINT FK_Code_Joueur6 FOREIGN KEY(Code_Joueur6) REFERENCES JOUEUR(Code_Joueur);
ALTER TABLE SCOREPARTIE ADD CONSTRAINT FK_SCORE_Code_Partie FOREIGN KEY(Code_Partie) REFERENCES PARTIE(Code_Partie);
ALTER TABLE RESUMEPARTIE ADD CONSTRAINT FK_RESUME_Code_Partie FOREIGN KEY(Code_Partie) REFERENCES PARTIE(Code_Partie);
ALTER TABLE JOUEUR ADD CONSTRAINT FK_Code_Joueur_Stat FOREIGN KEY(Code_Joueur) REFERENCES STATISTIQUE(Code_Joueur);

commit;
