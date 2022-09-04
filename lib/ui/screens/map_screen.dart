import 'dart:async';

import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:geolocator/geolocator.dart';
import 'package:get_it/get_it.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/commons/services.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';
import 'package:directorio_delicias/ui/widgets/slider_view.dart';
import 'package:directorio_delicias/ui/widgets/tab_header.dart';
import 'detail_screen.dart';

class MapPage extends StatefulWidget {
  const MapPage({Key? key}) : super(key: key);

  @override
  _MapPageState createState() => _MapPageState();
}

class _MapPageState extends State<MapPage> {
  final ScrollController _scrollController = ScrollController();
  bool isNearMe = false;
  Widget? root;
  bool hasData = false;
  GoogleMapController? _controller;
  Position? position;
  String? _darkMapStyle;
  String? _lightMapStyle;

  @override
  void initState() {
    super.initState();
    rootBundle.loadString('assets/maps/dark_map_style.json').then((string) {
      _darkMapStyle = string;
    });
    rootBundle.loadString('assets/maps/light_map_style.json').then((string) {
      _lightMapStyle = string;
    });
  }

  Future<bool> getData(int millis) async {
    await Future<dynamic>.delayed(Duration(milliseconds: millis));
    return true;
  }

  @override
  void dispose() {
    super.dispose();
  }

