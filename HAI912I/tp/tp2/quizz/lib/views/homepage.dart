import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:quizz/data/viewmodels/game_vm.dart';
import 'package:quizz/views/result.dart';

import 'custom_app_bar.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late GameViewModel gameViewModel;

  @override
  Widget build(BuildContext context) {
    // Get my provider
    gameViewModel = Provider.of<GameViewModel>(context);

    return Scaffold(
        appBar: const MyAppBar(title: Text("Beatbox Quizz")),
        body: Container(
            padding: const EdgeInsets.symmetric(vertical: 50),
            child: Column(children: <Widget>[
              _getGameState(),
              const SizedBox(height: 32),
              _getQuestion(),
              const SizedBox(height: 32),
              _getImage(),
              const Spacer(),
              _getButtons()
            ])));
  }

  Container _getGameState() {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 32),
      child: Row(crossAxisAlignment: CrossAxisAlignment.end, children: <Widget>[
        Text(
            '${gameViewModel.currentQuestionIndex + 1}/${gameViewModel.questions.length}',
            style: const TextStyle(fontSize: 24, fontWeight: FontWeight.w500)),
        const SizedBox(width: 12),
        const Text('Question',
            style: TextStyle(fontSize: 17, fontWeight: FontWeight.w400)),
        const Spacer(),
        Text('${gameViewModel.points}',
            style: const TextStyle(fontSize: 24, fontWeight: FontWeight.w500)),
        const SizedBox(width: 12),
        const Text('Points',
            style: TextStyle(fontSize: 17, fontWeight: FontWeight.w400)),
      ]),
    );
  }

  Container _getQuestion() {
    return Container(
        padding: const EdgeInsets.symmetric(horizontal: 32),
        child: Text(gameViewModel.question.question,
            textAlign: TextAlign.center,
            style: const TextStyle(fontWeight: FontWeight.w600, fontSize: 18)));
  }

  Container _getImage() {
    return Container(
        width: MediaQuery.of(context).size.width / 1.2,
        height: MediaQuery.of(context).size.height / 2,
        decoration: BoxDecoration(
            shape: BoxShape.rectangle,
            borderRadius: BorderRadius.circular(16),
            image: DecorationImage(
                image: NetworkImage(gameViewModel.question.getImageURL()),
                fit: BoxFit.cover)));
  }

  Container _getButtons() {
    bool answer = gameViewModel.question.getAnswer();

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 30),
      child: Row(children: <Widget>[
        // True buttons
        Expanded(
            child: GestureDetector(
                onTap: () {
                  handleState(answer == true);
                },
                child: Container(
                    alignment: Alignment.center,
                    padding: const EdgeInsets.symmetric(vertical: 12),
                    child: const Text('True',
                        style: TextStyle(
                            color: Colors.white,
                            fontSize: 17,
                            fontWeight: FontWeight.w500)),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(24),
                        gradient: const LinearGradient(
                            begin: Alignment.topLeft,
                            end: Alignment.bottomRight,
                            colors: <Color>[
                              Colors.green,
                              Colors.lightGreen
                            ]))))),
        const SizedBox(width: 20),
        // False button
        Expanded(
            child: GestureDetector(
                onTap: () {
                  handleState(answer == false);
                },
                child: Container(
                    alignment: Alignment.center,
                    padding: const EdgeInsets.symmetric(vertical: 12),
                    child: const Text('False',
                        style: TextStyle(
                            color: Colors.white,
                            fontSize: 17,
                            fontWeight: FontWeight.w500)),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(24),
                        gradient: const LinearGradient(
                            begin: Alignment.topLeft,
                            end: Alignment.bottomRight,
                            colors: <Color>[
                              Colors.pink,
                              Colors.deepOrangeAccent
                            ]))))),
      ]),
    );
  }

  void handleState(bool isCorrect) {
    // If correct answer add 20 points, else remove 5 points
    if (isCorrect) {
      gameViewModel.points += 20;
      gameViewModel.correct++;
      ScaffoldMessenger.of(context).showSnackBar(getSnackBar(true));
    } else {
      gameViewModel.points -= 5;
      gameViewModel.incorrect++;
      ScaffoldMessenger.of(context).showSnackBar(getSnackBar(false));
    }

    // if no more question then go to result page, else get next question
    if (gameViewModel.currentQuestionIndex <
        gameViewModel.questions.length - 1) {
      gameViewModel.currentQuestionIndex++;
    } else {
      Navigator.pushReplacement(
          context,
          MaterialPageRoute(
              builder: (BuildContext context) => Result(
                  score: gameViewModel.points,
                  totalQuestion: gameViewModel.questions.length,
                  correct: gameViewModel.correct,
                  incorrect: gameViewModel.incorrect)));
    }
  }

  // Custom SnackBar
  SnackBar getSnackBar(bool isSuccess) {
    return SnackBar(
      content: isSuccess
          ? const Text('That\'s correct!')
          : const Text('That\'s incorrect!'),
      duration: const Duration(seconds: 2),
      backgroundColor: isSuccess ? Colors.lightGreen : Colors.redAccent,
    );
  }
}
