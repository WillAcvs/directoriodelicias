
import 'package:flutter/widgets.dart';

class RowPlaceholder extends StatelessWidget {
  final int color;

  const RowPlaceholder({Key key, this.color}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 15,
      decoration: BoxDecoration(
        color: Color(color),
        borderRadius: BorderRadius.all(
          Radius.circular(20),
        ),
      ),
    );
  }
}