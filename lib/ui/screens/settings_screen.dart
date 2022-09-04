import 'dart:async';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:onesignal_flutter/onesignal_flutter.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:get_it/get_it.dart';
import 'package:package_info_plus/package_info_plus.dart';
import 'package:provider/provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/commons/app_builder.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/models/language.dart';
import 'package:directorio_delicias/notifiers/theme_notifier.dart';
import 'package:directorio_delicias/session/session_storage.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';

class SettingsScreen extends StatefulWidget {
  const SettingsScreen({Key? key}) : super(key: key);

  @override
  _SettingsScreenState createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  SessionStorage? sessionStorage;
  String projectCode = "1.1";

  @override
  void initState() {
    super.initState();
  }

  Future<SessionStorage> getData() async {
    try {
      PackageInfo packageInfo = await PackageInfo.fromPlatform();

      projectCode = packageInfo.buildNumber;
    } on PlatformException {
      projectCode = '1.1';
    }
    sessionStorage = GetIt.instance.get<SessionStorage>();
    return sessionStorage!;
  }

  Future<void> disablePush(val) async {
    await OneSignal.shared.disablePush(val);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        color: Theme.of(context).backgroundColor,
        child: Scaffold(
          body: root(),
        ));
  }

  Widget updateMain() {
    return FutureBuilder<SessionStorage>(
      future: getData(),
      builder: (BuildContext context, AsyncSnapshot<SessionStorage> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          return Expanded(child: getContent());
        }
      },
    );
  }

  Widget root() {
    return Container(
      color: Theme.of(context).backgroundColor,
      child: Column(
        children: <Widget>[getAppBarUI(), updateMain()],
      ),
    );
  }

  Widget getContent() {
    int indexLanguage = 0;
    for (int x = 0; x < Config.languageLocales.length; x++) {
      Language lang = Config.languageLocales[x];
      if (lang.locale == context.locale) {
        indexLanguage = x;
        break;
      }
    }

    return MediaQuery.removePadding(
      removeTop: true,
      context: context,
      child: ListView(
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.only(top: 20),
            child: Container(
              color: Theme.of(context).backgroundColor,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Container(
                    padding: EdgeInsets.only(left: 16, right: 16),
                    child: Text(
                      tr("app_settings"),
                      style: TextStyle(
                        fontSize: 15,
                        fontWeight: FontWeight.w600,
                        color: Theme.of(context).textTheme.caption?.color,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  ItemCard(
                    title: tr("nearby_radius"),
                    color: Theme.of(context).textTheme.bodyText1!.color!,
                    textColor: Theme.of(context).textTheme.subtitle1!.color!,
                    callback: () {},
                    rightWidget: Material(
                      color: Colors.transparent,
                      child: InkWell(
                        onTap: () {
                          Helpers.showRadiusDialog(
                              context: context,
                              onTap: (text) {},
                              initialValue:
                                  sessionStorage!.getRadius.toString(),
                              onTapOk: (radius) {
                                setState(() {
                                  sessionStorage
                                      ?.setRadius(double.parse(radius));
                                });
                              });
                        },
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Text(
                            sprintf("%.01f %s",
                                [sessionStorage?.getRadius, tr("km")]),
                            style: TextStyle(
                              fontSize: 15,
                              color:
                                  Theme.of(context).textTheme.subtitle1?.color,
                            ),
                          ),
                        ),
                      ),
                    ),
                  ),
                  Divider(
                    height: 1,
                  ),
                  ItemCard(
                    title: tr("push_notifications"),
                    color: Theme.of(context).textTheme.bodyText1!.color!,
                    textColor: Theme.of(context).textTheme.subtitle1!.color!,
                    callback: () {},
                    rightWidget: Checkbox(
                      activeColor: Theme.of(context).accentColor,
                      value: sessionStorage!.isPushEnabled,
                      onChanged: (val) {
                        setState(() {
                          sessionStorage?.setPushEnabled(val!);
                          disablePush(val);
                        });
                      },
                    ),
                  ),
                  Divider(
                    height: 1,
                  ),
                  ItemCard(
                    title: tr("language"),
                    color: Theme.of(context).textTheme.bodyText1!.color!,
                    textColor: Theme.of(context).textTheme.subtitle1!.color!,
                    callback: () {},
                    rightWidget: Material(
                      color: Colors.transparent,
                      child: InkWell(
                        onTap: () {
                          // Locale currentLocale = Locale('en', 'US');//Locale('ar', 'TN');
                          // ZoomDrawer.rtl = currentLocale.languageCode.toLowerCase() == "ar";
                          // context.locale = currentLocale;
                          // MyApp.selectedLocale = currentLocale;
                          Helpers.showLanguageDialog(
                              context: context,
                              initialValue: indexLanguage,
                              onTap: (index) {
                                Language lang = Config.languageLocales[index];
                                Locale currentLocale = lang.locale;
                                ZoomDrawer.rtl =
                                    currentLocale.languageCode.toLowerCase() ==
                                        "ar";
                                context.locale = currentLocale;
                                MyApp.selectedLocale = currentLocale;
                              },
                              onTapOk: () {
                                Provider.of<MenuProvider>(context,
                                        listen: false)
                                    .forceReload();
                              });
                        },
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Text(
                            Config.languageLocales[indexLanguage].name,
                            style: TextStyle(
                              fontSize: 15,
                              color:
                                  Theme.of(context).textTheme.subtitle1?.color,
                            ),
                          ),
                        ),
                      ),
                    ),
                  ),
                  Divider(
                    height: 1,
                  ),
                  SizedBox(
                    height: 40,
                  ),
                  Container(
                    padding: EdgeInsets.only(left: 16, right: 16),
                    child: Text(
                      tr("visuals"),
                      style: TextStyle(
                        fontSize: 15,
                        fontWeight: FontWeight.w600,
                        color: Theme.of(context).textTheme.caption?.color,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  ItemCard(
                    title: tr("dark_mode"),
                    color: Theme.of(context).textTheme.bodyText1!.color!,
                    textColor: Theme.of(context).textTheme.subtitle1!.color!,
                    callback: () {},
                    rightWidget: Checkbox(
                      activeColor: Theme.of(context).accentColor,
                      value: Provider.of<ThemeNotifier>(context, listen: false)
                          .isDarkMode,
                      onChanged: (bool? val) {
                        sessionStorage?.setDarkMode(val!);
                        Provider.of<ThemeNotifier>(context, listen: false)
                            .updateTheme(val!);
                        AppBuilder.of(context)?.rebuild();
                      },
                    ),
                  ),
                  SizedBox(
                    height: 40,
                  ),
                  Container(
                    padding: EdgeInsets.only(left: 16, right: 16),
                    child: Text(
                      tr("app_version"),
                      style: TextStyle(
                        fontSize: 15,
                        fontWeight: FontWeight.w600,
                        color: Theme.of(context).textTheme.caption?.color,
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  ItemCard(
                    title: tr("version"),
                    color: Theme.of(context).textTheme.bodyText1!.color!,
                    textColor: Theme.of(context).textTheme.subtitle1!.color!,
                    rightWidget: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Text(
                        "1.1.1",
                        style: TextStyle(
                          fontSize: 15,
                          color: Theme.of(context).textTheme.subtitle1!.color,
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget getAppBarUI() {
    return Container(
      decoration: BoxDecoration(
        color: Theme.of(context).backgroundColor,
      ),
      child: Padding(
        padding: EdgeInsets.only(
            top: MediaQuery.of(context).padding.top, left: 8, right: 8),
        child: Row(
          children: <Widget>[
            Container(
              alignment: AlignmentDirectional.centerStart,
              width: AppBar().preferredSize.height + 40,
              height: AppBar().preferredSize.height,
              child: Material(
                color: Colors.transparent,
                child: InkWell(
                  borderRadius: const BorderRadius.all(
                    Radius.circular(32.0),
                  ),
                  onTap: () {
                    ZoomDrawer.of(context).toggle();
                  },
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Icon(
                      Icons.menu,
                      color: Theme.of(context).appBarTheme.color,
                    ),
                  ),
                ),
              ),
            ),
            Expanded(
              child: Center(
                child: Text(
                  tr("settings"),
                  style: TextStyle(
                    fontWeight: FontWeight.w600,
                    fontSize: 22,
                    color: Theme.of(context).appBarTheme.color,
                  ),
                ),
              ),
            ),
            Container(
              width: AppBar().preferredSize.height + 40,
              height: AppBar().preferredSize.height,
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.end,
                children: <Widget>[],
              ),
            )
          ],
        ),
      ),
    );
  }
}

class ItemCard extends StatelessWidget {
  ItemCard(
      {this.title,
      this.color,
      this.rightWidget,
      this.callback,
      this.textColor});

  final Color? color;
  final Color? textColor;
  final String? title;
  final Widget? rightWidget;
  final Function()? callback;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      child: Container(
        height: 60,
        color: color,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            Container(
              padding: const EdgeInsets.only(left: 24, right: 24),
              child: Center(
                child: Text(
                  title!,
                  textAlign: TextAlign.start,
                  style: TextStyle(
                    fontWeight: FontWeight.w500,
                    fontSize: 14,
                    color: textColor,
                  ),
                ),
              ),
            ),
            rightWidget!
          ],
        ),
      ),
      onTap: callback,
    );
  }
}
