import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

import 'app.dart';
import 'data/bloc/simple_bloc_observer.dart';

Future main() async {
  await dotenv.load(fileName: '.env');
  Bloc.observer = SimpleBlocObserver();
  runApp(const MyApp());
}
