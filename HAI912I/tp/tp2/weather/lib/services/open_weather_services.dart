import 'dart:convert';

import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:weather/data/models/weather_model.dart';
import 'package:http/http.dart' as http;

class OpenWeatherApiServices {
  static const _baseUrl = "api.openweathermap.org/data/2.5/forecast";

  Future<WeatherModel> getWeatherForecast({required String cityName}) async {
    // Url query
    final String query =
        '?q=' + cityName + '&appid' + dotenv.get('OPEN_WEATHER_API_KEY');

    // Final url to call
    final Uri url = Uri.parse(_baseUrl + query);

    // Get the response
    final response = await http.get(url);

    // Handle response
    if (response.statusCode == 200) {
      print('api response ${response.body}');
      return WeatherModel.fromJson(jsonDecode(response.body));
    } else {
      throw Exception("Error getting wheater forecast");
    }
  }
}
