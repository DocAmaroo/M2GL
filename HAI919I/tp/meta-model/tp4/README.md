# HAI919 - TP4

💡 `Classifier`:

- Ensemble d'instances avec des carac. communes
- Ce sont des types
- Organisé par une relation de généralisation

💡 `Conformance de types` &rarr; Un classifier se conforme à lui-même et à ses généralisations.

💡 `Substituabilité` &rarr; le classifier le plus spécifique puisse être utilisé partout ou le classifier plus général peut l'être n'est pas auto. si spécialisation. L'attribut isSubstitutable permet de le préciser.

💡 `Generalization`

- Relation taxinomique
- Dans un métamodèle représenté sous forme d'une relation orientée (*DirectedRelationship*) entre une classifier général G et un classifier spécifique S (Toute instance de S est instance de G => S hérite des carac de G)
- S peut ajouter ou redéfinir des features.