# HAI914 - TP4 | CouchDB

## Exercice 1

👉 Le meilleur fichier JSON est *vaccin2.json* car il évite la redondance de donnée notamment pour la date.

## Exercice 2

👉 Information de la base de donnée

```curl
curl -vX GET $COUCH3/canta_vaccination
```

👉 Lister tous les documents de la DB

```curl
curl -vX GET $COUCH3/canta_vaccination/_all_docs
```

👉 Afficher le contenu d'un document

```curl
curl -vX GET $COUCH3/canta_vaccination/32ed5519c2e3cb6bd61f450ecab3aefb
```

## Exercice 3

👉 Donnez toutes les documents de type *"couverture_vaccinale"* du département de l’Hérault (34)(clé doc.id et valeur doc.jour).

```mango
function (doc) {
  if (doc.dep === '34' && doc.type === 'couverture_vaccinale') {
    emit(doc._id, doc.jour)
  }
}
```

👉 Donnez le nombre de documents de type *"couverture_vaccinale"* du département de l’Hérault (34)(clé doc.id, valeur 1).

```mango
function (doc) { // Group Level: None _sum
  if (doc.dep === '34' && doc.type === 'couverture_vaccinale') {
    emit(doc._id, doc.jour)
  }
}

// reduce: _sum | Group Level 1
```

👉 Donnez le nombre de documents de type *"couverture_vaccinale"* du département de l’Hérault (34) pour chaque année écoulée. Un exemple de manipulation de date est fourni.

```mango
function (doc) {
  if (doc.dep === '34' && doc.type === 'couverture_vaccinale') {
    const date = new Date(doc.jour);
    emit(date.getFullYear(), 1);
  }
}

// reduce: _count | GROUP LEVEL 1
```

👉 Donnez les documents de type *"couverture_vaccinale"* pour le vaccin Pfizer (clé doc.id et valeur doc.jour et doc.dep)

```mango
function (doc) {
  if (doc.type == 'couverture_vaccinale') {
    if(doc.vaccinations.some(vaccin => vaccin.vaccin === "Pfizer")) {
       emit(doc._id, {
        "jour": doc.jour,
        "dep": doc.dep
      });
    }
  }
}
```

👉 Donnez le nombre de documents de type *"couverture_vaccinale"* pour le vaccin Pfizer par département

```mango
function (doc) {
  if (doc.type == 'couverture_vaccinale') {
    if(doc.vaccinations.some(vaccination => vaccination.vaccin === "Pfizer")) {
       emit(doc.dep, null);
    }
  }
}

// reduce: _count | GROUP LEVEL: 1
```

👉 Donnez le nombre de documents de type *"couverture_vaccinale"* pour le vaccin Pfizer par mois et par an

```mango
function (doc) {
  if (doc.type == 'couverture_vaccinale') {
    const date = new Date(doc.jour);
    if(doc.vaccinations.some(vaccination => vaccination.vaccin === "Pfizer")) {
       emit([date.getMonth(), date.getFullYear()], null);
    }
  }
}

// reduce: _count | GROUP LEVEL: 2
```

👉 Donnez la somme de dose1 (et autres statistiques) pour le vaccin Pfizer par département, par mois et par an

```mango

function (doc) {
  const date = new Date(doc.jour);
  const nbDoses = doc.vaccinations.reduce((total,vaccination) => {
    if (vaccination.vaccin === "Pfizer") {
      const nbDose = vaccination.doses.reduce((total, dose) => total + (dose.dose1 || 0), 0);
      total += nbDose;
    }
    return total;
  }, 0);
  emit([doc.dep, date.getMonth(), date.getFullYear()], nbDoses);
}

// reduce: _sum | GROUP LEVEL: 3
```

## Exercice 4