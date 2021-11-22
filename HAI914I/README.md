# HAI914I - Gestion des données au delà de SQL (NoSQL)

## Liens

🔗 [Moodle](https://moodle.umontpellier.fr/course/view.php?id=22507)

## Préambule

### Quand passer au NoSQL ?

* évolution fréquent de schémas
  * entités ayant diverses carac. souvent non renseignées
  * nombreuses associations [1..*] aux extrémités
* lecture/écriture très élevé
* données distribuées

### Grands principes du NoSQL

* Simplicité
* Flexibilité
* Efficacité
* Passage à l'échelle
  * partitionnement dynamique
  * réplication à large échelle
  * architecture décentralisée

### Principe CAP

👉 Constat de Brewer: aucun système distribué ne peut satisfaire en même temps les principes C, A et P (au mieux 2 sur les 3)

* Consistency (cohérence)
* Availability (disponibilité)
* Partition tolerance (recouvrement des nœuds)

![Positionnement des systèmes / CAP](assets/pos_sys_CAP.png)

### Typologie

* Principe de base
  * Système clé/valeur &rarr; aucune complexité  
  * Système orienté colonne &rarr; complexe  
  * Système orienté document &rarr; complexité dans le document.
* Système orienté graphe

## Cypher & Neo4J

👉 Schema-less ([voir diapo 36](cours/cours1.pdf))

👉 Clauses principale & exemple Cypher ([voir diapo 41 à 57](cours/cours1.pdf)).