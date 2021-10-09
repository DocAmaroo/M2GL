part of 'weather_bloc.dart';

abstract class WeatherEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class LoadWeatherEvent extends WeatherEvent {
  final String cityName;

  LoadWeatherEvent(this.cityName);

  @override
  List<Object> get props => [cityName];

  @override
  String toString() => '[~] Load data for the city: $cityName';
}
