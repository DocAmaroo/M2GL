# HAI914I - TD1

## Question 4

👉 Compter le nombre de départements de la région OCCITANIE

```neo4j
MATCH (d:Departement)-[:WITHIN]->(occi:Region {name:'OCCITANIE'})
RETURN count(*) as nbDepartement
```

<hr/>

👉 Compter le nombre de départements par région et renvoyer le nom de région et le nombre de départements associés à ces régions

```neo4j
MATCH (d:Departement)-[:WITHIN]->(r:Region)
WITH r, count(*) as nbDepartement
RETURN r.name as Nom, nbDepartement
```
<hr/>

👉 Compter le nombre de départements par région (quand la région en compte plus de 6) et renvoyer le nom de région et le nombre de départements associés à ces régions

```neo4j
MATCH (d:Departement)-[:WITHIN]->(r:Region)
WITH r, count(*) as nbDepartement
WHERE nbDepartement > 6
RETURN r.name as Nom, nbDepartement
```
<hr/>

👉 Retourner le nom des communes ainsi que le nom de leur département, quand ces communes sont voisines de MONTPELLIER


<hr/>

👉 Retourner les communes (nom et codeinsee) qui sont voisines de communes voisines de MONTPELLIER et qui ne sont pas dans l’HERAULT

<hr/>

👉 Retourner le nom de chaque commune ainsi que le nom de sa région et de son département de rattachement


<hr/>

👉 Retourner tous les chemins qui relient MONTPELLIER à NIMES


<hr/>

👉 Retourner le plus court chemin (ou un des plus courts chemins) entre MONTPELLIER et NIMES