import 'package:flutter/material.dart';
import 'package:quizz/data/question_data.dart';
import 'package:quizz/model/question_model.dart';
import 'package:quizz/views/result.dart';

import 'custom_app_bar.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final correctSnackBar = const SnackBar(
    content: Text('That\'s correct!'),
    duration: Duration(seconds: 2),
    backgroundColor: Colors.lightGreen,
  );
  final incorrectSnackBar = const SnackBar(
    content: Text('That\'s incorrect!'),
    duration: Duration(seconds: 2),
    backgroundColor: Colors.redAccent,
  );

  late List<QuestionModel> questions;
  int index = 0;
  int correct = 0, incorrect = 0;
  int points = 0;

  @override
  void initState() {
    super.initState();

    questions = getQuestions();
  }

  @override
  Widget build(BuildContext context) {
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
        Text('${index + 1}/${questions.length}',
            style: const TextStyle(fontSize: 24, fontWeight: FontWeight.w500)),
        const SizedBox(width: 12),
        const Text('Question',
            style: TextStyle(fontSize: 17, fontWeight: FontWeight.w400)),
        const Spacer(),
        Text('$points',
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
        child: Text(questions[index].getQuestion(),
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
                image: NetworkImage(questions[index].getImageURL()),
                fit: BoxFit.cover)));
  }

  Container _getButtons() {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 30),
      child: Row(children: <Widget>[
        // True buttons
        Expanded(
            child: GestureDetector(
                onTap: () {
                  bool isCorrect = questions[index].getAnswer() == 1;
                  handleState(isCorrect);
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
                  bool isCorrect = questions[index].getAnswer() == 0;
                  handleState(isCorrect);
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
    setState(() {
      // If correct answer add 20 points, else remove 5 points
      if (isCorrect) {
        points = points + 20;
        correct++;
        ScaffoldMessenger.of(context).showSnackBar(correctSnackBar);
      } else {
        points = points - 5;
        incorrect++;
        ScaffoldMessenger.of(context).showSnackBar(incorrectSnackBar);
      }

      // if no more question then go to result page, else get next question
      if (index < questions.length - 1) {
        index++;
      } else {
        Navigator.pushReplacement(
            context,
            MaterialPageRoute(
                builder: (BuildContext context) => Result(
                    score: points,
                    totalQuestion: questions.length,
                    correct: correct,
                    incorrect: incorrect)));
      }
    });
  }
}
