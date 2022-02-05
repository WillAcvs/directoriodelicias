
import 'dart:async';
import 'dart:collection';
import 'dart:typed_data';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';

class StoreMapScreen extends StatefulWidget {

  StoreMapScreen({Key key, this.lat = 0, this.lon = 0}) : super(key: key);

  final double lat;
  final double lon;

  @override
  _StoreMapScreenState createState() => _StoreMapScreenState();
}

class _StoreMapScreenState extends State<StoreMapScreen> {
  
  String _darkMapStyle;
  String _lightMapStyle;

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

  GoogleMapController ctrl;
  LatLng selectedLatLng;
  Uint8List bytes;

  void done() async {
    DataHandler dataHandler = new DataHandler();
    dataHandler.latLng = selectedLatLng;
    dataHandler.bytes = bytes;
    Navigator.pop(context, dataHandler);
  }

  Future<bool> getData(int millis) async {
    await Future<dynamic>.delayed(Duration(milliseconds: millis));
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<bool>(
      future: getData(200),
      builder:(BuildContext context, AsyncSnapshot<bool> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          return showMain();
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
          return showMap();
        }
      },
    );
  }

  Widget showMain() {
    return Scaffold(
          backgroundColor: Theme.of(context).backgroundColor,
          body: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              getAppBarUI(),
              Expanded( child: delayBeforeFetch() ),
            ]
          ),
        
    );
  }


  Widget showMap() {
    return Stack(
      children: [
        googleMap(),
        Align(
          alignment: Alignment.bottomCenter,
          child: Container(
            height: 100,
            decoration: BoxDecoration(
              color: Theme.of(context).backgroundColor,
              borderRadius: BorderRadius.circular(20),
              boxShadow: <BoxShadow>[
              BoxShadow(
                color: Theme.of(context).shadowColor,
                offset: const Offset(1.1, 1.1),
                blurRadius: 10.0),
              ],
            ),
            child: Padding(
              padding: const EdgeInsets.all(20.0),
              child: Center(
                child: InkWell(
                  onTap: done,
                  child: Container(
                    height: 48,
                    decoration: BoxDecoration(
                      color: Theme.of(context).accentColor,
                      borderRadius: const BorderRadius.all(
                        Radius.circular(16.0),
                      ),
                      boxShadow: <BoxShadow>[
                        BoxShadow(
                            color: Theme.of(context).shadowColor,
                            offset: const Offset(1.1, 1.1),
                            blurRadius: 10.0),
                      ],
                    ),
                    child: Center(
                      child: Text(
                        tr("select"),
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontWeight: FontWeight.w600,
                          fontSize: 18,
                          letterSpacing: 0.0,
                          color: Theme.of(context).floatingActionButtonTheme.foregroundColor,
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ),
            
          ),
        )
      ],
    );
  }

  Widget googleMap() {
    
    if(widget.lat == 0 || widget.lon == 0) {
      selectedLatLng = LatLng(Config.DEFAULT_LAT, Config.DEFAULT_LON);
    }
    else {
      selectedLatLng = LatLng(widget.lat, widget.lon);
    }

    final CameraPosition cameraPosition = CameraPosition(
      target: selectedLatLng,
      zoom: 10,
    );

    Marker marker = Marker(
      consumeTapEvents: false,
      markerId: MarkerId("CURRENT_LOCATION"),
      position: selectedLatLng,
      anchor: Offset(0, -0.75),
      draggable: true,
      icon: BitmapDescriptor.defaultMarker,
      infoWindow: InfoWindow(
        title: tr("tap_and_hold"),
        snippet: tr("drag_me_around"),
      ),
      onDragEnd: (latlng) async {
        selectedLatLng = latlng;
        ctrl.animateCamera(
          CameraUpdate.newCameraPosition(
            CameraPosition(
              target: selectedLatLng,
              zoom: await ctrl.getZoomLevel(),
            )
          )
        );
      }
    );

    Set<Marker> markers = HashSet<Marker>();
    markers.add(marker);
    
    return ClipRRect(
      borderRadius: BorderRadius.all(Radius.circular(10)),
      child: GoogleMap(
        markers: markers,
        myLocationButtonEnabled: false,
        mapType: MapType.normal,
        initialCameraPosition: cameraPosition,
        onMapCreated: (GoogleMapController controller) {
          ctrl = controller;
          if (Helpers.isDarkMode(context)) {
              controller.setMapStyle(_darkMapStyle);
          }
          else {
              controller.setMapStyle(_lightMapStyle);
          }
          
          ctrl.animateCamera(CameraUpdate.newCameraPosition(cameraPosition));
          ctrl.showMarkerInfoWindow(marker.markerId);
        },
        onCameraIdle: () async {
          ctrl.showMarkerInfoWindow(marker.markerId);
          bytes = await ctrl.takeSnapshot();
        },
        onCameraMoveStarted: ()  async {
          bytes = await ctrl.takeSnapshot();
        },
      )
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
                    Navigator.pop(context);
                  },
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Icon(
                      Icons.close,
                      color: Theme.of(context).appBarTheme.color,
                    ),
                  ),
                ),
              ),
            ),
            Expanded(
              child: Center(
                child: Text(
                  tr("store_location"),
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
            )
          ],
        ),
      ),
    );
  }
}
