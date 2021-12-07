/**
 * HAI916 - TP Prolog
 * Description: Mise en place du système de banque vu en cours
 * Author: Canta Thomas
 * Copyright : 2021 Ⓒ
 */

% ----------------------------------------------------------------
% Classes

persone("Personne", vrai).
compte("Compte", vrai).
courant("Courant", faux).
epargne("Epargne", faux).
client("Client", faux).
employe("Employe", faux).
responsable("Responsable", faux).

% ----------------------------------------------------------------
% Enumeration

enumeration("Epargne", "livretJeune").
enumeration("Epargne", "livretA").
enumeration("Epargne", "cagnotte").
enumeration("Epargne", "epargneSante").
enumeration("Epargne", "epargneEnfant").


% ----------------------------------------------------------------
% Attributs

attribut(prenom, "EString", "Personne", 0, 1).
attribut(age, "EInt", "Personne", 0, 1).
attribut(nom, "EString", "Personne", 0, 1).
attribut(adresse, "EString", "Personne", 0, 1).

attribut(numCompte, "EString", "Compte", 0, 1).
attribut(dateOuverture, "EDate", "Compte", 0, 1).
attribut(solde, "EInt", "Compte", 0, 1).
attribut(soldeMin, "EInt", "Compte", 0, 1).
attribut(soldeMax, "EInt", "Compte", 0, 1).

attribut(numClient, "EString", "Client", 0, 1).
attribut(agenceRattachement, "EString", "Client", 0, 1).

attribut(type, "Epargne", "Epargne", 0, 1).

attribut(idEmploye, "EString", "Employe", 0, 1).
attribut(salaire, "EDouble", "Employe", 0, 1).

attribut(estDG, "EBoolean", "Responsable", 0, 1).
attribut(_, _, Class, _, _) :- classe(Class, _).

% ----------------------------------------------------------------
% Paramètre 

parametre(somme, "EInt", "debiter").
parametre(somme, "EInt", "payer").

% ----------------------------------------------------------------
% Opération

operation(debiter, _, "Compte", 0, 1).
operation(payer, _, "Compte", 0, 1).


% ----------------------------------------------------------------
% Associations

asociation(detient, "Client", "Compte", 0, *, detenuPar).
asociation(detenuPar, "Compte", "Client", 1, 1, detient).

asociation(accedeA, "Client", "Compte", 0, *, authorisePour).
asociation(authorisePour, "Compte", "Client", 1, *, accedeA).

asociation(parentDe, "Client", "Client", 0, *, enfantDe).
asociation(enfantDe, "Client", "Client", 0, 2, parentDe).

asociation(conseillePar, "Client", "Employe", 1, 1, conseille).
asociation(conseille, "Employe", "Client", 0, *, conseillePar).

asociation(directeur, "Employe", "Responsable", 1, *, responsableDe).
asociation(responsableDe, "Responsable", "Employe", 1, *, directeur).
association(_, ClassSource, ClassCible, _, _, _) :- classe(ClassSource, _), classe(ClassCible, _).


% ----------------------------------------------------------------
% Heritage

heritage("Client", "Personne").
heritage("Employe", "Personne").

heritage("Courant", "Compte").
heritage("Epargne", "Compte").

heritage("Responsable", "Employe").
heritage(SClass, SupClass) :- classe(SClass, _), class(SupClass, _).


% ----------------------------------------------------------------
% De la magie noire

attrHerite(NomAttr, SClass, SupClass) :- heritage(SClass,SupClass), attribut(NomAttr, _, SupClass, _, _).
assoHerite(NomAsso, SClass, SupClass) :- association(NomAsso, SupClass, _, _, _, _), heritage(SClass, SupClass).
opHerite(NomOp, SClass, SupClass) :- heritage(SClass,SupClass), operation(NomOp, _, SupClass, _, _).
cycleDirect(NomAsso, Class) :- association(NomAsso, Class, Class, _, _, _).
cycleIndirect(NomAsso, SClass, SupClass) :- association(NomAsso, SClass, SupClass, _, _, _), heritage(SClass, SupClass).
chemins(ClassSource, ClassCible, assoA, assoB) :- association(assoA, ClassSource, ClassCible, _, _, _). association(assoB, ClassSource, ClassCible, _, _, _).


% ----------------------------------------------------------------
% Command to exexute to test:
% attrHerite(A,B,C).
% assoHerite(A,B,C).
% opHerite(A,B,C).
% cycleDirect(A,B).
% cycleIndirect(A,B,C).
% chemins(A,B,C,D).
% findall([A,B,C], attrHerite(A,B,C), Z).

% Impr le résultat dans la console et sauvegarde dans un fichier texte (dans Documents sur windows)
% tell('attrHerite.txt').findall([A,B,C], attrHerite(A,B,C), Z), told.

% NB: association() à des problèmes



