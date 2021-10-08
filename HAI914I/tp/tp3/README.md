# HAI914 - TP3 | CouchDB

## Commands

Get all database

```curl
curl -v $COUCH3/_all_dbs
```

## Exercice 2

```curl
curl -X POST $COUCH3/canta_occitanie/_bulk_docs -d @aveyron.json -H "Content-Type: application/json"

curl -X POST $COUCH3/canta_occitanie/_bulk_docs -d @gard.json -H "Content-Type: application/json"

curl -X POST $COUCH3/canta_occitanie/_bulk_docs -d @hauteGaronne.json -H "Content-Type: application/json"

curl -X POST $COUCH3/canta_occitanie/_bulk_docs -d @herault.json -H "Content-Type: application/json"

curl -X POST $COUCH3/canta_occitanie/_bulk_docs -d @regions_partiel.json -H "Content-Type: application/json"
```

## Exercice 3

### Question 1

```curl
curl -X GET $COUCH3
```

<details><summary>Voir la réponse</summary>

    {"couchdb":"Welcome","version":"3.1.1","git_sha":"ce596c65d","uuid":"1126b56a22d66301e43b4957c7000b5c","features":["access-ready","partitioned","pluggable-storage-engines","reshard","scheduler"],"vendor":{"name":"The Apache Software Foundation"}}

</details>

<br>

```curl
curl -v $COUCH3/_all_dbs
```

### Question 2

```curl
curl -vX GET $COUCH3/canta_occitanie
```

:point_right: On peut récupérer le nombre de documents dans le résultat de la requête.

### Question 3

```curl
curl -vX GET $COUCH3/canta_occitanie/_all_docs
```

### Question 4

```curl
curl -X GET $COUCH3/canta_occitanie/34196
```

Le numéro de révision est écrit dans le réponse de la requête *_rev*.

## Exercice 4

Pour accéder à l'affichage graphique

```curl
http://prodpeda-couchdb3-2.infra.umontpellier.fr:5984/_utils
```

Information de connexions:

    username: admin
    password: 7kR9fu0L8ZYqEp4ZJVd


Old regions

```mango
function(doc){
  if (doc.type == 'old_region') {
    emit(null, doc._id)
  }
}
```

Get communes informations

```mango
function (doc) {
  if (doc.type == 'commune') { emit(doc.name, doc.longitude, doc.latitude); }
}
```
