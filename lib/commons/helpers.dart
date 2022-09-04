import 'package:cached_network_image/cached_network_image.dart';
import 'package:day_night_time_picker/lib/daynight_timepicker.dart';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter_custom_dialog/flutter_custom_dialog.dart';
import 'package:intl/intl.dart';
import 'package:smooth_star_rating_nsafe/smooth_star_rating.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/models/language.dart';
import 'package:directorio_delicias/models/user.dart';
import '../main.dart';

class Helpers {
  static TimeOfDay formatTimeOfDay(String timeOfDay) {
    List<String> split = timeOfDay.split(":");
    if (split.length == 2) {
      int hr = int.parse(split[0]);
      int min = int.parse(split[1]);
      return TimeOfDay(hour: hr, minute: min);
    }
    return TimeOfDay(hour: 0, minute: 0);
  }

  // https://pub.dev/documentation/intl/latest/intl/DateFormat-class.html
  static String formatDateTime(String format) {
    final DateTime now = DateTime.now();
    final DateFormat formatter = DateFormat(format);
    return formatter.format(now);
  }

  static YYDialog showAlertDialog(
      {@required context, Function? onTapOk, title, message}) {
    return YYDialog().build(context)
      ..barrierDismissible = false
      ..barrierColor
      ..width = MediaQuery.of(context).size.width - 60
      ..borderRadius = 4.0
      ..backgroundColor = Theme.of(context).backgroundColor
      ..text(
        padding:
            EdgeInsets.only(left: 25.0, right: 25.0, bottom: 10.0, top: 20.0),
        alignment: AlignmentDirectional.topStart,
        text: title,
        color: Theme.of(context).textTheme.caption?.color,
        fontSize: 18.0,
        fontWeight: FontWeight.w500,
      )
      ..text(
        padding: EdgeInsets.only(left: 25.0, right: 25.0, bottom: 25.0),
        alignment: AlignmentDirectional.topStart,
        text: message,
        color: Theme.of(context).textTheme.caption?.color,
        fontSize: 16.0,
        fontWeight: FontWeight.w200,
      )
      ..doubleButton(
        padding: EdgeInsets.only(top: 10.0),
        gravity: Gravity.right,
        text2: tr("ok"),
        color2: Theme.of(context).accentColor,
        fontSize2: 16.0,
        fontWeight2: FontWeight.bold,
        onTap2: () {
          if (onTapOk != null) onTapOk();
        },
      )
      ..animatedFunc = (child, animation) {
        return FadeTransition(
            opacity: animation,
            child: Transform(
                transform: Matrix4.translationValues(
                    0.0, 50 * (1.0 - animation.value), 0.0),
                child: child));
      }
      ..show();
  }

  static YYDialog showDualButtonDialog(
      {context, Function? onTapOk, Function? onTapDismiss, title, message}) {
    return YYDialog().build(context)
      ..barrierDismissible = false
      ..barrierColor
      ..width = MediaQuery.of(context).size.width - 60
      ..borderRadius = 4.0
      ..backgroundColor = Theme.of(context).backgroundColor
      ..text(
        padding:
            EdgeInsets.only(left: 25.0, right: 25.0, bottom: 10.0, top: 20.0),
        alignment: AlignmentDirectional.topStart,
        text: title,
        color: Theme.of(context).textTheme.caption?.color,
        fontSize: 18.0,
        fontWeight: FontWeight.w500,
      )
      ..text(
        padding: EdgeInsets.only(left: 25.0, right: 25.0, bottom: 25.0),
        alignment: AlignmentDirectional.topStart,
        text: message,
        color: Theme.of(context).textTheme.caption?.color,
        fontSize: 16.0,
        fontWeight: FontWeight.w200,
      )
      ..divider()
      ..doubleButton(
        padding: EdgeInsets.only(top: 10.0),
        gravity: Gravity.right,
        withDivider: true,
        text2: tr("ok"),
        color2: Theme.of(context).accentColor,
        fontSize2: 16.0,
        fontWeight2: FontWeight.bold,
        onTap2: () {
          if (onTapOk != null) onTapOk();
        },
        text1: tr("cancel"),
        color1: Theme.of(context).accentColor,
        fontSize1: 16.0,
        fontWeight1: FontWeight.bold,
        onTap1: () {
          if (onTapDismiss != null) onTapDismiss();
        },
      )
      ..animatedFunc = (child, animation) {
        return FadeTransition(
            opacity: animation,
            child: Transform(
                transform: Matrix4.translationValues(
                    0.0, 50 * (1.0 - animation.value), 0.0),
                child: child));
      }
      ..show();
  }

