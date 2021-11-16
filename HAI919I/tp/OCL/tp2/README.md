# HAI919 TP 2 OCL

## 1 - Contraintes sur un modèle : les Tortues

👉 Une tortue mâle ne peut avoir de dates de ponte

```ocl
context Tortue inv:
  self.sexe == #M and self.datePonte->isEmpty()
```

👉 Une tortue habite l’un des lieux de la répartition géographique de son espèce

```ocl
context Tortue inv:
  self.espèce.répartitionGéographique.lieu->includes(self.habite)
```

👉 Tout aliment utilisable pour l’élevage en captivité fait partie du régime alimentaire général

```ocl
context EspèceTortue inv:
  self.biologie.régimeGénéral->includesAll(self.modeElevage.régimeCaptivité)
```

👉 écrire la post-condition de l’opération `nourriturePossible(t:TypeAliment):boolean` de la classe espèce-Tortue; t est un un type d’aliment possible s’il fait partie du régime alimentaire général de l’espèce

```ocl
context EspèceTortue::nourriturePossible(t:TypeAliment):boolean
  body: self.biologie.régimeGénéral->includes(t)
```

👉 écrire la pré-condition de l’opération mange(a:Aliment) de la classe Tortue; une tortue ne peut manger que des aliments prévus par un des régimes alimentaires de son espèce (régime général ou de captivité), il faut donc tester si la tortue est captive ou non pour connaître le régime alimentaire et savoir si a est admissible.

```ocl
context Tortue::mange(a:Aliment):
  pre: (self.captive and self.espèce.modeElevage.régimeCaptivité.aliment->includes(a)) or 
       (not(self.captive) and self.espèce.biologie.régimeGénéral.aliment->includes(a))
```

## 2 - Contraintes sur un métamodèle : UML2.5

### Root Diagram

👉 Définir la requête allOwnedElements:Element [0..*] qui retourne les éléments possédés directement ou indirectement par un autre élément.
```ocl
body: ownedElement->union(ownedElement->collect(e | e.allOwnedElements()))->asSet()
```

👉 Un élément ne peut pas se contenir lui-même, que ce soit directement ou indirectement.
```ocl
inv: not allOwnedElements()->includes(self)
```

### Classifier Diagram

👉 Définir la requête parents():Classifier [0..*] qui retourne tous les successeurs immédiats (généralisations) d’un classifier.
```ocl
body: generalization.general->asSet()
```

👉 Définir la requête allParents():Classifier [0..*] qui retourne tous les successeurs (généralisations) d’un classifier.
```ocl
body: parents()->union(parents()->collect(allParents())->asSet())
```

👉 Définir la requête conformsTo(other:Classifier):boolean qui re- tourne vrai si et seulement si other est une généralisation du classifieur.
```ocl
body: if other.oclIsKindOf(Classifier) then  let otherClassifier : Classifier = other.oclAsType(Classifier) in    self = otherClassifier or allParents()->includes(otherClassifier)
else  
    false
endif
```

👉 /general est égal aux successeurs immédiats.
```ocl
```

👉 Il n’y a pas de circuit dans les généralisations.
```ocl
inv: not allParents()->includes(self)
```
