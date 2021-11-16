# HAI919 TP 1 OCL

## Exercice 1

### Question 1.1

👉 La taille d’une tortue est comprise entre 0 et la taille maximale admise pour son espèce ;

```ocl
context Tortue tailleBorné inv:
  taille > 0 and taille <= espèce.tailleMaxAdulte
```

👉 La température de jour est supérieure à la température de nuit

```ocl
context ModeElevage checkTemperature inv:
  tempJour > tempNuit
```


👉 On ne peut changer la taille d’une tortue que pour l’augmenter ; Précisez la pré-condition et post-condition de l’opération ;

```ocl
context Tortue::changeTaille( n:entier )
  pre: n > taille and n <= self.espèce.tailleMaxAdulte
  post: n = taille
```

<details><summary>Voir une autre version faite car je savais pas lire</summary>

```ocl
context Tortue::augmenterTaille( value:entier )
  pre: (taille + value) <= self.espèce.tailleMaxAdulte
  post: result = (taille@pre + value)
```

</details> <br/>

### Question 2.1

📌 [Voir shéma UML 2.5](HAI919I/tp/meta-model/tp1/sujet2_diagram.pdf)

👉 La borne inférieure doit être positive ou nulle.

```ocl
context MultiplicityElement inv:
  lowerValue >= 0
```

👉 La borne supérieure doit être supérieure à la borne inférieure.

```ocl
context MultiplicityElement inv:
  lowerValue < upperValue 
```

👉 La valeur dérivée de /lower doit être égale à la borne inférieure.

```ocl
context MultiplicityElement::lowerBand():
  post: /lower = lowerValue
```

👉 La valeur dérivée de /upper doit être égale à la borne supérieure.

```ocl
context MultiplicityElement::lowerBand():
  post: /upper = upperValue
```

👉 La requête isMultivalued() retourne vrai si la propriété peut prendre plus d’une valeur; elle ne s’applique que lorsqu’une borne supérieure a été spécifiée.

```ocl
context MultiplicityElement::isMultivalued():
    pre: self.upperBound()->notEmpty()
    body: self.upperBound() > 1
```

👉 La requête includesMultiplicity(M: MultiplicityElement) retourne vrai si la multiplicité de l’élément inclut M. Vous devez déterminer les conditions d’application.

```ocl
context MultiplicityElement::includesMultiplicity(M: MultiplicityElement):
    pre: self.upperBound()->notEmpty() and 
      self.lowerBound()->notEmpty() and 
      M.upperBound()->notEmpty() and 
      M.lowerBound()->notEmpty()
    body: (self.lowerBound() <= M.lowerBound()) and (self.upperBound() >= M.upperBound())
```

### Question 2.2

👉 Une bodyCondition ne peut être spécifiée que pour une opération de type requête.

```ocl
context Operation inv:
  self.bodyContext
```