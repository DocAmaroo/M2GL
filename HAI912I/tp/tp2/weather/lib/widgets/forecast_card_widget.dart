import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:weather/data/models/weather_model.dart';
import 'package:weather/widgets/spacer_widget.dart';

import 'item_widget.dart';

class ForecastCardWidget extends StatelessWidget {
  final Liste _liste;

  const ForecastCardWidget(this._liste, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16.0)),
      elevation: 3,
      child: Container(
          width: MediaQuery.of(context).size.width / 2,
          padding: const EdgeInsets.all(16),
          child: Column(children: [
            _getDate(),
            const MySpacer(size: 8),
            _getIcon(),
            const MySpacer(size: 8),
            _getDescription(),
            const MySpacer(),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                _getHumidityItem(),
                _getTemperatureItem(),
                _getWindItem()
              ],
            )
          ])),
    );
  }

  Text _getDate() {
    return Text(_liste.formatedDate,
        style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500));
  }

  Image _getIcon() {
    return Image(image: NetworkImage(_liste.weather![0].iconUrl));
  }

  Text _getDescription() {
    return Text(_liste.weather![0].description.toString(),
        style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w400));
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
