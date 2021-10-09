import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:weather/data/models/weather_model.dart';
import 'package:weather/data/repositories/weather_repository.dart';
import 'package:weather/errors/http_exception.dart' as http_exception;

part 'weather_event.dart';
part 'weather_state.dart';

class WeatherBloc extends Bloc<WeatherEvent, WeatherState> {
  final weatherRepository = WeatherRepository();

  WeatherBloc() : super(WeatherInitial()) {
    on<LoadWeatherEvent>((event, emit) => _onLoadWeatherEvent(event, emit));
  }

  Future<void> _onLoadWeatherEvent(
      LoadWeatherEvent event, Emitter<WeatherState> emit) async {
    emit(LoadingWeatherState());
    try {
      final weather =
          await weatherRepository.getWeatherForecast(event.cityName);
      emit(LoadedWeatherState(weather: weather));
    } on http_exception.HTTPException catch (e) {
      emit(FailedToLoadWeatherState(error: e));
    }
  }
}
