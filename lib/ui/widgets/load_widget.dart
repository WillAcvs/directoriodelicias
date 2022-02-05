
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

class LoadingWidget extends StatelessWidget {
  final double size;
  final Color iconColor;

  const LoadingWidget({Key key, this.size, this.iconColor})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Theme.of(context).backgroundColor,
      child: Center(
        child: SpinKitWave(
          itemCount: 3,
          color: iconColor,
          size: size,
        ),
      ),
    );
  }
}