import 'package:flutter/widgets.dart';

class MySpacer extends StatelessWidget {
  final double? size;

  const MySpacer({Key? key, this.size}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(height: size ?? 12);
  }
}
