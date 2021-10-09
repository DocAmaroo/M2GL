import 'package:flutter/material.dart';
import 'package:weather/data/models/weather_model.dart';
import 'package:weather/widgets/spacer_widget.dart';

import 'item_widget.dart';

class ForecastCardWidget extends StatelessWidget {
  final Liste _liste;

  const ForecastCardWidget(this._liste, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
        child: Center(
      child: Container(
          padding: const EdgeInsets.all(12),
          child: Column(children: [
            _getDate(),
            const MySpacer(size: 8),
            _getIcon(),
            const MySpacer(size: 8),
            _getDescription(),
            const MySpacer(size: 8),
            Row(
              children: <Widget>[
                _getHumidityItem(),
                _getTemperatureItem(),
                _getWindItem()
              ],
            )
          ])),
    ));
  }

  Text _getDate() {
    return Text(_liste.formatedDate,
        style: const TextStyle(fontSize: 14, fontStyle: FontStyle.italic));
  }

  Image _getIcon() {
    return Image(image: NetworkImage(_liste.weather![0].iconUrl));
  }

  Text _getDescription() {
    return Text(_liste.weather![0].description.toString(),
        style: const TextStyle(fontSize: 14, fontStyle: FontStyle.italic));
  }

  WeatherItem _getHumidityItem() {
    return WeatherItem(
        Icons.water_rounded, '${_liste.main!.humidity.toString()}%');
  }

  WeatherItem _getTemperatureItem() {
    return WeatherItem(
        Icons.thermostat_rounded, '${_liste.main!.tempMax.toString()}°C');
  }

  WeatherItem _getWindItem() {
    return WeatherItem(Icons.air, '${_liste.wind!.speed.toString()}km/h');
  }
}
