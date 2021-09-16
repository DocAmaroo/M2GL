# HAI934I - Vérification automatique de programmes

## Liens

🔗 [Moodle](https://moodle.umontpellier.fr/course/view.php?id=23037)

## Introduction


🚩 Preuve par récurrence de la formule ([voir diapo 9](./HAI934/../HAI934I/cours/intro.pdf))

<details><summary>voir la preuve</summary>

On veut démontrer par récurrence que ∀x ∈ N.f(x) et ∀i ∈ N alors x*x = g(x) = h(x,x)

**Initialisation**

👉 `i = 0`

    x*x = h(x,i)
    h(x,0) = x

    Or x = i car g(x) = h(x,x)
    Donc h(0,0) = 0
    Enfin 0*0 = 0
    
👉 `i = 1`

    Même raisonnement.

🟢 Le cas de base est correct

**Preuve**

En supposant que l'hypothèse de récurrence est vraie, alors on sait que:

<center> ∀x ∈ N.f(x) et ∀i ∈ N\{0, 1} </center>
<center> h(x,i) = x + h(x, i-1) </center>

Et on veut montrer que:

<center> x*x = h(x,i) </center>

    x*x = x + h(x, i-1)
    h(x, i-1) = x*x-x = x(x-1)

👉 h(x, i-1) = x(x-1) *(i)*

    h(x,i) = x + x(x-1) | d'après (i)
    h(x,i) = x*x

**Conclusion**

On as démontré que ∀x ∈ N.f(x) et ∀i ∈ N l'axiome de récurrence est vrai.

</details>


