# HAI913I - Évolution et restructuration des logiciels

- [HAI913I - Évolution et restructuration des logiciels](#hai913i---évolution-et-restructuration-des-logiciels)
  - [Liens](#liens)
  - [Cours](#cours)

## Liens

🔗 [Moodle](https://moodle.umontpellier.fr/course/view.php?id=23155)

🔗 [Accéder au cours](http://www.lirmm.fr/~seriai/)

## Cours


👉 **Analyse statique**

- Baséée sur l'analyse du code source
- Considèrent toutes les exec possibles
- calcul complexes mais sans impact sur l'exec

<details><summary>Voir (dé)avantages</summary>

✔️ **Pros**

Pas d'exec, donc pas de dommages et pas de temps d'exec

</details>


👉 **Analyse dynamique**

- Baséée sur une ou plusieurs executions
- Considèrent certaines execution concrète
- Impact sur l'execution proportionnel aux donnée traitées

<details><summary>Voir (dé)avantages</summary>

✔️ **Pros**

- Résultat plus précis
- Obtention d'information de nature temporelle à propos de l'exec
- Obtention de la fréquence ou l'importance de certains évènements

❌ **Cons**

- Étendu de l'analyse dépend de l'étendu des scénarios d'exec 
- Possibilité de dommage en cas d'analyse pour des raisons de sécurité (comportement malicieux)
- Dépendance par rapport au temps d'execution