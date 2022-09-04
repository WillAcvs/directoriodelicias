// ignore_for_file: import_of_legacy_library_into_null_safe

import 'dart:async';
import 'package:animated_stack/animated_stack.dart';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/commons/rating_services.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/models/fave.dart';
import 'package:directorio_delicias/models/photo.dart';
import 'package:directorio_delicias/models/store.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';

import 'package:directorio_delicias/ui/screens/image_viewer_screen.dart';
import 'package:directorio_delicias/ui/screens/reviews_screen.dart';
import 'package:directorio_delicias/ui/screens/store_edit_screen.dart';
import 'package:directorio_delicias/ui/widgets/detail_column.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';

class DetailScreen extends StatefulWidget {
  const DetailScreen({Key? key, required this.store, this.willEdit = false})
      : super(key: key);
  final Store store;
  final bool willEdit;

  @override
  _DetailScreenState createState() => _DetailScreenState();
}

class _DetailScreenState extends State<DetailScreen>
    with TickerProviderStateMixin {
  final double infoHeight = 364.0;
  late AnimationController animationController;
  late Animation<double> animation;
  double opacity1 = 1.0;
  late GoogleMapController ctrl;
  late DataHandler dataHandler;
  double ratingValue = 0;
  late Widget rootWidget;

  @override
  void initState() {
    animationController = AnimationController(
        duration: const Duration(milliseconds: 1000), vsync: this);

    animation = Tween<double>(begin: 0.0, end: 1.0).animate(CurvedAnimation(
        parent: animationController,
        curve: Interval(0, 1.0, curve: Curves.fastOutSlowIn)));

    animationController.forward();
    super.initState();
  }

  Future<bool> getData(int millis) async {
    await Future<dynamic>.delayed(Duration(milliseconds: millis));
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<DataHandlerNotifier>(
        create: (context) => DataHandlerNotifier(),
        builder: (ctx, widget) {
          return root();
        });
  }

  Widget root() {
    rootWidget = AnimatedStack(
      backgroundColor: Theme.of(context).canvasColor,
      fabBackgroundColor: Theme.of(context).accentColor,
      buttonIcon: Icons.keyboard_arrow_down_outlined,
      foregroundWidget: ClipRRect(
          borderRadius: BorderRadius.all(Radius.circular(8)),
          child: Container(
            decoration: BoxDecoration(
              color: Theme.of(context).backgroundColor,
            ),
            child: FutureBuilder<bool>(
              future: getData(300),
              builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
                if (!snapshot.hasData) {
                  return const Center();
                } else {
                  return loadMain();
                }
              },
            ),
          )),
      columnWidget: DetailColumn(store: widget.store),
      bottomWidget: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).accentColor,
          borderRadius: BorderRadius.all(Radius.circular(50)),
        ),
        width: 180,
        height: 50,
        child: InkWell(
          child: Center(
            heightFactor: 1,
            child: Text(
              tr("rate_store"),
              overflow: TextOverflow.ellipsis,
              maxLines: 1,
              textAlign: TextAlign.center,
              style: TextStyle(
                fontWeight: FontWeight.w600,
                fontSize: 16,
                letterSpacing: 0.27,
                color:
                    Theme.of(context).floatingActionButtonTheme.foregroundColor,
              ),
            ),
          ),
          onTap: () {
            RatingServices.rate(context: context, store: widget.store);
          },
        ),
      ),
    );
    return rootWidget;
  }

  Widget loadMain() {
    return FutureBuilder<Widget>(
      future: main(),
      builder: (BuildContext context, AsyncSnapshot<Widget> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          return snapshot.data!;
        }
      },
    );
  }

  Future<Widget> main() async {
    String photoUrl = "";
    if (widget.store.photos!.length > 0) {
      photoUrl = widget.store.photos![0].thumbUrl;
    }

    List<String> imageUrls = <String>[];
    for (Photo photo in widget.store.photos!) {
      if (photo.photoUrl != null) imageUrls.add(photo.photoUrl);
      print(photo.photoUrl);
    }

    var photoLeft;
    var photoRight;

    var reviewsLeft;
    var reviewsRight;

    var faveLeft;
    var faveRight;

    if (Helpers.isRTL()) {
      photoRight = 20.0;
      faveLeft = 35.0;
      reviewsLeft = 100.0;
    } else {
      photoLeft = 20.0;
      faveRight = 35.0;
      reviewsRight = 100.0;
    }

    return Container(
      color: Theme.of(context).backgroundColor,
      child: Scaffold(
        backgroundColor: Colors.transparent,
        body: Stack(
          children: <Widget>[
            Column(
              children: <Widget>[
                AspectRatio(
                    aspectRatio: 1.2,
                    child: Helpers.loadCacheImage(imageUrl: photoUrl)),
              ],
            ),
            Positioned(
              top: (MediaQuery.of(context).size.width / 1.2) - 100.0,
              left: photoLeft,
              right: photoRight,
              child: ScaleTransition(
                alignment: Alignment.center,
                scale: CurvedAnimation(
                    parent: animationController, curve: Curves.fastOutSlowIn),
                child: FloatingActionButton.extended(
                  heroTag: "btnPhotos",
                  onPressed: () {
                    Navigator.push<dynamic>(
                      context,
                      ImageViewerPageRoute(
                        builder: (BuildContext context) =>
                            ImageViewerScreen(photos: widget.store.photos!),
                      ),
                    );
                  },
                  foregroundColor: Theme.of(context)
                      .floatingActionButtonTheme
                      .foregroundColor,
                  label: Text(widget.store.photos!.length.toString()),
                  icon: Icon(Icons.photo_rounded),
                  backgroundColor: Theme.of(context).accentColor,
                ),
              ),
            ),
            Positioned(
              top: (MediaQuery.of(context).size.width / 1.2) - 24.0,
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
                  padding: const EdgeInsets.only(left: 8, right: 8, top: 20),
                  child: SingleChildScrollView(
                    child: Container(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Padding(
                            padding: const EdgeInsets.only(
                                top: 12.0, left: 18, right: 16),
                            child: Text(
                              widget.store.storeName,
                              overflow: TextOverflow.ellipsis,
                              maxLines: 2,
                              textAlign: TextAlign.start,
                              style: TextStyle(
                                  fontWeight: FontWeight.w600,
                                  fontSize: 22,
                                  letterSpacing: 0.27,
                                  color: Theme.of(context)
                                      .textTheme
                                      .caption
                                      ?.color),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(
                                left: 16, right: 16, bottom: 8, top: 16),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: <Widget>[
                                Flexible(
                                    child: Text(
                                  widget.store.storeAddress,
                                  overflow: TextOverflow.ellipsis,
                                  maxLines: 2,
                                  textAlign: TextAlign.start,
                                  style: TextStyle(
                                    fontWeight: FontWeight.w200,
                                    fontSize: 22,
                                    letterSpacing: 0.27,
                                    color: Theme.of(context).accentColor,
                                  ),
                                )),
                                Container(
                                  child: Row(
                                    children: <Widget>[
                                      Text(
                                        sprintf(
                                            "%.1f", [widget.store.ratingAve]),
                                        textAlign: TextAlign.start,
                                        style: TextStyle(
                                          fontWeight: FontWeight.w200,
                                          fontSize: 22,
                                          letterSpacing: 0.27,
                                          color: Theme.of(context)
                                              .textTheme
                                              .subtitle1
                                              ?.color,
                                        ),
                                      ),
                                      Icon(
                                        Icons.star,
                                        color: Theme.of(context).accentColor,
                                        size: 24,
                                      ),
                                    ],
                                  ),
                                )
                              ],
                            ),
                          ),
                          AnimatedOpacity(
                            duration: const Duration(milliseconds: 500),
                            opacity: opacity1,
                            child: Padding(
                                padding: const EdgeInsets.all(8),
                                child: SingleChildScrollView(
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    mainAxisSize: MainAxisSize.min,
                                    children: <Widget>[
                                      getTimeBoxUI(),
                                    ],
                                  ),
                                )),
                          ),
                          Padding(
                              padding: const EdgeInsets.only(
                                  left: 16, right: 16, top: 8, bottom: 8),
                              child: Html(
                                data: widget.store.storeDesc,
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
                          SizedBox(
                            height: 240,
                            child: Padding(
                              padding: const EdgeInsets.all(20),
                              child: snapShot(),
                              // child: imageBytes != null ? snapShot() : delayGoogleMap(),
                            ),
                          ),
                          SizedBox(
                            height: MediaQuery.of(context).padding.bottom,
                          )
                        ],
                      ),
                    ),
                  ),
                ),
              ),
            ),
            Positioned(
              top: (MediaQuery.of(context).size.width / 1.2) - 24.0 - 35,
              right: faveRight,
              left: faveLeft,
              child: ScaleTransition(
                alignment: Alignment.center,
                scale: CurvedAnimation(
                    parent: animationController, curve: Curves.fastOutSlowIn),
                child: Card(
                  color: Theme.of(context).accentColor,
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(50.0)),
                  elevation: 10.0,
                  child: Consumer<DataHandlerNotifier>(
                    builder: (context, provider, child) => InkWell(
                      borderRadius: BorderRadius.circular(50.0),
                      child: Container(
                        width: 60,
                        height: 60,
                        child: Center(
                          child: FutureBuilder<Fave?>(
                            future: provider.isFave(widget.store.storeId),
                            builder: (BuildContext context,
                                AsyncSnapshot<Fave?> snapshot) {
                              if (!snapshot.hasData) {
                                return Icon(
                                  Icons.favorite_border_outlined,
                                  color: Theme.of(context)
                                      .floatingActionButtonTheme
                                      .foregroundColor,
                                  size: 30,
                                );
                              } else {
                                if (snapshot != null) {
                                  return Icon(
                                    Icons.favorite,
                                    color: Theme.of(context)
                                        .floatingActionButtonTheme
                                        .foregroundColor,
                                    size: 30,
                                  );
                                } else {
                                  return Icon(
                                    Icons.favorite_border_outlined,
                                    color: Theme.of(context)
                                        .floatingActionButtonTheme
                                        .foregroundColor,
                                    size: 30,
                                  );
                                }
                              }
                            },
                          ),
                        ),
                      ),
                      onTap: () {
                        provider.toggleFave(widget.store.storeId);
                      },
                    ),
                  ),
                ),
              ),
            ),
            Positioned(
              top: (MediaQuery.of(context).size.width / 1.2) - 24.0 - 35,
              right: reviewsRight,
              left: reviewsLeft,
              child: ScaleTransition(
                alignment: Alignment.center,
                scale: CurvedAnimation(
                    parent: animationController, curve: Curves.fastOutSlowIn),
                child: Card(
                    color: Theme.of(context).accentColor,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(60.0)),
                    elevation: 10.0,
                    child: InkWell(
                      borderRadius: BorderRadius.circular(60.0),
                      child: Container(
                        width: 60,
                        height: 60,
                        child: Center(
                          child: Icon(
                            Icons.rate_review,
                            color: Theme.of(context)
                                .floatingActionButtonTheme
                                .foregroundColor,
                            size: 30,
                          ),
                        ),
                      ),
                      onTap: () {
                        Navigator.push<dynamic>(
                          context,
                          MaterialPageRoute<dynamic>(
                            builder: (BuildContext context) => ReviewsScreen(
                                store: widget.store,
                                loggedUser: MyApp.loggedUser!),
                          ),
                        );
                      },
                    )),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(top: MediaQuery.of(context).padding.top),
              child: SizedBox(
                width: AppBar().preferredSize.height,
                height: AppBar().preferredSize.height,
                child: Material(
                  color: Colors.transparent,
                  child: InkWell(
                    borderRadius:
                        BorderRadius.circular(AppBar().preferredSize.height),
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        decoration: BoxDecoration(
                          color: Theme.of(context).accentColor,
                          borderRadius: BorderRadius.all(
                            Radius.circular(20),
                          ),
                        ),
                        child: Icon(
                          Icons.arrow_back,
                          color: Theme.of(context).appBarTheme.color,
                        ),
                      ),
                    ),
                    onTap: () {
                      Navigator.pop(context);
                    },
                  ),
                ),
              ),
            ),
            widgetEdit()
          ],
        ),
      ),
    );
  }

  Widget widgetEdit() {
    if (!widget.willEdit) return Container(height: 1);

    return Align(
      alignment: AlignmentDirectional.topEnd,
      child: Padding(
        padding: EdgeInsets.only(
            top: MediaQuery.of(context).padding.top + 10, right: 10, left: 10),
        child: SizedBox(
          width: 40,
          height: 40,
          child: Material(
            color: Colors.transparent,
            child: InkWell(
              borderRadius:
                  BorderRadius.circular(AppBar().preferredSize.height),
              child: Card(
                color: Theme.of(context).accentColor,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(50.0)),
                elevation: 10.0,
                child: Icon(Icons.edit,
                    size: 20,
                    color: Theme.of(context)
                        .floatingActionButtonTheme
                        .foregroundColor),
              ),
              onTap: () async {
                DataHandler dataHandler = await Navigator.push<dynamic>(
                  context,
                  MaterialPageRoute<dynamic>(
                      builder: (BuildContext context) => StoreEditScreen(
                            store: widget.store,
                          ),
                      fullscreenDialog: true),
                );

                if (dataHandler != null) {
                  Navigator.pop(context, dataHandler);
                }
              },
            ),
          ),
        ),
      ),
    );
  }

  Widget snapShot() {
    String darkModeUrl = Helpers.isDarkMode(context)
        ? Config.STATIC_DARK_GOOGLE_MAPS_URL
        : Config.STATIC_GOOGLE_MAPS_URL;

    return ClipRRect(
        borderRadius: BorderRadius.all(Radius.circular(10)),
        child: Container(
          decoration: BoxDecoration(color: Theme.of(context).shadowColor),
          height: 180,
          width: MediaQuery.of(context).size.width,
          child: Helpers.loadCacheImage(
              imageUrl: sprintf(darkModeUrl, [
            widget.store.lon,
            widget.store.lat,
            widget.store.lon,
            widget.store.lat,
          ])),
        ));
  }

  Duration? getDuration(String str) {
    List<String> split = str.split(":");
    if (split.length <= 1) return null;

    int hr = int.parse(split[0]);
    int min = int.parse(split[1]);
    return Duration(hours: hr, minutes: min);
  }

  String formatDuration(Duration duration) {
    int hr = duration.inHours;
    String ampm = "AM";
    if (duration.inHours > 12) {
      hr = duration.inHours - 12;
      ampm = "PM";
    }
    int min = duration.inMinutes % 60;
    return sprintf("%02d:%02d %s", [hr, min, ampm]);
  }

  Widget getTimeBoxUI() {
    String currDay = DateFormat('EEEE').format(DateTime.now());
    String openStr = tr("close_now");
    String timeStrStr = "";
    Duration? dtOpen;
    Duration? dtClose;
    if (currDay.compareTo("Monday") == 0) {
      dtOpen = getDuration(widget.store.monOpen);
      dtClose = getDuration(widget.store.monClose);
    } else if (currDay.compareTo("Tuesday") == 0) {
      dtOpen = getDuration(widget.store.tueOpen);
      dtClose = getDuration(widget.store.tueClose);
    } else if (currDay.compareTo("Wednesday") == 0) {
      dtOpen = getDuration(widget.store.wedOpen);
      dtClose = getDuration(widget.store.wedClose);
    } else if (currDay.compareTo("Thursday") == 0) {
      dtOpen = getDuration(widget.store.thuOpen);
      dtClose = getDuration(widget.store.thuClose);
    } else if (currDay.compareTo("Friday") == 0) {
      dtOpen = getDuration(widget.store.friOpen);
      dtClose = getDuration(widget.store.friClose);
    } else if (currDay.compareTo("Saturday") == 0) {
      dtOpen = getDuration(widget.store.satOpen);
      dtClose = getDuration(widget.store.satClose);
    } else {
      dtOpen = getDuration(widget.store.sunOpen);
      dtClose = getDuration(widget.store.sunClose);
    }

    DateTime dtCurr = DateTime.now();
    Duration durationCurr =
        Duration(hours: dtCurr.hour, minutes: dtCurr.minute);
    timeStrStr = sprintf(
        " | %s - %s", [formatDuration(dtOpen!), formatDuration(dtClose!)]);

    Color color = Theme.of(context).errorColor;
    if (durationCurr < dtClose && durationCurr > dtOpen) {
      openStr = tr("open");
      color = Theme.of(context).accentColor;
    }

    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1!.color,
          borderRadius: const BorderRadius.all(Radius.circular(16.0)),
          boxShadow: <BoxShadow>[
            BoxShadow(
                color: Theme.of(context).shadowColor,
                offset: const Offset(1.1, 1.1),
                blurRadius: 8.0),
          ],
        ),
        child: Padding(
          padding: const EdgeInsets.only(
              left: 18.0, right: 18.0, top: 12.0, bottom: 12.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Text(
                openStr,
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontWeight: FontWeight.w600,
                  fontSize: 14,
                  letterSpacing: 0.27,
                  color: color,
                ),
              ),
              SizedBox(width: 10),
              Text(
                timeStrStr,
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontWeight: FontWeight.w200,
                  fontSize: 14,
                  letterSpacing: 0.27,
                  color: Theme.of(context).textTheme.caption!.color,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
