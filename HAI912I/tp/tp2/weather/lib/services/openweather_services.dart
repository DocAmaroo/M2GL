import 'dart:convert';

import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:weather/data/models/weather_model.dart';
import 'package:http/http.dart' as http;
import 'package:weather/errors/http_exception.dart';

class OpenWeatherApiServices {
  static const _host = 'api.openweathermap.org';
  static const _path = 'data/2.5';

  Uri _buildUri(String endpoint, Map<String, String> queryParameters) {
    // handle query parameters
    var query = {'appid': dotenv.get('OPEN_WEATHER_API_KEY')};
    query = query..addAll(queryParameters);

    // build the Uri
    var uri = Uri(
      scheme: 'http',
      host: _host,
      path: _path + '/$endpoint',
      queryParameters: query,
    );

    return uri;
  }

  Future<WeatherModel> getWeatherForecast({required String cityName}) async {
    // Get the url
    //final Uri url = Uri.parse(_baseUrl + query);
    final Uri url = _buildUri('forecast', {'q': cityName});

    // Get the response
    final response = await http.get(url);

    // Handle response
    if (response.statusCode == 200) {
      print('api response ${response.body}');
      return WeatherModel.fromJson(jsonDecode(response.body));
    } else {
      throw HTTPException(
          response.statusCode, "Unable to fetch weather forecast data");
    }
  }
}
