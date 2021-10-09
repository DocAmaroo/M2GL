import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:weather/data/models/weather_model.dart';

import 'item_widget.dart';
import 'spacer_widget.dart';

class CurrentConditions extends StatelessWidget {
  final String _cityName;
  final Liste _liste;

  const CurrentConditions(this._cityName, this._liste, {Key? key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(children: <Widget>[
        _getIcon(),
        const MySpacer(),
        _getCityName(),
        const MySpacer(),
        _getDescription(),
        const MySpacer(),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            _getHumidityItem(),
            _getTemperatureItem(),
            _getWindItem()
          ],
        )
      ]),
    );
  }

  Image _getIcon() {
    return Image(image: NetworkImage(_liste.weather![0].iconUrl));
  }

  Text _getCityName() {
    return Text(_cityName,
        style: const TextStyle(fontSize: 30, fontWeight: FontWeight.bold));
  }

  Text _getDescription() {
    return Text(_liste.weather![0].description.toString(),
        style: const TextStyle(fontSize: 16, fontStyle: FontStyle.italic));
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
