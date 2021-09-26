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

```neo4j
MATCH (c:Commune)-[:NEARBY]->(mtp:Commune {name: 'MONTPELLIER'})
MATCH (c)-[:WITHIN]->(d:Departement)
RETURN c.name as NomCommune, d.name as NomDepartement
```

<hr/>

👉 Retourner les communes (nom et codeinsee) qui sont voisines de communes voisines de MONTPELLIER et qui ne sont pas dans l’HERAULT

```neo4j
MATCH (c:Commune)-[:NEARBY*1..2]-(mtp:Commune {name: 'MONTPELLIER'})
MATCH (c)-[:WITHIN]->(d:Departement)
WHERE not d.name = "HERAULT"
RETURN distinct c.name as NomCommune, d.name as NomDepartement
```

<hr/>

👉 Retourner le nom de chaque commune ainsi que le nom de sa région et de son département de rattachement


```neo4j
MATCH (c:Commune)-[:WITHIN]->(d:Departement)
MATCH (d:Departement)-[:WITHIN]->(r:Region)
RETURN c.name as Commune, d.name as Departement, r.name as Region ORDER BY c.name
```

<hr/>

👉 Retourner tous les chemins qui relient MONTPELLIER à NIMES

```neo4j
MATCH p = (c1:Commune {name:"NIMES"}) -[n:NEARBY*]- (c2:Commune {name:"MONTPELLIER"})
RETURN p
```

<hr/>

👉 Retourner le plus court chemin (ou un des plus courts chemins) entre MONTPELLIER et NIMES

```neo4j
MATCH p=shortestPath( (m:Commune {name: 'MONTPELLIER'})-[:NEARBY*..25]-(n:Commune {name: 'NIMES'}) )
RETURN length(p) as CheminLePlusCourt
```