import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather/data/bloc/weather_bloc.dart';
import 'package:weather/views/search.dart';
import 'package:weather/widgets/current_conditions.dart';
import 'package:weather/widgets/custom_app_bar.dart';
import 'package:weather/widgets/forecast_horizontal_list_widget.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: MyAppBar(title: widget.title),
        body: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Column(children: <Widget>[
              BlocListener<WeatherBloc, WeatherState>(
                  listener: (context, state) {
                if (state is FailedToLoadWeatherState) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                      backgroundColor: Colors.red,
                      content: Text('Sorry, cannot find this city'),
                    ),
                  );
                }
              }, child: BlocBuilder<WeatherBloc, WeatherState>(
                      builder: (context, state) {
                if (state is LoadingWeatherState) {
                  return const Center(
                      child: CircularProgressIndicator(color: Colors.black));
                } else if (state is LoadedWeatherState) {
                  return _getLoadedContent(state);
                } else if (state is FailedToLoadWeatherState) {
                  return Text('Error occured: ${state.error.message}');
                } else {
                  return const Center(
                    child: Text('Choissisez une localisation...',
                        style: TextStyle(
                            fontWeight: FontWeight.w400,
                            fontStyle: FontStyle.italic)),
                  );
                }
              }))
            ]),
          ),
        ),
        floatingActionButton: _getFloatingActionButton());
  }

  Wrap _getLoadedContent(LoadedWeatherState state) {
    return Wrap(runSpacing: 20, children: [
      _getTitle('Météo actuelle'),
      CurrentConditions(
          state.weather.city!.name.toString(), state.weather.list![0]),
      _getTitle('Prévision 5 jours'),
      ForecastHorizontalListWidget(state.weather)
    ]);
  }

  Container _getTitle(String title) {
    return Container(
      margin: const EdgeInsets.only(top: 16),
      child: Text(title,
          style: const TextStyle(fontSize: 30, fontWeight: FontWeight.bold)),
    );
  }

  FloatingActionButton _getFloatingActionButton() {
    return FloatingActionButton(
      child: Container(
        width: double.infinity,
        height: double.infinity,
        child: const Icon(Icons.search),
        decoration: BoxDecoration(
            shape: BoxShape.circle,
            gradient: LinearGradient(
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
                colors: <Color>[Colors.orange.shade400, Colors.pink.shade400])),
      ),
      onPressed: () async {
        final city = await Navigator.of(context).push(SearchPage.route());
        if (city != null) {
          context.read<WeatherBloc>().add(LoadWeatherEvent(city));
        } else {
          // Display error message
        }
      },
    );
  }
}
