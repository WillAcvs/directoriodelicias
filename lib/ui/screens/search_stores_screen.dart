import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:incrementally_loading_listview/incrementally_loading_listview.dart';
import 'package:provider/provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';

import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';

import 'package:directorio_delicias/models/store.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';
import 'package:directorio_delicias/ui/screens/detail_screen.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';

import 'package:directorio_delicias/ui/widgets/store_list_view.dart';
import 'package:directorio_delicias/ui/widgets/tab_header.dart';

class SearchStoresScreen extends StatefulWidget {
  final String? searchStr;
  const SearchStoresScreen({Key? key, this.searchStr}) : super(key: key);

  @override
  _SearchStoresState createState() => _SearchStoresState();
}

class _SearchStoresState extends State<SearchStoresScreen> {
  final ScrollController _scrollController = ScrollController();
  bool isNearMe = false;
  Widget? root;
  bool hasData = false;
  int min = 0;
  int max = Config.MAX_STORES_COUNT_FETCH;

  @override
  void initState() {
    super.initState();
    print(widget.searchStr);
  }

  Future<bool> getData() async {
    await Future<dynamic>.delayed(const Duration(milliseconds: 200));
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
                future: getData(),
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

  Future<DataHandler> pauseBeforeServer() async {
    await Future<dynamic>.delayed(const Duration(milliseconds: 500));
    return GetIt.instance
        .get<DataParser>()
        .searchStores(min: min, max: max, searchStr: widget.searchStr);
  }

  Widget getDataFromServer() {
    return FutureBuilder<DataHandler>(
      future: pauseBeforeServer(),
      builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
        switch (snapshot.connectionState) {
          case ConnectionState.waiting:
            return LoadingWidget(
              size: 30.0,
              iconColor: Theme.of(context).accentColor,
            );

          case ConnectionState.done:
            DataHandler dataHandler = snapshot.data!;
            hasData = true;
            var provider =
                Provider.of<DataHandlerNotifier>(context, listen: false);
            provider.setDataHandler(dataHandler);
            provider.setCount(dataHandler.stores!.length);
            provider.setHasMore(dataHandler.stores!.length == max);
            return showList();

          default:
            return Center();
        }
      },
    );
  }

  Widget showMain() {
    root = Container(
      child: Column(
        children: <Widget>[getAppBarUI(), Expanded(child: getDataFromServer())],
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
          builder: (context, provider, child) => IncrementallyLoadingListView(
            padding: EdgeInsets.all(0.0),
            hasMore: () => provider.hasMore,
            itemCount: () => provider.dataHandler.stores == null
                ? 0
                : provider.dataHandler.stores!.length,
            loadMore: () async {
              min += max;
              DataHandler dataHandler = await GetIt.instance
                  .get<DataParser>()
                  .searchStores(
                      min: min, max: max, searchStr: widget.searchStr);
              if (dataHandler.stores!.length > 0) {
                for (Store store in dataHandler.stores!) {
                  provider.dataHandler.stores?.add(store);
                }
              }
              provider.setHasMore(dataHandler.stores!.length == max);
            },
            onLoadMore: () {},
            onLoadMoreFinished: () {
              var provider =
                  Provider.of<DataHandlerNotifier>(context, listen: false);
              provider.setCountNotify(provider.dataHandler.stores!.length);
              if (provider.hasMore) provider.notifyListenersOnly();
            },
            loadMoreOffsetFromBottom: 1,
            itemBuilder: (context, index) {
              if ((provider.hasMore) &&
                  index == provider.dataHandler.stores!.length - 1) {
                return Column(
                  children: <Widget>[
                    entry(provider, index),
                    LoadingWidget(
                      size: 30.0,
                      iconColor: Theme.of(context).accentColor,
                    )
                  ],
                );
              }
              return entry(provider, index);
            },
          ),
        ),
      ),
    );
  }

  Widget entry(provider, index) {
    return StoreListView(
      callback: () {
        Navigator.push<dynamic>(
          context,
          MaterialPageRoute<dynamic>(
            builder: (BuildContext context) =>
                DetailScreen(store: provider.dataHandler.stores[index]),
            fullscreenDialog: true,
          ),
        );
      },
      faveCallback: (bool isFave) {
        Store store1 = provider.dataHandler.stores[index];
        provider.updateDataHandler(store1, index, isFave);
      },
      isFave: provider.getStoreIsFave(provider.dataHandler.stores[index]),
      store: provider.dataHandler.stores[index],
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
                    child: Selector<DataHandlerNotifier, int>(
                      selector: (ctxN, provider) => provider.count,
                      builder: (ctxN, count, widgt) => Text(
                        sprintf("%d %s '%s'",
                            [count, tr("results_found"), widget.searchStr!]),
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
              alignment: Alignment.centerLeft,
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
                  tr("search_stores"),
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
              child: null,
            )
          ],
        ),
      ),
    );
  }
}
