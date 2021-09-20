CREATE (m:Commune {nom:'MONTPELLIER', latitude:43.610769, longitude:3.876716, codeinsee:'34172'}) -[:WITHIN]-> (h:Departement {nom:'HERAULT', numero:'34'}),
(h) <-[:WITHIN]- (l:Commune {nom:'LUNEL', latitude:43.67445, longitude:4.135366, codeinsee:'34145'}), (m) -[:NEARBY]-> (l), (c:Commune {nom:'CLAPIERS', latitude:43.6575, longitude:3.88833, codeinsee:'34830', pop_17:5135}) -[:WITHIN]-> (h), (m) -[:NEARBY {type:'border'}]-> (c)
RETURN l, m, h, c

MATCH (l:Commune {nom:'LUNEL'})
CREATE (m:Commune {nom:'NIMES', latitude:43.836699, longitude:4.360054, codeinsee:'30189'}) -[:WITHIN]-> (h:Departement {nom:'GARD', numero:'30'}), (m) -[:NEARBY]-> (l)

MATCH (h:Departement {nom:'GARD', numero:'30'})
CREATE (m:Commune {nom:'GARONS', latitude:43.770059, longitude:4.424545, codeinsee:'30125'}) -[:WITHIN]-> (h), (m) -[:NEARBY]-> (s:Commune {nom:'SOMMIERES', codeinsee:'30321'}), (s) -[:WITHIN]-> (h) 

MATCH (h:Departement {numero:'34'})
MATCH (m:Commune {nom:'MONTPELLIER'})
CREATE (c:Commune {nom:'MONTFERRIER-SUR-LEZ', latitude:43.671824, longitude:3.859265, codeinsee:'34169',pop_1975:1682}) -[:WITHIN]-> (h), (c) -[:NEARBY]-> (m),(cr:Commune {nom:'CRES', latitude:43.644825, longitude:3.936612, codeinsee:'34090',pop_1975:4507}) -[:WITHIN]-> (h), (cr) -[:NEARBY]-> (m), (ca:Commune {nom:'CASTELNAU-LE-LEZ', latitude:43.634144, longitude:3.897398, codeinsee:'34057',pop_1975:9446}) -[:WITHIN]-> (h), (ca) -[:NEARBY {type:'border'}]-> (m),(cas:Commune {nom:'CASTRIES', latitude:43.677589, longitude:3.985579, codeinsee:'34058',pop_1975:2494}) -[:WITHIN]-> (h), (cas) -[:NEARBY]-> (m), (stc:Commune {nom:'SAINT-CLEMENT-DE-RIVIERE', latitude:43.6844, longitude:3.8472, codeinsee:'34247',pop_1975:845,pop_2010:4985}) -[:WITHIN]-> (h), (stc) -[:NEARBY]-> (m)

MATCH (g:Commune {nom:'GARONS'})
MATCH (n:Commune {nom:'NIMES'})
CREATE (g) -[:NEARBY]-> (n)

MATCH (s:Commune {nom:'SOMMIERES'})
SET s.pop_1975=3072, s.latitude=43.783450, s.longitude=4.089738
RETURN s

MATCH (s:Commune {nom:'SOMMIERES'}) <-[r:NEARBY]- (g:Commune {nom:'GARONS'})
SET r.distance = 27.4

MATCH (d:Departement {nom:'HERAULT'}) <-[p:WITHIN]- (n:Commune)
RETURN d, n, p

MATCH (c1:Commune)-[:NEARBY]->()<-[:NEARBY]-(c2:Commune)
MERGE (c1)-[:NEARBY]-(c2)

// Communes qui sont proches de MONTPELLIER
MATCH (m:Commune {nom:'MONTPELLIER'}) -[:NEARBY]- (n:Commune)
RETURN m, n

MATCH (m:Commune {nom:'MONTPELLIER'}) -[:NEARBY*]- (n:Commune)
RETURN m, n

MATCH (m:Commune {nom:'MONTPELLIER'}) -[:NEARBY*2]- (n:Commune)
RETURN m, n

MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r

// --ou

MATCH (n)
DETACH DELETE n

// -- un noeud
MATCH (n:Commune {nom:'SOMMIERES'})
DELETE n

// -- une relation
MATCH (n:Commune { nom:'GARONS' })-[r:NEARBY]->()
DELETE r

// -- un attribut de noeud
MATCH (n:Commune { nom:'NIMES' })
REMOVE n.codeinsee
return n

