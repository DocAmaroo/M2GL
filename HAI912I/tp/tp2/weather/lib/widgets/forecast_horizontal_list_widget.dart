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
          children: getNoonTime(),
        ));
  }

  /// Get forecasts weather at 14pm
  List<ForecastCardWidget> getNoonTime() {
    List<ForecastCardWidget> widgets = [ForecastCardWidget(_weather.list![0])];

    for (int i = 0; i < _weather.list!.length; i++) {
      DateTime date = _weather.list![i].completeDate;
      if (date.hour == 14) widgets.add(ForecastCardWidget(_weather.list![i]));
    }

    return widgets;
  }
}
