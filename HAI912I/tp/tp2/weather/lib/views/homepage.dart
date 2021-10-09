import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather/data/bloc/weather_bloc.dart';
import 'package:weather/views/search.dart';
import 'package:weather/widgets/current_conditions.dart';
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
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: Column(children: <Widget>[
          Expanded(
            child: BlocBuilder<WeatherBloc, WeatherState>(
                builder: (context, state) {
              if (state is LoadingWeatherState) {
                return const Center(child: CircularProgressIndicator());
              } else if (state is LoadedWeatherState) {
                return Wrap(runSpacing: 50, children: [
                  CurrentConditions(state.weather.city!.name.toString(),
                      state.weather.list![0]),
                  ForecastHorizontalListWidget(state.weather)
                ]);
              } else if (state is FailedToLoadWeatherState) {
                // Make a snackbar instead of the text
                return Text('Error occured: ${state.error.message}');
              } else {
                return Container();
              }
            }),
          )
        ]),
        floatingActionButton: FloatingActionButton(
          child: const Icon(Icons.search),
          onPressed: () async {
            final city = await Navigator.of(context).push(SearchPage.route());
            if (city != null) {
              context.read<WeatherBloc>().add(LoadWeatherEvent(city));
            } else {
              // Display error message
            }
          },
        ));
  }
}
