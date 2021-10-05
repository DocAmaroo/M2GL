import 'package:flutter/material.dart';

class MyAppBar extends StatelessWidget with PreferredSizeWidget {
  final Widget? title;
  final List<Widget>? actions;

  const MyAppBar({Key? key, this.title, this.actions}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: title ?? const Text("MyAppBar"),
      flexibleSpace: Container(
          decoration: const BoxDecoration(
              gradient: LinearGradient(
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                  colors: <Color>[Colors.deepPurple, Colors.indigo]))),
      actions: actions ?? [],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(60.0);
}
