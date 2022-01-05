# Résumé de l'article

## Introduction

* Réseau maillé sans fil (Wireless Mesh Network) &rarr; réseau ad-hoc doté d'une infrastructure réseau fixe

WMN composé de:
* Stations de bases
* Un réseau ad-hoc sans fil entre les stations de base. (backbone)
* Stations mobiles

L'infrastructure réseau fixe possède:
* Accès au réseau sans-fil des stations mobiles dans une zone finis à trois dimensiosn (service area)

Les stations mobiles se déplacent et communiquent avec les autres stations WMN en utilisant un protocol *multi-hop*

## Couverture radio

S'assure que les stations mobiles peuvent accéder à l'infrastructure réseau. Notamment par une couverture complète de la zone de service via les stations de bases.

## Connectivité

Assure que toutes les stations de bases peuvent communiquer entres elles (voir graphe avec les liens)

## Problème d'environnement

* Ils sont imprévisibles
* 1er contribution: fault-tolerance pour garantir la couverture radio
* 2eme contribution: optimisation de l'emplacement des stations de bases

## Fault-tolerance approach

* Permet d'éviter les échecs système en effectuant une récupération avant que les erreurs n'entraînent des défaillances.

### Erreur de couverture radio

Une défaillance est détecté quand l'atténuation de la propagation radio est augmenté, cela signifie qu'un ou plusieurs services de locations ne sont couverts par le signal.

### Erreur de connectivité

Pour détecter les erreurs de connectivité il est utilisé un test de biconnectivité sur la couche de routage. Si le graphe n'est pas biconnecté, il y a une erreur. Les stations de base surveillent l'état des liens du réseau en échangeant des messages de contrôle avec d'autres stations de base. L'un d'eux envoie des messages de contrôle et l'autre détermine l'état de la liaison sur la base d'une statistique sur les messages reçus. Les informations sur l'état des liaisons sont périodiquement mises à jour et communiquées afin que le dispositif de gestion effectue des tests	 de biconnectivité.

## Restauration du système

* Mise en place d'un algorithme permettant de donnée le minimum de stations de bases à ajoutées et à quel endroit elles doivent être installée afin de restaurer à la fois la couverture radio ainsi que la connectivité.
* Problème NP-Complet, il faut donc trouver un compromis entre minimalité et temps d'exécution.


Leur algo fonctionne en trois étapes:
1. optimisation &rarr; répare la couche radio en minimisant les conditions de connectivité.
2. test de connectivité &rarr; vérifie la biconnectivité. Si c'est bon l'algo s'arrête, sinon passe à l'étape suivante.
3. consolidation du graphe &rarr; retire certaines biconnection en une unique arêtes et recommence à l'étape 1.