  Future<bool> getLocation() async {
    position = await MyApp.determinePosition();
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<DataHandlerNotifier>(
        create: (context) => DataHandlerNotifier(),
        builder: (ctx, widget) {
          return Container(
            child: Scaffold(
              backgroundColor: Theme.of(context).backgroundColor,
              body: FutureBuilder<bool>(
                future: getData(200),
                builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
                  if (!snapshot.hasData) {
                    return const SizedBox();
                  } else {
                    return showMain();
                  }
                },
              ),
            ),
          );
        });
  }

  Widget getDataFromServer() {
    return FutureBuilder<DataHandler>(
      future: GetIt.instance.get<DataParser>().fetchStoresMap(),
      builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          DataHandler? dataHandler = snapshot.data;
          hasData = true;
          Provider.of<DataHandlerNotifier>(context, listen: false)
              .setDataHandler(dataHandler);
          return showList();
        }
      },
    );
  }

  Widget delayBeforeFetch() {
    return FutureBuilder<bool>(
      future: getData(500),
      builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          return getDataFromServer();
        }
      },
    );
  }

  Widget showList() {
    final CameraPosition cameraPosition = CameraPosition(
      target: LatLng(Config.DEFAULT_LAT, Config.DEFAULT_LON),
      zoom: 10,
    );

    return Column(
      children: [
        SizedBox(
          height: 50,
          child: NestedScrollView(
            controller: _scrollController,
            headerSliverBuilder:
                (BuildContext context, bool innerBoxIsScrolled) {
              return <Widget>[
                SliverPersistentHeader(
                  pinned: true,
                  floating: true,
                  delegate: ContestTabHeader(
                    getFilterBarUI(),
                  ),
                ),
              ];
            },
            body: SizedBox(height: 1),
          ),
        ),
        Expanded(
          child: Stack(children: <Widget>[
            Container(
              height: (MediaQuery.of(context).size.height / 1.2) - 230,
              color: Theme.of(context).backgroundColor,
              child: Consumer<DataHandlerNotifier>(
                builder: (context, provider, child) => GoogleMap(
                    gestureRecognizers: <Factory<OneSequenceGestureRecognizer>>[
                      new Factory<OneSequenceGestureRecognizer>(
                        () => new EagerGestureRecognizer(),
                      ),
                    ].toSet(),
                    compassEnabled: true,
                    myLocationEnabled: true,
                    mapToolbarEnabled: true,
                    markers: provider.markers,
                    myLocationButtonEnabled: false,
                    mapType: MapType.normal,
                    initialCameraPosition: cameraPosition,
                    onMapCreated: (GoogleMapController controller) {
                      _controller = controller;
                      if (Helpers.isDarkMode(context)) {
                        controller.setMapStyle(_darkMapStyle);
                      } else {
                        controller.setMapStyle(_lightMapStyle);
                      }

                      Future.delayed(Duration(milliseconds: 200), () {
                        provider.setMakers();
                      });

                      Future.delayed(Duration(milliseconds: 500), () {
                        List<LatLng> list = <LatLng>[];
                        for (Marker marker in provider.markers) {
                          list.add(marker.position);
                        }
                        _controller?.moveCamera(
                          CameraUpdate.newLatLngBounds(
                              Services().boundsFromLatLngList(list), 40),
                        );
                      });
                    }),
              ),
            ),
            Positioned(
                top: (MediaQuery.of(context).size.height / 1.2) - 280,
                bottom: 0,
                left: 0,
                right: 0,
                child: getCarouselUI()),
          ]),
        ),
      ],
    );
  }

  Widget showMain() {
    root = Container(
        child: Column(children: <Widget>[
      getAppBarUI(),
      FutureBuilder<bool>(
        future: getLocation(),
        builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
          if (!snapshot.hasData) {
            return const SizedBox();
          } else {
            return Expanded(child: delayBeforeFetch());
          }
        },
      )
    ]));
    return root!;
  }

  Widget getCarouselUI() {
    return Container(
      height: 280,
      decoration: BoxDecoration(
        color: Theme.of(context).backgroundColor,
        borderRadius: const BorderRadius.only(
            topLeft: Radius.circular(20.0), topRight: Radius.circular(20.0)),
        boxShadow: <BoxShadow>[
          BoxShadow(
              color: Theme.of(context).shadowColor,
              offset: const Offset(1.1, 1.1),
              blurRadius: 10.0),
        ],
      ),
      padding: const EdgeInsets.only(top: 8.0, left: 18, right: 16),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          SizedBox(height: 10),
          Text(
            tr("nearby_stores"),
            textAlign: TextAlign.left,
            style: TextStyle(
                fontWeight: FontWeight.w600,
                fontSize: 22,
                letterSpacing: 0.27,
                color: Theme.of(context).textTheme.caption?.color),
          ),
          Consumer<DataHandlerNotifier>(
            builder: (context, provider, child) => SliderView(
              callBack: (store) {
                moveTo(store);
              },
              stores: provider.dataHandler.stores,
              onPageChanged: (index, reason) {
                Marker? marker = provider.dataHandler.stores![index].marker;
                CameraPosition cameraPosition = CameraPosition(
                  target: marker!.position,
                  zoom: 12,
                );

                _controller?.animateCamera(
                    CameraUpdate.newCameraPosition(cameraPosition));
                _controller?.showMarkerInfoWindow(marker.markerId);
              },
            ),
          )
        ],
      ),
    );
  }

  void moveTo(store) {
    Navigator.push<dynamic>(
      context,
      MaterialPageRoute<dynamic>(
        builder: (BuildContext context) => DetailScreen(store: store),
        fullscreenDialog: true,
      ),
    );
  }

  Widget getFilterBarUI() {
    return Stack(
      children: <Widget>[
        Container(
          color: Theme.of(context).backgroundColor,
          child: Padding(
            padding:
                const EdgeInsets.only(left: 16, right: 16, top: 8, bottom: 4),
            child: Row(
              children: <Widget>[
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Consumer<DataHandlerNotifier>(
                      builder: (context, provider, child) => Text(
                        sprintf("%d %s", [
                          provider.dataHandler.stores?.length,
                          tr("stores_found")
                        ]),
                        style: TextStyle(
                            fontWeight: FontWeight.w100,
                            fontSize: 16,
                            color: Theme.of(context).textTheme.caption?.color),
                      ),
                    ),
                  ),
                ),
                Material(
                  color: Colors.transparent,
                  child: InkWell(
                    focusColor: Colors.transparent,
                    highlightColor: Colors.transparent,
                    hoverColor: Colors.transparent,
                    splashColor: Colors.grey.withOpacity(0.2),
                    borderRadius: const BorderRadius.all(
                      Radius.circular(4.0),
                    ),
                    onTap: () {
                      FocusScope.of(context).requestFocus(FocusNode());
                    },
                    child: Padding(
                      padding: const EdgeInsets.only(left: 8),
                      child: Row(
                        children: <Widget>[
                          Text(
                            "",
                            style: TextStyle(
                              fontWeight: FontWeight.w100,
                              fontSize: 16,
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Icon(
                              Icons.sort,
                              color: Color.fromRGBO(1, 1, 1, 0),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
        const Positioned(
          top: 0,
          left: 0,
          right: 0,
          child: Divider(
            height: 1,
          ),
        )
      ],
    );
  }

  Widget getAppBarUI() {
    return Container(
      decoration: BoxDecoration(
        color: Theme.of(context).backgroundColor,
        boxShadow: <BoxShadow>[
          BoxShadow(
              color: Colors.grey.withOpacity(0.2),
              offset: const Offset(0, 2),
              blurRadius: 8.0),
        ],
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
                  tr("map"),
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
                children: <Widget>[
                  Consumer<DataHandlerNotifier>(
                    builder: (context, provider, child) => Material(
                      color: Colors.transparent,
                      child: InkWell(
                        borderRadius: const BorderRadius.all(
                          Radius.circular(32.0),
                        ),
                        onTap: () {
                          CameraPosition cameraPosition = CameraPosition(
                            target:
                                LatLng(position!.latitude, position!.longitude),
                            zoom: 12,
                          );
                          _controller?.animateCamera(
                              CameraUpdate.newCameraPosition(cameraPosition));
                        },
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Icon(
                            Icons.my_location_rounded,
                            color: Theme.of(context).appBarTheme.color,
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}
