import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather/data/bloc/weather_bloc.dart';
import 'package:weather/views/search.dart';

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
        body: Center(
          child: Column(mainAxisAlignment: MainAxisAlignment.center, children: <
              Widget>[
            BlocBuilder<WeatherBloc, WeatherState>(builder: (context, state) {
              if (state is LoadingWeatherState) {
                return const Center(child: CircularProgressIndicator());
              } else if (state is LoadedWeatherState) {
                return Column(children: [
                  Image(
                      image: NetworkImage(
                          state.weather.list![0].weather![0].iconUrl)),
                  Text(state.weather.city!.name.toString(),
                      style: const TextStyle(
                          fontSize: 30, fontWeight: FontWeight.bold)),
                  Text(
                      state.weather.list![0].weather![0].description.toString(),
                      style: const TextStyle(
                          fontSize: 14, fontStyle: FontStyle.italic))
                ]);
              } else if (state is FailedToLoadWeatherState) {
                return Text('Error occured: ${state.error}');
              } else {
                return Container();
              }
            }),
          ]),
        ),
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
