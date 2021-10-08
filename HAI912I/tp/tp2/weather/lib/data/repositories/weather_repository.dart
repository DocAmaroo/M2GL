import 'package:weather/data/models/weather_model.dart';
import 'package:weather/services/open_weather_services.dart';

class WeatherRepository {
  final OpenWeatherApiServices _openWeatherApiServices;

  WeatherRepository({OpenWeatherApiServices? openWeatherApiServices})
      : _openWeatherApiServices =
            openWeatherApiServices ?? OpenWeatherApiServices();

  Future<WeatherModel> getWeather(String cityName) async {
    final weather =
        await _openWeatherApiServices.getWeatherForecast(cityName: cityName);

    return weather;
  }
}
