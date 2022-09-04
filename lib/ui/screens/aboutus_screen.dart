import 'dart:async';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/commons/services.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';

class AboutUsPage extends StatefulWidget {
  const AboutUsPage({Key? key}) : super(key: key);

  @override
  _AboutUsPageState createState() => _AboutUsPageState();
}

class _AboutUsPageState extends State<AboutUsPage>
    with TickerProviderStateMixin {
  final double infoHeight = 364.0;
  late AnimationController animationController;
  late Animation<double> animation;
  double opacity1 = 0.0;
  double opacity2 = 0.0;
  double opacity3 = 0.0;
  double opacity4 = 0.0;

  @override
  void initState() {
    animationController = AnimationController(
        duration: const Duration(milliseconds: 1000), vsync: this);

    animation = Tween<double>(begin: 0.0, end: 1.0).animate(CurvedAnimation(
        parent: animationController,
        curve: Interval(0, 1.0, curve: Curves.fastOutSlowIn)));

    setData();
    super.initState();
  }

  Future<void> setData() async {
    animationController.forward();
    await Future<dynamic>.delayed(const Duration(milliseconds: 200));
    setState(() {
      opacity1 = 1.0;
    });
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<DataHandlerNotifier>(
        create: (context) => DataHandlerNotifier(),
        builder: (ctx, widget) {
          return root();
        });
  }

  Future<bool> getData() async {
    await Future<dynamic>.delayed(const Duration(milliseconds: 200));
    return true;
  }

  Widget root() {
    var left;
    var right;

    if (Helpers.isRTL()) {
      left = 35.0;
    } else {
      right = 35.0;
    }

    return Container(
        child: Column(children: <Widget>[
      Stack(
        children: <Widget>[
          Column(
            children: <Widget>[
              Container(
                color: Theme.of(context).accentColor,
                height: MediaQuery.of(context).size.height,
              ),
            ],
          ),
          Positioned(
            top: (MediaQuery.of(context).size.width / 3.0) - 24.0,
            bottom: 0,
            left: 0,
            right: 0,
            child: Container(
              decoration: BoxDecoration(
                color: Theme.of(context).backgroundColor,
                borderRadius: const BorderRadius.only(
                    topLeft: Radius.circular(32.0),
                    topRight: Radius.circular(32.0)),
                boxShadow: <BoxShadow>[
                  BoxShadow(
                      color: Theme.of(context).shadowColor,
                      offset: const Offset(1.1, 1.1),
                      blurRadius: 10.0),
                ],
              ),
              child: Padding(
                padding: const EdgeInsets.only(left: 8, right: 8, top: 40),
                child: SingleChildScrollView(
                  child: Container(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Padding(
                            padding: const EdgeInsets.only(
                                left: 16, right: 16, top: 8, bottom: 8),
                            child: Html(
                              data: tr("about_us_details"),
                              style: {
                                "*": Style(
                                  fontWeight: FontWeight.w200,
                                  fontSize: FontSize.em(14),
                                  letterSpacing: 0.27,
                                  color: Theme.of(context)
                                      .textTheme
                                      .caption!
                                      .color,
                                )
                              },
                            )),
                        SizedBox(height: MediaQuery.of(context).padding.bottom)
                      ],
                    ),
                  ),
                ),
              ),
            ),
          ),
          Positioned(
            top: (MediaQuery.of(context).size.width / 3.0) - 24.0 - 35,
            right: right,
            left: left,
            child: ScaleTransition(
              alignment: Alignment.center,
              scale: CurvedAnimation(
                  parent: animationController, curve: Curves.fastOutSlowIn),
              child: Card(
                color: Theme.of(context).accentColor,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(50.0)),
                elevation: 10.0,
                shadowColor: Theme.of(context).shadowColor,
                child: Consumer<DataHandlerNotifier>(
                  builder: (context, provider, child) => InkWell(
                    borderRadius: BorderRadius.circular(50.0),
                    child: Container(
                      width: 60,
                      height: 60,
                      child: Center(
                        child: Icon(
                          Icons.email,
                          color: Theme.of(context).indicatorColor,
                          size: 30,
                        ),
                      ),
                    ),
                    onTap: () {
                      GetIt.instance
                          .get<Services>()
                          .sendEmail(Config.CONTACT_EMAIL);
                    },
                  ),
                ),
              ),
            ),
          ),
          getAppBarUI(),
        ],
      )
    ]));
  }

  Widget getAppBarUI() {
    return Container(
      decoration: BoxDecoration(
        color: Colors.transparent,
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
                  tr("about_us"),
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
