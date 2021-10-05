import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:quizz/data/viewmodels/game_vm.dart';
import 'package:quizz/views/homepage.dart';

class Result extends StatefulWidget {
  final int score, totalQuestion, correct, incorrect;

  const Result(
      {Key? key,
      required this.score,
      required this.totalQuestion,
      required this.correct,
      required this.incorrect})
      : super(key: key);

  @override
  State<Result> createState() => _ResultState();
}

class _ResultState extends State<Result> {
  @override
  Widget build(BuildContext context) {
    GameViewModel gameViewModel = Provider.of<GameViewModel>(context);

    return Scaffold(
        appBar: AppBar(
          title: const Text('Results'),
          centerTitle: true,
          flexibleSpace: Container(
            decoration: const BoxDecoration(
                gradient: LinearGradient(
                    begin: Alignment.topLeft,
                    end: Alignment.bottomRight,
                    colors: <Color>[Colors.deepPurple, Colors.indigo])),
          ),
        ),
        body: Center(
          child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                // Main score
                Text(
                    'Votre score est de ${widget.score} / ${widget.totalQuestion * 20}',
                    style: const TextStyle(fontSize: 18)),
                const SizedBox(height: 16),
                // Correct and incorrect counter
                Text(
                    '${widget.correct} correcte | ${widget.incorrect} incorrecte',
                    style: const TextStyle(fontSize: 16)),
                const SizedBox(height: 32),
                // Restart button
                GestureDetector(
                    onTap: () {
                      gameViewModel.reset();
                      Navigator.pushReplacement(
                          context,
                          MaterialPageRoute(
                              builder: (context) => const MyHomePage()));
                    },
                    child: Container(
                        padding: const EdgeInsets.symmetric(
                            vertical: 16, horizontal: 64),
                        child: const Text("Replay Quizz",
                            style:
                                TextStyle(color: Colors.white, fontSize: 16)),
                        decoration: BoxDecoration(
                            shape: BoxShape.rectangle,
                            borderRadius: BorderRadius.circular(16),
                            gradient: const LinearGradient(
                                begin: Alignment.topLeft,
                                end: Alignment.bottomRight,
                                colors: <Color>[
                                  Colors.deepPurple,
                                  Colors.indigo
                                ])))),
              ]),
        ));
  }
}
