import 'package:flutter/widgets.dart';
import 'package:quizz/data/models/question.dart';

class GameViewModel extends ChangeNotifier {
  late List<QuestionModel> questions;
  int _currentQuestionIndex = 0;
  int points = 0;
  int correct = 0;
  int incorrect = 0;

  GameViewModel({required this.questions});

  // Get/Set the current question
  QuestionModel get question => questions[_currentQuestionIndex];

  // Get/Set the current question number
  int get currentQuestionIndex => _currentQuestionIndex;
  set currentQuestionIndex(int val) {
    _currentQuestionIndex = val;
    notifyListeners();
  }

  // Reset the game
  void reset() {
    _currentQuestionIndex = 0;
    points = 0;
    correct = 0;
    incorrect = 0;
  }
}
