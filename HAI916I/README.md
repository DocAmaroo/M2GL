# HAI916I - IA pour le génie logiciel

- [HAI916I - IA pour le génie logiciel](#hai916i---ia-pour-le-génie-logiciel)
  - [Liens](#liens)
  - [Rappels](#rappels)
  - [Lignes de produits](#lignes-de-produits)
  - [Programmation par contraintes](#programmation-par-contraintes)
  - [Apprentissage automatique par analyse de programmes (NLP/TALN)](#apprentissage-automatique-par-analyse-de-programmes-nlptaln)

## Liens

🔗 [Moodle](https://moodle.umontpellier.fr/enrol/index.php?id=22617 "Accéder au moodle")

## Rappels

📑 [Cours 1 - Rappel](../HAI916I/cours/cours1_rappels.pdf)

## Lignes de produits

📑 [Cours 2](../HAI916I/cours/cours2.pdf)

## Programmation par contraintes

📑 [Cours 3](cours/cours3.pdf)

## Apprentissage automatique par analyse de programmes (NLP/TALN)

📑 [Cours 4](cours/cours4.pdf)

👉 NLP à l'ancienne(Wordnet):  
- Manque de nuances.  
- Impossible à mettre à jour.  
- Représentation vectorielle trop complexe et intensive pour la mémoire.  
- Mesure de similarité trop complexe.  
- Autre mesure comme bag of words ou n-grams souffrent des mêmes problèmes.  

✔️ Solution possible est d'encoder la similarité des vecteurs

👉 NLP moderne:
- Réseaux de neurones
- Représentation des mots par leur contexte dans la phrase *(Word Vector)*
 
  Un vecteur dense par mot, choisi de telle sorte à ce qu’il soit similaire aux vecteurs des mots qui apparaissent dans le même contexte.

*NB:* p(y|x) se lit, probabilité y étant donné x.