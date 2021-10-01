# HAI914 - TP2

## 🚩 Exercices après enrichissement

### Question 1

```neo4j
match (p:Personne) -[:ADMINISTREE_PAR]- (c:Commune)
SET p :Maire
RETURN p
```

<details><summary>Correction du prof</summary>

```neo4j
%MATCH (p:Personne) <-[:ADMINISTREE_PAR]- (c:Commune) SET p:Maire
```
</details>


### Question 2

```neo4j
CALL dbms.procedures()
CALL dbms.functions()
```

### Question 3

```neo4j
CALL apoc.export.json.query("MATCH (a:Commune) -[w:WITHIN]- (d:Departement) -[]- (r:Region)
RETURN id(a), labels(a), a.name, d.name, r.name", "test.json", {} )
```
👉 Le fichier `test.json` se trouve à dans: `~/neo4j_folder/import/test.json`

👉 Pour ouvrir avec un éditeur de graphe.

```neo4j
CALL apoc.export.graphml.all("test.xml", {})
```

## 🚩 Utilisation du plugin Neosemantics

### Question 1

```neo4j
:GET /rdf/onto
```

### Question 2

👉 On récupère l'ID de Montpellier

```neo4j
MATCH (c:Commune {name: "MONTPELLIER"}) return ID(c)
```

👉 On l'exporte au format RDF

```neo4j
:GET /rdf/describe/id/59
// ou
:GET /rdf/describe/id/59?format=RDF/XML&excludeContext=true
```

<details><summary>Voir le résultat</summary>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:neovoc="neo4j://vocabulary#"
	xmlns:neoind="neo4j://individuals#">

<rdf:Description rdf:about="neo4j://individuals#59">
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.876716</neovoc:longitude>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.610769</neovoc:latitude>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34172</neovoc:codeinsee>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:name>MONTPELLIER</neovoc:name>
</rdf:Description>

</rdf:RDF>
```

</details>

### Question 3

```neo4j
:POST /rdf/cypher {"cypher": "MATCH (c:Commune {name: 'MONTPELLIER'}) RETURN c", "format":"N3"}
// ou
:POST /rdf/cypher {"cypher": "MATCH (c:Commune {name: 'MONTPELLIER'}) RETURN c", "format":"RDF/XML"}
```

<details><summary>Voir le résultat</summary>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:neovoc="neo4j://vocabulary#"
	xmlns:neoind="neo4j://individuals#">

<rdf:Description rdf:about="neo4j://individuals#59">
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.876716</neovoc:longitude>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.610769</neovoc:latitude>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34172</neovoc:codeinsee>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:name>MONTPELLIER</neovoc:name>
</rdf:Description>

</rdf:RDF>
```

</details>

👉 Le résultat est équivalent.

### Question 4

```neo4j
:POST /rdf/cypher {"cypher": "MATCH (c:Commune {name: 'MONTPELLIER'}) -[:ADMINISTREE_PAR]- (m:Maire) RETURN m", "format":"RDF/XML"}
```

<details><summary>Voir le résultat</summary>
    
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:neovoc="neo4j://vocabulary#"
	xmlns:neoind="neo4j://individuals#">

<rdf:Description rdf:about="neo4j://individuals#198">
	<neovoc:prenom>GEORGES</neovoc:prenom>
	<neovoc:nom>FRECHE</neovoc:nom>
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#199">
	<neovoc:prenom>HELENE</neovoc:prenom>
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
	<neovoc:nom>MANDROUX</neovoc:nom>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#200">
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<neovoc:prenom>PHILIPPE</neovoc:prenom>
	<neovoc:nom>SAUREL</neovoc:nom>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#201">
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<neovoc:prenom>MICKAEL</neovoc:prenom>
	<neovoc:nom>DELAFOSSE</neovoc:nom>
</rdf:Description>

