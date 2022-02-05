import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:geolocator/geolocator.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';
import 'package:directorio_delicias/ui/screens/detail_screen.dart';
import 'package:directorio_delicias/ui/screens/notifications_screen.dart';
import 'package:directorio_delicias/ui/screens/search_stores_screen.dart';
import 'package:directorio_delicias/ui/widgets/top_rated_list_view.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';
import 'package:directorio_delicias/ui/widgets/nearby_list_view.dart';
import 'package:directorio_delicias/ui/widgets/slider_view.dart';
import 'package:carousel_slider/carousel_slider.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key key}) : super(key: key);

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  bool multiple = true;
  Widget root;
  bool hasData = false;
  TextEditingController _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    // checkLocation();
  }

  void checkLocation() async {
    bool enabled = await Geolocator.isLocationServiceEnabled();
    print(enabled);
    if (enabled) {
      print("enabled");
      LocationPermission permission = await Geolocator.requestPermission();
      print(permission);
      try {
        Position pos = await MyApp.determinePosition();
        print(pos);
      } catch (e) {
        print(e);
      }
    }
  }

  Future<bool> getData(int millis) async {
    await Future<dynamic>.delayed(Duration(milliseconds: millis));
    return true;
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<DataHandlerNotifier>(
        create: (context) => DataHandlerNotifier(),
        builder: (ctx, widget) {
          return Scaffold(
            backgroundColor: Theme.of(context).backgroundColor,
            body: FutureBuilder<bool>(
              future: getData(0),
              builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
                if (!snapshot.hasData) {
                  return const SizedBox();
                } else {
                  return root == null ? showMain() : root;
                }
              },
            ),
          );
        });
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

  Widget checkLocationBeforeFetch() {
    return FutureBuilder<bool>(
      future: getData(500),
      builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          return delayBeforeFetch();
        }
      },
    );
  }

  Widget getDataFromServer() {
    return FutureBuilder<DataHandler>(
      future: GetIt.instance.get<DataParser>().fetchHome(),
      builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          DataHandler dataHandler = snapshot.data;
          Provider.of<DataHandlerNotifier>(context, listen: false)
              .setDataHandler(dataHandler);
          hasData = true;
          return showList();
        }
      },
    );
  }

  Widget showMain() {
    root = Stack(
      children: <Widget>[
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            appBar(),
            Expanded(child: checkLocationBeforeFetch()),
          ],
        )
      ],
    );
    return root;
  }

  Widget appBar() {
    return Container(
      color: Theme.of(context).backgroundColor,
      child: Padding(
        padding: EdgeInsets.only(
            top: MediaQuery.of(context).padding.top, left: 8, right: 8),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.only(top: 8, left: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
                child: Material(
                  color: Colors.transparent,
                  child: InkWell(
                    borderRadius:
                        BorderRadius.circular(AppBar().preferredSize.height),
                    child: Icon(
                      Icons.menu,
                      color: Theme.of(context).appBarTheme.color,
                    ),
                    onTap: () {
                      ZoomDrawer.of(context).toggle();
                    },
                  ),
                ),
              ),
            ),
            Expanded(
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.only(top: 4),
                  child: Text(
                    tr("app_name"),
                    style: TextStyle(
                      fontSize: 22,
                      color: Theme.of(context).appBarTheme.color,
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(top: 8, right: 8),
              child: Container(
                width: AppBar().preferredSize.height - 8,
                height: AppBar().preferredSize.height - 8,
                child: Material(
                  color: Colors.transparent,
                  child: InkWell(
                    borderRadius:
                        BorderRadius.circular(AppBar().preferredSize.height),
                    child: Icon(
                      Icons.notifications,
                      color: Theme.of(context).appBarTheme.color,
                    ),
                    onTap: () {
                      Navigator.push<dynamic>(
                        context,
                        MaterialPageRoute<dynamic>(
                          builder: (BuildContext context) =>
                              NotificationScreen(),
                        ),
                      );
                    },
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget showList() {
    return Container(
      color: Theme.of(context).backgroundColor,
      child: Scaffold(
          backgroundColor: Colors.transparent,
          body: MediaQuery.removePadding(
            removeTop: true,
            context: context,
            child: ListView.builder(
              scrollDirection: Axis.vertical,
              itemCount: 5,
              cacheExtent: 999,
              itemBuilder: (context, index) {
                if (index == 0) return getSearchBarUI();
                if (index == 1) return getCarouselHead();
                if (index == 2) return getCarouselUI();
                if (index == 3) return getTopRated();
                return getNearby();
              },
            ),
          )),
    );
  }

  Widget getTopRated() {
    return SizedBox(
      height: 235,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Padding(
              padding: const EdgeInsets.only(top: 16.0, left: 18, right: 16),
              child: Text(
                tr("top_rated"),
                textAlign: TextAlign.left,
                style: TextStyle(
                  fontWeight: FontWeight.w600,
                  fontSize: 22,
                  letterSpacing: 0.27,
                  color: Theme.of(context).textTheme.caption.color,
                ),
              )),
          const SizedBox(
            height: 16,
          ),
          Selector<DataHandlerNotifier, DataHandler>(
              selector: (ctxN, provider) => provider.dataHandler,
              builder: (ctxN, dataHandler, widgt) {
                return TopRatedListView(
                  callBack: (store) {
                    moveTo(store);
                  },
                  stores: dataHandler.topRated,
                );
              }),
        ],
      ),
    );
  }

  Widget getNearby() {
    return Padding(
      padding: const EdgeInsets.only(top: 8.0, left: 18, right: 16),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Text(
            tr("nearby_stores"),
            textAlign: TextAlign.left,
            style: TextStyle(
              fontWeight: FontWeight.w600,
              fontSize: 22,
              letterSpacing: 0.27,
              color: Theme.of(context).textTheme.caption.color,
            ),
          ),
          Container(
            child: Selector<DataHandlerNotifier, DataHandler>(
                selector: (ctxN, provider) => provider.dataHandler,
                builder: (ctxN, dataHandler, widgt) {
                  return NearbyListView(
                    callBack: (store) {
                      moveTo(store);
                    },
                    stores: dataHandler.nearbyStores,
                  );
                }),
          )
        ],
      ),
    );
  }

  Widget getCarouselUI() {
    return SizedBox(
      height: 260,
      child: Padding(
        padding: const EdgeInsets.only(top: 8.0, left: 18, right: 16),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text(
              tr("featured"),
              textAlign: TextAlign.left,
              style: TextStyle(
                fontWeight: FontWeight.w600,
                fontSize: 22,
                letterSpacing: 0.27,
                color: Theme.of(context).textTheme.caption.color,
              ),
            ),
            Selector<DataHandlerNotifier, DataHandler>(
                selector: (ctxN, provider) => provider.dataHandler,
                builder: (ctxN, dataHandler, widgt) {
                  return SliderView(
                    callBack: (store) {
                      moveTo(store);
                    },
                    stores: dataHandler.stores,
                    onPageChanged: (index, reason) {},
                  );
                }),
          ],
        ),
      ),
    );
  }

  //demo
  Widget getCarouselHead() {
    return SizedBox(
      height: 200,
      child: Padding(
        padding: const EdgeInsets.only(top: 8.0, left: 18, right: 16),
        child: ListView(
          children: [
            CarouselSlider(
              items: [
                //1st Image of Slider
                Container(
                  margin: EdgeInsets.all(6.0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    image: DecorationImage(
                      image: AssetImage('assets/images/logo.png'),
                      fit: BoxFit.cover,
                    ),
                  ),
                ),

                //2nd Image of Slider
                Container(
                  margin: EdgeInsets.all(6.0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    image: DecorationImage(
                      image: AssetImage('assets/images/tienda.png'),
                      fit: BoxFit.cover,
                    ),
                  ),
                ),

                //3rd Image of Slider
                Container(
                  margin: EdgeInsets.all(6.0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    image: DecorationImage(
                      image: AssetImage('assets/images/municipio.png'),
                      fit: BoxFit.cover,
                    ),
                  ),
                ),

                //4th Image of Slider
                Container(
                  margin: EdgeInsets.all(6.0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    image: DecorationImage(
                      image: AssetImage('assets/images/tienda2.png'),
                      fit: BoxFit.cover,
                    ),
                  ),
                ),

                //5th Image of Slider
                Container(
                  margin: EdgeInsets.all(6.0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8.0),
                    image: DecorationImage(
                      image: AssetImage('assets/images/desarrollo.png'),
                      fit: BoxFit.cover,
                    ),
                  ),
                ),
              ],

              //Slider Container properties
              options: CarouselOptions(
                height: 180.0,
                enlargeCenterPage: true,
                autoPlay: true,
                aspectRatio: 16 / 9,
                autoPlayCurve: Curves.fastOutSlowIn,
                enableInfiniteScroll: true,
                autoPlayAnimationDuration: Duration(milliseconds: 800),
                viewportFraction: 0.8,
              ),
            ),
          ],
        ),
      ),
    );
  }
  //demo fin

  void moveTo(store) {
    Navigator.push<dynamic>(
      context,
      MaterialPageRoute<dynamic>(
        builder: (BuildContext context) => DetailScreen(store: store),
        fullscreenDialog: true,
      ),
    );
  }

  Widget getSearchBarUI() {
    return Padding(
      padding: const EdgeInsets.only(left: 16, right: 16, top: 8, bottom: 8),
      child: Row(
        children: <Widget>[
          Expanded(
            child: Padding(
              padding:
                  const EdgeInsets.only(right: 16, top: 8, bottom: 8, left: 16),
              child: Container(
                decoration: BoxDecoration(
                  color: Theme.of(context).textTheme.bodyText1.color,
                  borderRadius: const BorderRadius.all(
                    Radius.circular(38.0),
                  ),
                  boxShadow: <BoxShadow>[
                    BoxShadow(
                        color: Theme.of(context).shadowColor,
                        offset: const Offset(0, 2),
                        blurRadius: 8.0),
                  ],
                ),
                child: Padding(
                  padding: const EdgeInsets.only(
                      left: 16, right: 16, top: 4, bottom: 4),
                  child: TextField(
                    controller: _searchController,
                    onChanged: (String txt) {},
                    style: TextStyle(
                        fontSize: 18,
                        color: Theme.of(context).textTheme.caption.color),
                    cursorColor: Theme.of(context).accentColor,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: tr("search_stores"),
                    ),
                  ),
                ),
              ),
            ),
          ),
          Container(
            decoration: BoxDecoration(
              color: Theme.of(context).accentColor,
              borderRadius: const BorderRadius.all(
                Radius.circular(38.0),
              ),
              boxShadow: <BoxShadow>[
                BoxShadow(
                    color: Theme.of(context).shadowColor,
                    offset: const Offset(0, 2),
                    blurRadius: 8.0),
              ],
            ),
            child: Material(
              color: Colors.transparent,
              child: InkWell(
                borderRadius: const BorderRadius.all(
                  Radius.circular(32.0),
                ),
                onTap: () {
                  FocusScope.of(context).requestFocus(FocusNode());
                  Navigator.push<dynamic>(
                    context,
                    MaterialPageRoute<dynamic>(
                      builder: (BuildContext context) =>
                          SearchStoresScreen(searchStr: _searchController.text),
                      fullscreenDialog: true,
                    ),
                  );
                },
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Icon(FontAwesomeIcons.search,
                      size: 20, color: Theme.of(context).indicatorColor),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
