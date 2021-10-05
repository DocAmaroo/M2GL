import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:quizz/data/question_data.dart';

import 'data/viewmodels/game_vm.dart';
import 'views/homepage.dart';

void main() {
  runApp(ChangeNotifierProvider(
    create: (_) => GameViewModel(questions: questions),
    child: const MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Beatbox Quizz',
        theme: ThemeData(
          primarySwatch: Colors.deepPurple,
        ),
        home: const MyHomePage());
  }
}
