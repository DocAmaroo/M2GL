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
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16.0)),
      elevation: 3,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(children: <Widget>[
          _getIcon(context),
          const MySpacer(),
          _getCityName(),
          const MySpacer(size: 16),
          _getDescription(),
          const MySpacer(size: 16),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              _getHumidityItem(),
              _getTemperatureItem(),
              _getWindItem()
            ],
          )
        ]),
      ),
    );
  }

  Image _getIcon(BuildContext context) {
    return Image(image: NetworkImage(_liste.weather![0].iconUrl));
  }

  Text _getCityName() {
    return Text(_cityName,
        style: const TextStyle(fontSize: 30, fontWeight: FontWeight.w500));
  }

  Text _getDescription() {
    return Text(_liste.weather![0].description.toString(),
        style: const TextStyle(fontSize: 22, fontWeight: FontWeight.w400));
  }

  WeatherItem _getHumidityItem() {
    return WeatherItem(
        Icons.water_rounded, '${_liste.main!.humidity.toString()}%');
  }

  WeatherItem _getTemperatureItem() {
    int temp = _liste.main!.tempMax!.floor();
    return WeatherItem(Icons.thermostat_rounded, '$temp°C');
  }

  WeatherItem _getWindItem() {
    int wind = _liste.wind!.speed!.floor();
    return WeatherItem(Icons.air, '$wind km/h');
  }
}
