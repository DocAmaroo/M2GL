import 'package:flutter/material.dart';

class WeatherItem extends StatelessWidget {
  final IconData _icon;
  final String _text;

  const WeatherItem(this._icon, this._text, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(children: [Icon(_icon), Text(_text)]);
  }
}
