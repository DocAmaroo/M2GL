import 'package:flutter/widgets.dart';
import 'package:weather/data/models/weather_model.dart';

import 'forecast_card_widget.dart';

class ForecastHorizontalListWidget extends StatelessWidget {
  final WeatherForecastModel _weather;

  const ForecastHorizontalListWidget(this._weather, {Key? key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
        height: 250,
        child: ListView(
          scrollDirection: Axis.horizontal,
          children: _getNoonTime(),
        ));
  }

  /// Get forecasts weather at 14pm
  List<ForecastCardWidget> _getNoonTime() {
    List<ForecastCardWidget> widgets = [];

    for (int i = 1; i < _weather.list!.length; i++) {
      DateTime date = _weather.list![i].completeDate;
      if (date.hour == 14 && date.day != DateTime.now().day) {
        widgets.add(ForecastCardWidget(_weather.list![i]));
      }
    }

    return widgets;
  }
}