</rdf:RDF>
```

</details>

### Question 5

```neo4j
:POST /rdf/cypher {"cypher": "MATCH (c:Commune {name: 'MONTPELLIER'}) -[]- (m)  RETURN c,m", "format":"RDF/XML"}
```

<details><summary>Voir le résultat</summary>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:neovoc="neo4j://vocabulary#"
	xmlns:neoind="neo4j://individuals#">

<rdf:Description rdf:about="neo4j://individuals#198">
	<neovoc:prenom>GEORGES</neovoc:prenom>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#59">
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.876716</neovoc:longitude>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.610769</neovoc:latitude>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34172</neovoc:codeinsee>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#198">
	<neovoc:nom>FRECHE</neovoc:nom>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#59">
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#198">
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#59">
	<neovoc:name>MONTPELLIER</neovoc:name>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#199">
	<neovoc:prenom>HELENE</neovoc:prenom>
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
	<neovoc:nom>MANDROUX</neovoc:nom>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#201">
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<neovoc:prenom>MICKAEL</neovoc:prenom>
	<neovoc:nom>DELAFOSSE</neovoc:nom>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#200">
	<rdf:type rdf:resource="neo4j://vocabulary#Personne"/>
	<rdf:type rdf:resource="neo4j://vocabulary#Maire"/>
	<neovoc:prenom>PHILIPPE</neovoc:prenom>
	<neovoc:nom>SAUREL</neovoc:nom>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#34">
	<neovoc:name>HERAULT</neovoc:name>
	<neovoc:id>34</neovoc:id>
	<rdf:type rdf:resource="neo4j://vocabulary#Departement"/>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#3">
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.567296</neovoc:latitude>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34129</neovoc:codeinsee>
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.896473</neovoc:longitude>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:name>LATTES</neovoc:name>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#1">
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34169</neovoc:codeinsee>
	<neovoc:name>MONTFERRIER-SUR-LEZ</neovoc:name>
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.859265</neovoc:longitude>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.671824</neovoc:latitude>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#92">
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34077</neovoc:codeinsee>
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.887951</neovoc:longitude>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.657266</neovoc:latitude>
	<neovoc:name>CLAPIERS</neovoc:name>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#5">
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.656681</neovoc:latitude>
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.9693</neovoc:longitude>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34327</neovoc:codeinsee>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:name>VENDARGUES</neovoc:name>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#51">
	<neovoc:name>LUNEL</neovoc:name>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34145</neovoc:codeinsee>
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">4.135366</neovoc:longitude>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.67445</neovoc:latitude>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#2">
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.761729</neovoc:longitude>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34202</neovoc:codeinsee>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.5819029</neovoc:latitude>
	<neovoc:name>PIGNAN</neovoc:name>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#0">
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:name>JUVIGNAC</neovoc:name>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34123</neovoc:codeinsee>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.611297</neovoc:latitude>
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.812357</neovoc:longitude>
</rdf:Description>

<rdf:Description rdf:about="neo4j://individuals#91">
	<neovoc:longitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">3.911453</neovoc:longitude>
	<neovoc:codeinsee rdf:datatype="http://www.w3.org/2001/XMLSchema#long">34120</neovoc:codeinsee>
	<neovoc:latitude rdf:datatype="http://www.w3.org/2001/XMLSchema#double">43.660874</neovoc:latitude>
	<rdf:type rdf:resource="neo4j://vocabulary#Commune"/>
	<neovoc:name>JACOU</neovoc:name>
</rdf:Description>

</rdf:RDF>
```

</details>

## 🚩 Questions d’appropriation

**Partie 1**

### Question 1

La requête CONSTRUCT permet de construire un graphe avec les données (name, idInsee, etc...) via les données disponible sur Wikidata qui permet de retourner différentes informations sur des communes (ici le concept City est emprunté au vocabulaire schema.org). 

### Question 2

```neo4j
MATCH (sch:sch__City) 
MATCH (h:Departement {name:"HERAULT"})
CREATE (sch) -[:WITHIN]-> (h)
```

### Question 3

```neo4j
MATCH (c:Commune) DETACH DELETE c
```

### Question 4

```neo4j
MATCH (n:sch__City {sch__name:"Montpellier"}) -[:sch__geoTouches]- (m:sch__City)
RETURN count(distinct m) as limitrophes
```

<hr>

**Partie 2**

## Question 1

```neo4j
MATCH n = shortestpath((:sch__City {sch__name:'Montpellier'}) -[:sch__geoTouches*]-> (:sch__City {sch__name:'Beaulieu'}))
RETURN n
```

## Question 2

```neo4j
MATCH n = allshortestpaths((:sch__City {sch__name:'Montpellier'}) -[:sch__geoTouches*]-> (:sch__City {sch__name:'Beaulieu'}))
RETURN n
```
## Question 3

```neo4j
MATCH n = allshortestpaths((a:sch__City {sch__name:'Montpellier'}) -[b:sch__geoTouches*]-> (c:sch__City {sch__name:'Beaulieu'}))
RETURN extract (c in nodes(n) | {sch__name:c.sch__name}) as Communes
```

## Question 4

```neo4j
MATCH n = allshortestpaths((a:sch__City {sch__name:'Montpellier'}) -[b:sch__geoTouches*]-> (c:sch__City {sch__name:'Beaulieu'}))
RETURN extract (c in nodes(n) | {sch__name:c.sch__name, sch__address:c.sch__address}) as Communes
```

## Question 5

```neo4j
MATCH n = allshortestpaths((a:sch__City {sch__name:'Montpellier'}) -[b:sch__geoTouches*]-> (c:sch__City {sch__name:'Beaulieu'}))
RETURN count(n)
```

## Question 6

Limiter le nombre de chemin que le noeud peut parcourir

```neo4j
MATCH n = ((a:sch__City {sch__name:'Montpellier'}) -[b:sch__geoTouches*]-> (c:sch__City {sch__name:'Beaulieu'}))
RETURN n LIMIT 10
```
## Question 7

```neo4j
MATCH s = shortestpath((:sch__City {sch__name:'Montpellier'}) -[:sch__geoTouches*]-> (:sch__City {sch__name:'Beaulieu'}))
WHERE NOT ('Clapiers' IN (EXTRACT (n in nodes(s) | n.sch__name)))
RETURN s
```