  static YYDialog showRatingDialog(
      {context, Function(double)? onTapRate, Function? onTapOk}) {
    return YYDialog().build(context)
      ..barrierDismissible = false
      ..barrierColor
      ..width = MediaQuery.of(context).size.width - 100
      ..borderRadius = 4.0
      ..backgroundColor = Theme.of(context).backgroundColor
      ..widget(Container(
        color: Theme.of(context).accentColor,
        height: 50,
        child: Center(
          child: Text(
            tr("rate_store"),
            textAlign: TextAlign.center,
            style: TextStyle(
              fontWeight: FontWeight.w400,
              fontSize: 20,
              letterSpacing: 0.27,
              color: Colors.white,
            ),
          ),
        ),
      ))
      ..widget(Padding(
          padding: EdgeInsets.only(top: 20, bottom: 20),
          child: SmoothStarRating(
              allowHalfRating: false,
              onRatingChanged: (v) {
                if (onTapRate != null) onTapRate(v);
              },
              starCount: 5,
              rating: 0,
              size: 40.0,
              defaultIconData: Icons.star_outline_outlined,
              filledIconData: Icons.star_outlined,
              color: Theme.of(context).accentColor,
              borderColor: Theme.of(context).accentColor,
              spacing: 0.0)))
      ..divider()
      ..doubleButton(
        padding: EdgeInsets.only(top: 10.0),
        gravity: Gravity.right,
        withDivider: true,
        text2: tr("rate"),
        color2: Theme.of(context).accentColor,
        fontSize2: 16.0,
        fontWeight2: FontWeight.bold,
        onTap2: () {
          if (onTapOk != null) onTapOk();
        },
        text1: tr("cancel"),
        color1: Theme.of(context).accentColor,
        fontSize1: 16.0,
        fontWeight1: FontWeight.bold,
        onTap1: () {},
      )
      ..animatedFunc = (child, animation) {
        return FadeTransition(
            opacity: animation,
            child: Transform(
                transform: Matrix4.translationValues(
                    0.0, 50 * (1.0 - animation.value), 0.0),
                child: child));
      }
      ..show();
  }

  static String formatDate(int timestamp, String formatStr) {
    // var now = new DateTime.now();
    var format = new DateFormat(formatStr);
    var date = new DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);
    // var diff = date.difference(now);
    // var time = '';

    // if (diff.inSeconds <= 0 || diff.inSeconds > 0 && diff.inMinutes == 0 || diff.inMinutes > 0 && diff.inHours == 0 || diff.inHours > 0 && diff.inDays == 0) {
    //   time = format.format(date);
    // } else {
    //   if (diff.inDays == 1) {
    //     time = diff.inDays.toString() + 'DAY AGO';
    //   } else {
    //     time = diff.inDays.toString() + 'DAYS AGO';
    //   }
    // }

