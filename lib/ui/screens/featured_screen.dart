import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/models/store.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';
import 'package:directorio_delicias/ui/widgets/store_list_view.dart';
import 'package:directorio_delicias/ui/widgets/tab_header.dart';

import 'detail_screen.dart';

class FeaturePage extends StatefulWidget {
  const FeaturePage({Key? key}) : super(key: key);

  @override
  _FeaturePageState createState() => _FeaturePageState();
}

class _FeaturePageState extends State<FeaturePage> {
  final ScrollController _scrollController = ScrollController();
  bool isNearMe = false;
  Widget? root;
  bool hasData = false;

  @override
  void initState() {
    super.initState();
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

  Widget getDataFromServer() {
    return FutureBuilder<DataHandler>(
      future: GetIt.instance.get<DataParser>().fetchFeatured(),
      builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          DataHandler? dataHandler = snapshot.data;
          hasData = true;
          Provider.of<DataHandlerNotifier>(context, listen: true)
              .setDataHandler(dataHandler);
          return showList();
        }
      },
    );
  }

  Widget showMain() {
    root = Container(
      child: Column(
        children: <Widget>[getAppBarUI(), Expanded(child: delayBeforeFetch())],
      ),
    );
    return root!;
  }

  Widget showList() {
    return NestedScrollView(
      controller: _scrollController,
      headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) {
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
      body: Container(
        color: Theme.of(context).backgroundColor,
        child: Consumer<DataHandlerNotifier>(
          builder: (context, provider, child) => ListView.builder(
            itemCount: provider.dataHandler.stores?.length,
            padding: const EdgeInsets.only(top: 8),
            scrollDirection: Axis.vertical,
            itemBuilder: (BuildContext context, int index) {
              return StoreListView(
                callback: () {
                  Navigator.push<dynamic>(
                    context,
                    MaterialPageRoute<dynamic>(
                      builder: (BuildContext context) => DetailScreen(
                          store: provider.dataHandler.stores![index]),
                      fullscreenDialog: true,
                    ),
                  );
                },
                faveCallback: (bool isFave) {
                  Store store1 = provider.dataHandler.stores![index];
                  provider.updateDataHandler(store1, index, isFave);
                },
                isFave: provider
                    .getStoreIsFave(provider.dataHandler.stores![index]),
                store: provider.dataHandler.stores![index],
              );
            },
          ),
        ),
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
                          provider.dataHandler.stores!.length,
                          tr("stores_found")
                        ]),
                        style: TextStyle(
                          fontWeight: FontWeight.w100,
                          fontSize: 16,
                          color: Theme.of(context).textTheme.caption!.color,
                        ),
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
                            child: Icon(Icons.sort, color: Colors.transparent),
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
              color: Theme.of(context).shadowColor,
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
                  tr("featured"),
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
                          if (!hasData) return;

                          isNearMe = !isNearMe;
                          provider.sortFeatured(isNearMe);
                        },
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Icon(
                            isNearMe
                                ? Icons.near_me
                                : FontAwesomeIcons.sortAlphaUp,
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
