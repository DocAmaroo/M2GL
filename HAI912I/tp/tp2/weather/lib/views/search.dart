import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:weather/widgets/custom_app_bar.dart';

class SearchPage extends StatefulWidget {
  const SearchPage._({Key? key}) : super(key: key);

  static Route<String> route() {
    return MaterialPageRoute(builder: (_) => const SearchPage._());
  }

  @override
  State<SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends State<SearchPage> {
  final TextEditingController _textController = TextEditingController();

  String get _text => _textController.text;

  @override
  void dispose() {
    _textController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const MyAppBar(title: 'City Search'),
      body: Row(
        children: [
          Expanded(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                style: const TextStyle(fontWeight: FontWeight.w400),
                controller: _textController,
                cursorColor: Colors.black45,
                decoration: const InputDecoration(
                  labelText: 'Ville',
                  floatingLabelStyle: TextStyle(color: Colors.deepOrange),
                  hintText: 'Japon',
                  enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Colors.black)),
                  focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Colors.deepOrange)),
                  border: UnderlineInputBorder(
                      borderSide: BorderSide(color: Colors.pink)),
                ),
              ),
            ),
          ),
          IconButton(
            key: const Key('searchPage_search_iconButton'),
            icon: const Icon(Icons.search),
            onPressed: () => Navigator.of(context).pop(_text),
          )
        ],
      ),
    );
  }
}
