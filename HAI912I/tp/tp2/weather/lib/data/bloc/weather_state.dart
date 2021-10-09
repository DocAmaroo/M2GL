part of 'weather_bloc.dart';

abstract class WeatherState {}

// Loading Weather State
class WeatherInitial extends WeatherState {}

class LoadingWeatherState extends WeatherState {}

// Weather information have been loaded
class LoadedWeatherState extends WeatherState {
  final WeatherForecastModel weather;
  LoadedWeatherState({required this.weather});
}

// Failed to load weather information
class FailedToLoadWeatherState extends WeatherState {
  final Error error;
  FailedToLoadWeatherState({required this.error});
}
