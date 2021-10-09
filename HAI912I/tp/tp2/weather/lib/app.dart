import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:weather/data/bloc/weather_bloc.dart';

import 'views/homepage.dart';

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MyWeather',
      theme: ThemeData(
        primarySwatch: Colors.deepPurple,
      ),
      home: BlocProvider<WeatherBloc>(
        create: (context) => WeatherBloc(),
        child: const MyHomePage(title: 'Weather'),
      ),
    );
  }
}
