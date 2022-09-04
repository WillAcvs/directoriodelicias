import 'package:flutter/widgets.dart';

class IconTile extends StatelessWidget {
  final double? width;
  final double? height;
  final IconData? iconData;
  final Color? backgroundColor;
  final Color? iconColor;

  const IconTile(
      {Key? key,
      this.width,
      this.height,
      this.iconData,
      this.backgroundColor,
      this.iconColor})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width,
      height: height,
      decoration: BoxDecoration(
        color: backgroundColor,
        borderRadius: BorderRadius.all(
          Radius.circular(height!),
        ),
      ),
      child: Icon(
        iconData,
        color: iconColor == null ? Color(0xFFFFFFFF) : iconColor,
      ),
    );
  }
}
