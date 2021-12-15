# Exercices

## Question 1

3x1 + 2x2 ≤ 5x3 ∧ 2x1 − 2x2 = 0  
3x1 + 2x2 - 5x3 ≤ 0 ∧ 2x1 − 2x2 = 0

_Forme générale :_

3x1 + 2x2 - 5x3 - s1 = 0 ∧  
2x1 - 2x2 - s2 = 0 ∧  
s1 ≤ 0 ∧  
0 ≤ s2 ∧  
s2 ≤ 0

_Application du simplexe :_

N={x1,x2,x3}  
B={s1,s2}  
α(x1)=0,α(x2)=0,α(x3)=0,α(s1)=0,α(s2)=0

Tableau

|    | x1 | x2 | x3 |
| -- | -- | -- | -- |
| s1 | 3  | 2  | -5 |
| s2 | 2  | -2 | 0  |

s1 ≤ 0 ∧  
0 ≤ s2 ∧  
s2 ≤ 0

👉 Les variables basiques (de B) sont-elles dans leurs bornes ?
> Oui, donc on ne fait rien.

🟢 La solution est : α(x1)=0,α(x2)=0,α(x3)=0

## Question 2

3x + y ≤ 3 ∧ x + y ≥ 1 ∧ x − y ≥ − 2
 
_Forme générale :_
 
3x + y - s1 = 0 ∧  
x + y - s2 =0 ∧  
x - y - s3 = 0 ∧  
s1 <= 3 ∧  
s2 ≥ 1 ∧  
s3 ≥ -2 

_Application du simplexe :_

N = {x,y}  
B={s1,s2,s3}  
α(x)=0,α(y)=0,α(s1)=0,α(s2)=0,α(s3)=0
 
_Tableau :_

|    | x | y  |
| -- | - | -- |
| s1 | 3 | 1  |
| s2 | 1 | 1  |
| s3 | 1 | -1 |

s1 ≤ 3 ∧  
s2 ≥ 1 ∧  
s3 ≥ -2 

👉 Les bornes des si ?  
> s2 n'est pas dans sa borne, il doit être augmenté de 1 pour être dans sa borne (inférieure).  
> Pivot avec x : on doit augmenter x de 1 (θ = 1).

s2 = x+y => x = s2 - y  
s1 = 3(s2 - y) + y = 3s2 - 2y  
s3 = s2 -2y

_Tableau :_

|    | s2 | y  |
| -- | -- | -- |
| s1 | 3  | -2 |
| x  | 1  | -1 |
| s3 | 1  | -2 |

α(s2)=1,α(x)=1,α(y)=0,α(s1)=3,α(s3)=1

🟢 Solution : α(x)=1,α(y)=0

## Question 3

3x + y ≤ 3 ∧ x + 2y ≥ 2 ∧ x − y ≥ − 2

_Forme générale :_

3x + y - s1 = 0 ∧  
x + 2y - s2 = 0 ∧  
x − y - s3 = 0 ∧  
s1 ≤ 3 ∧  
s2 ≥ 2 ∧  
s3 ≥ -2

_Application du simplexe :_

N = {x,y}  
B={s1,s2,s3}  
α(x)=0,α(y)=0,α(s1)=0,α(s2)=0,α(s3)=0
 
_Tableau :_

|    | x | y  |
| -- | - | -- |
| s1 | 3 | 1  |
| x  | 1 | 2  |
| s3 | 1 | -1 |

s1 ≤ 3 ∧  
s2 ≥ 2 ∧  
s3 ≥ -2

👉 Bornes des si ?
> s2 n'est pas dans sa borne, il doit être augmenté de 2.  
> Pivot avec x: il doit être augmenté de 2.

s2 = x + 2y <=> x = s2 - 2y  
s1 = 3x+y <=> s1 = 3s2 -5y  
s3 = x - y <=> s3 = s2 -3y  

_Tableau :_

|    | s2 | y  |
| -- | -- | -- |
| s1 | 3  | -5 |
| x  | 1  | -2 |
| s3 | 1  | -3 |

α(x)=2,α(s2)=2,α(y)=0,α(s1)=6,α(s3)=2

👉 Bornes des si ?
> s1 n'est pas dans sa borne, il doit être abaissé de 3.  
> Pivot avec y : on doit augmenter y de ? θ = (3-6)/-5 = 3/5

s1 = 3s2-5y <=> y = 3/5s2 - 1/5s1  
x = s2-2y <=> x = s2-(6/5)s2 + (2/5)s1 = -1/5 s2 + 2/5s1  
s3 = s2-3y <=> s3 = s2 - 9/5s2 + 3/5s1 = -4/5s2 + 3/5s1

_Tableau :_

|    | s2   | s1   |
| -- | ---- | ---- |
| y  | 3/5  | -1/5 |
| x  | -1/5 | 2/5  |
| s3 | -4/5 | 3/5  |

α(s1)=3,α(y)=3/5,α(s2)=2,α(x)=(2-6)/5=4/5,α(s3)=2-(9/5)=1/5

👉 Bornes des si ?
> Tout le monde est dans ses bornes.

s1 ≤ 3 ∧  
s2 ≥ 2 ∧  
s3 ≥ -2

🟢 Solution : α(x) = 4/5,α(y)=3/5.



	
