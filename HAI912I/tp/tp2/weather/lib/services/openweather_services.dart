import 'dart:convert';

import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:weather/data/models/weather_model.dart';
import 'package:http/http.dart' as http;
import 'package:weather/errors/http_exception.dart';

class OpenWeatherApiServices {
  static const _host = 'api.openweathermap.org';
  static const _path = 'data/2.5';
  static const _units = 'metric';

  /// Return final Uri to call the Openweather API.
  Uri _buildUri(String endpoint, Map<String, String> queryParameters) {
    // handle query parameters
    var query = {'appid': dotenv.get('OPEN_WEATHER_API_KEY')};
    queryParameters = query..addAll(queryParameters);

    // build the Uri
    var uri = Uri.https(_host, _path + '/$endpoint', queryParameters);

    return uri;
  }

  /// Return weather forecast informaton from the given city.
  Future<WeatherForecastModel> getWeatherForecast(
      {required String cityName}) async {
    final Uri url =
        _buildUri('forecast', {'q': cityName, 'units': _units}); // Get the url
    final response = await http.get(url); // Get the response

    // Handle response
    if (response.statusCode == 200) {
      return WeatherForecastModel.fromJson(jsonDecode(response.body));
    } else {
      throw HTTPException(
          response.statusCode, "Unable to fetch weather forecast data");
    }
  }
}
