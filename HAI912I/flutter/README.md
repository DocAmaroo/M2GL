# HAI912I - Dev. mobile avancé, IoT et embarqué | Partie 1

## Liens

🔗 Moodle

## Introduction

**Programmation native**

> Plus performant car on utilise les outils spécifiques pour cette application. 


```dart
void loop(int x) {
  for (int i=0; i < x; i++) {
    if (i == 69) break;
    print(i);
  }   
}

class Person {
  int id;
  String name;
  
  Person(this.id, this.name);

  String get getName => name;
  set setName(String val) => name = val;
}

class TypePerson extends Person{
  String type;
  TypePerson(id, name,this.type) : super(id, name);
  
  String get getType => type;
  set setType(String val) => type = val;
}

class A {}

abstract class IService {
  double add(double a, double b);
  double sub(double a, double b);
}

class Service extends A implements IService {
  double add(double a, double b) { return a+b; }
  double sub(double a, double b) { return a-b; }
}

void main() {
//   loop(100);
  var me = Person(1, 'Thomas');
  me.name = 'Maximou';
  print(me.name);
  
  TypePerson TP = TypePerson(2, 'Charlimou', 'Gay');
  print('${TP.getName} | ${TP.getType}');
  
  Service s = Service();
  print(s.add(2, 3));  
}
```