    return format.format(date);
  }

  static YYDialog showReviewDialog(
      {context, Function(String)? onTapReview, Function? onTapOk}) {
    TextEditingController controller = new TextEditingController();
    return YYDialog().build(context)
      ..barrierDismissible = false
      ..barrierColor
      ..width = MediaQuery.of(context).size.width - 100
      ..borderRadius = 4.0
      ..backgroundColor = Theme.of(context).backgroundColor
      ..widget(Container(
        color: Theme.of(context).accentColor,
        height: 50,
        child: Center(
          child: Text(
            tr("add_review"),
            textAlign: TextAlign.center,
            style: TextStyle(
              fontWeight: FontWeight.w400,
              fontSize: 20,
              letterSpacing: 0.27,
              color: Colors.white,
            ),
          ),
        ),
      ))
      ..widget(Padding(
        padding: EdgeInsets.only(top: 20, bottom: 20, left: 10, right: 10),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(5),
          child: Container(
            padding: const EdgeInsets.all(4.0),
            constraints: const BoxConstraints(minHeight: 80, maxHeight: 160),
            color: Theme.of(context).textTheme.bodyText1?.color,
            child: SingleChildScrollView(
              padding:
                  const EdgeInsets.only(left: 10, right: 10, top: 0, bottom: 0),
              child: TextFormField(
                maxLines: null,
                textInputAction: TextInputAction.done,
                controller: controller,
                onFieldSubmitted: (txt) {
                  if (onTapReview != null) onTapReview(txt);
                },
                onChanged: (txt) {
                  if (onTapReview != null) onTapReview(txt);
                },
                onEditingComplete: () {
                  FocusScope.of(context).requestFocus(FocusNode());
                },
                style: TextStyle(
                  fontFamily: Theme.of(context).textTheme.caption?.fontFamily,
                  fontSize: 16,
                  color: Theme.of(context).textTheme.caption?.color,
                ),
                cursorColor: Theme.of(context).accentColor,
                decoration: InputDecoration(
                    border: InputBorder.none, hintText: tr("your_review")),
              ),
            ),
          ),
        ),
      ))
      ..divider()
      ..doubleButton(
        padding: EdgeInsets.only(top: 10.0),
        gravity: Gravity.right,
        withDivider: true,
        text2: tr("send"),
        color2: Theme.of(context).accentColor,
        fontSize2: 16.0,
        fontWeight2: FontWeight.bold,
        onTap2: () {
          if (onTapOk != null) onTapOk();
        },
        text1: tr("cancel"),
        color1: Theme.of(context).accentColor,
        fontSize1: 16.0,
        fontWeight1: FontWeight.bold,
        onTap1: () {},
      )
      ..animatedFunc = (child, animation) {
        return FadeTransition(
            opacity: animation,
            child: Transform(
                transform: Matrix4.translationValues(
                    0.0, 50 * (1.0 - animation.value), 0.0),
                child: child));
      }
      ..show();
  }

  static Widget createHeader({text, context}) {
    return Container(
      padding: const EdgeInsets.only(top: 8),
      child: Text(
        text,
        textAlign: TextAlign.start,
        style: TextStyle(
          fontSize: 20,
          fontWeight: FontWeight.bold,
          color: Theme.of(context).textTheme.subtitle1?.color,
        ),
      ),
    );
  }

  static Widget createClickableText(
      {text = "00:00 - 23:59", @required context, onTap}) {
    return Padding(
        padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
        child: InkWell(
          onTap: onTap ?? onTap,
          child: Container(
              height: 50,
              decoration: BoxDecoration(
                color: Theme.of(context).textTheme.bodyText1?.color,
                borderRadius: BorderRadius.circular(8),
              ),
              child: Align(
                alignment: AlignmentDirectional.centerStart,
                child: Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    text,
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.w500,
                      color: Theme.of(context).textTheme.caption?.color,
                    ),
                  ),
                ),
              )),
        ));
  }

  static Widget createFormField(
      {controller,
      placeholder,
      validatorText,
      @required context,
      double height = 50,
      bool enabled = true}) {
    return Padding(
      padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1?.color,
          borderRadius: BorderRadius.circular(8),
        ),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(25),
          child: Container(
            padding: const EdgeInsets.all(4.0),
            height: height,
            child: Container(
              padding:
                  const EdgeInsets.only(left: 10, right: 10, top: 0, bottom: 0),
              child: SizedBox(
                child: TextFormField(
                  validator: (value) {
                    if (value!.isEmpty && validatorText != null) {
                      // return validatorText;
                      Helpers.showAlertDialog(
                          context: context,
                          message: validatorText,
                          title: tr("action_error"),
                          onTapOk: () {});
                    }
                    return null;
                  },
                  enabled: enabled,
                  expands: true,
                  controller: controller,
                  maxLines: null,
                  onChanged: (String txt) {},
                  style: TextStyle(
                    fontFamily: Theme.of(context).textTheme.caption?.fontFamily,
                    fontSize: 16,
                    color: Theme.of(context).textTheme.caption?.color,
                  ),
                  cursorColor: Theme.of(context).accentColor,
                  decoration: InputDecoration(
                      // isDense: true,
                      // contentPadding: EdgeInsets.fromLTRB(10, 10, 10, 0),
                      border: InputBorder.none,
                      hintText: placeholder),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  static Widget createFormFieldClickable(
      {@required didTap, placeholder, @required context}) {
    return Padding(
      padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1?.color,
          borderRadius: BorderRadius.circular(8),
        ),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(25),
          child: InkWell(
            onTap: () {
              if (didTap != null) didTap();
            },
            child: Container(
              padding: const EdgeInsets.all(4.0),
              height: 50,
              child: Container(
                padding: const EdgeInsets.only(
                    left: 10, right: 10, top: 0, bottom: 0),
                child: SizedBox(
                  height: 50,
                  child: Text(
                    placeholder,
                    maxLines: null,
                    style: TextStyle(
                      fontFamily:
                          Theme.of(context).textTheme.caption?.fontFamily,
                      fontSize: 16,
                      color: Theme.of(context).textTheme.caption?.color,
                    ),
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  static Widget createTimeRange(
      {required context,
      required TimeOfDay startTime,
      required TimeOfDay endTime,
      Function(TimeOfDay)? onTimeStartChanged,
      Function(TimeOfDay)? onTimeEndChanged}) {
    var formatStartTime = "00:00";
    if (startTime != null) {
      formatStartTime =
          sprintf("%02d:%02d", [startTime.hour, startTime.minute]);
    }

    var formatEndTime = "23:59";
    if (formatEndTime != null) {
      formatEndTime = sprintf("%02d:%02d", [endTime.hour, endTime.minute]);
    }

    return Row(
      children: [
        Expanded(
          child: Helpers.createClickableText(
              context: context,
              text: formatStartTime,
              onTap: () {
                Navigator.of(context).push(
                  showPicker(
                    accentColor: Theme.of(context).accentColor,
                    context: context,
                    value: startTime,
                    onChange: (time) => onTimeStartChanged!(time),
                    // Optional onChange to receive value as DateTime
                    onChangeDateTime: (DateTime dateTime) {
                      print(dateTime);
                    },
                  ),
                );
              }),
        ),
        SizedBox(
          width: 10,
        ),
        Expanded(
          child: Helpers.createClickableText(
              context: context,
              text: formatEndTime,
              onTap: () {
                Navigator.of(context).push(
                  showPicker(
                    accentColor: Theme.of(context).accentColor,
                    context: context,
                    value: endTime,
                    onChange: (time) => onTimeEndChanged!(time),
                    // Optional onChange to receive value as DateTime
                    onChangeDateTime: (DateTime dateTime) {
                      print(dateTime);
                    },
                  ),
                );
              }),
        ),
      ],
    );
  }

  static Widget loadCacheImage({imageUrl}) {
    return CachedNetworkImage(
      imageBuilder: (context, imageProvider) => Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: imageProvider,
            fit: BoxFit.cover,
          ),
        ),
      ),
      placeholder: (context, url) => Center(
          child: CircularProgressIndicator(
              valueColor:
                  AlwaysStoppedAnimation(Theme.of(context).accentColor))),
      errorWidget: (context, url, error) => Image.asset(
          Helpers.getThemedPhotoPlaceHolder(context),
          fit: BoxFit.cover),
      imageUrl: imageUrl,
    );
  }

  static Widget loadCacheImageThumb({imageUrl}) {
    return CachedNetworkImage(
      imageBuilder: (context, imageProvider) => Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: imageProvider,
            fit: BoxFit.cover,
          ),
        ),
      ),
      placeholder: (context, url) => Center(
          child: CircularProgressIndicator(
              valueColor:
                  AlwaysStoppedAnimation(Theme.of(context).accentColor))),
      errorWidget: (context, url, error) =>
          Image.asset(Config.DEFAULT_THUMB_PHOTO),
      imageUrl: imageUrl,
    );
  }

  static isDarkMode(context) {
    return Theme.of(context).brightness == Brightness.dark;
  }

  static YYDialog showRadiusDialog(
      {context,
      Function(String)? onTap,
      Function(String)? onTapOk,
      initialValue}) {
    TextEditingController controller = new TextEditingController();
    controller.text = initialValue;
    return YYDialog().build(context)
      ..barrierDismissible = false
      ..barrierColor
      ..width = MediaQuery.of(context).size.width - 100
      ..borderRadius = 4.0
      ..backgroundColor = Theme.of(context).backgroundColor
      ..widget(Container(
        color: Theme.of(context).accentColor,
        height: 50,
        child: Center(
          child: Text(
            tr("nearby_radius_in_km"),
            textAlign: TextAlign.center,
            style: TextStyle(
              fontWeight: FontWeight.w400,
              fontSize: 20,
              letterSpacing: 0.27,
              color: Colors.white,
            ),
          ),
        ),
      ))
      ..widget(Padding(
        padding: EdgeInsets.only(top: 20, bottom: 20, left: 10, right: 10),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(5),
          child: Container(
            padding: const EdgeInsets.all(4.0),
            color: Theme.of(context).textTheme.bodyText1?.color,
            child: SingleChildScrollView(
              padding:
                  const EdgeInsets.only(left: 10, right: 10, top: 0, bottom: 0),
              child: TextFormField(
                maxLines: 1,
                textInputAction: TextInputAction.done,
                controller: controller,
                keyboardType: TextInputType.number,
                onFieldSubmitted: (txt) {
                  if (onTap != null) onTap(txt);
                },
                onChanged: (txt) {
                  if (onTap != null) onTap(txt);
                },
                onEditingComplete: () {
                  FocusScope.of(context).requestFocus(FocusNode());
                },
                style: TextStyle(
                  fontFamily: Theme.of(context).textTheme.caption?.fontFamily,
                  fontSize: 16,
                  color: Theme.of(context).textTheme.caption?.color,
                ),
                cursorColor: Theme.of(context).accentColor,
                decoration: InputDecoration(
                    border: InputBorder.none,
                    hintText: tr("radius_in_km_placeholder")),
              ),
            ),
          ),
        ),
      ))
      ..divider()
      ..doubleButton(
        padding: EdgeInsets.only(top: 10.0),
        gravity: Gravity.right,
        withDivider: true,
        text2: tr("ok"),
        color2: Theme.of(context).accentColor,
        fontSize2: 16.0,
        fontWeight2: FontWeight.bold,
        onTap2: () {
          if (onTapOk != null) onTapOk(controller.text);
        },
        text1: tr("cancel"),
        color1: Theme.of(context).accentColor,
        fontSize1: 16.0,
        fontWeight1: FontWeight.bold,
        onTap1: () {},
      )
      ..animatedFunc = (child, animation) {
        return FadeTransition(
            opacity: animation,
            child: Transform(
                transform: Matrix4.translationValues(
                    0.0, 50 * (1.0 - animation.value), 0.0),
                child: child));
      }
      ..show();
  }

  static String getThemedPhotoPlaceHolder(context) {
    return Helpers.isDarkMode(context)
        ? Config.DEFAULT_EMPTY_PHOTO_PLACEHOLDER_DARK
        : Config.DEFAULT_EMPTY_PHOTO_PLACEHOLDER;
  }

  static String getThemedThumbPlaceHolder(context) {
    return Helpers.isDarkMode(context)
        ? Config.DEFAULT_THUMB_PHOTO_DARK
        : Config.DEFAULT_THUMB_PHOTO;
  }

  static bool isRTL() {
    print("isRTL=" + MyApp.selectedLocale!.languageCode.toString());
    return MyApp.selectedLocale!.languageCode.toLowerCase() == "ar";
  }

  static YYDialog showLanguageDialog(
      {context, Function(int)? onTap, Function? onTapOk, initialValue}) {
    List<RadioItem> radioItems = <RadioItem>[];

    for (Language lang in Config.languageLocales) {
      var radioItem = RadioItem(
        padding: EdgeInsets.only(left: 6.0),
        text: lang.name,
        color: Theme.of(context).textTheme.bodyText1?.color,
        fontSize: 16.0,
      );
      radioItems.add(radioItem);
    }

    return YYDialog().build(context)
      ..barrierDismissible = false
      ..barrierColor
      ..width = MediaQuery.of(context).size.width - 100
      ..borderRadius = 4.0
      ..backgroundColor = Theme.of(context).backgroundColor
      ..widget(Container(
        color: Theme.of(context).accentColor,
        height: 50,
        child: Center(
          child: Text(
            tr("app_language"),
            textAlign: TextAlign.center,
            style: TextStyle(
              fontWeight: FontWeight.w400,
              fontSize: 20,
              letterSpacing: 0.27,
              color: Colors.white,
            ),
          ),
        ),
      ))
      ..listViewOfRadioButton(
          items: radioItems,
          height: 370,
          intialValue: initialValue,
          color: Colors.transparent,
          activeColor: Theme.of(context).accentColor,
          onClickItemListener: (index) {
            onTap!(index);
          })
      ..divider()
      ..doubleButton(
        padding: EdgeInsets.only(top: 10.0),
        gravity: Gravity.right,
        text2: tr("done"),
        color2: Theme.of(context).accentColor,
        fontSize2: 16.0,
        fontWeight2: FontWeight.bold,
        onTap2: () {
          onTapOk!();
        },
      )
      ..animatedFunc = (child, animation) {
        return FadeTransition(
            opacity: animation,
            child: Transform(
                transform: Matrix4.translationValues(
                    0.0, 50 * (1.0 - animation.value), 0.0),
                child: child));
      }
      ..show();
  }

  static bool checkIfLoggedSocial() {
    User? loggedUser = MyApp.loggedUser;
    if (loggedUser == null) return false;

    if (loggedUser.facebookId != null && loggedUser.facebookId.length > 0)
      return true;

    if (loggedUser.twitterId != null && loggedUser.twitterId.length > 0)
      return true;

    return false;
  }
}
