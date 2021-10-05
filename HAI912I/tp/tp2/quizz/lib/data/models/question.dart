class QuestionModel {
  String question;
  bool answer;
  String imageURL;

  QuestionModel(
      {required this.question, required this.answer, required this.imageURL});

  String getQuestion() {
    return question;
  }

  bool getAnswer() {
    return answer;
  }

  String getImageURL() {
    return imageURL;
  }
}
