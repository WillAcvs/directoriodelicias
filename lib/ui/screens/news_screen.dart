import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:get_it/get_it.dart';
import 'package:incrementally_loading_listview/incrementally_loading_listview.dart';
import 'package:provider/provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/services.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/models/news.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';
import 'package:directorio_delicias/ui/widgets/news_widget.dart';
import 'package:directorio_delicias/ui/widgets/tab_header.dart';

class NewsPage extends StatefulWidget {
  const NewsPage({Key? key}) : super(key: key);

  @override
  _NewsPageState createState() => _NewsPageState();
}

class _NewsPageState extends State<NewsPage> {
  final ScrollController _scrollController = ScrollController();
  bool isNearMe = false;
  Widget? root;
  bool hasData = false;
  int min = 0;
  int max = Config.MAX_NEWS_COUNT_FETCH;

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
      future: GetIt.instance.get<DataParser>().fetchNews(min: min, max: max),
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
            provider.setCount(dataHandler.news?.length);
            provider.setHasMore(dataHandler.news?.length == max);
            return showList();

          default:
            return Center(child: Text(tr("")));
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
          builder: (context, provider, child) => IncrementallyLoadingListView(
            padding: EdgeInsets.all(0.0),
            hasMore: () => provider.hasMore,
            itemCount: () => provider.dataHandler == null
                ? 0
                : provider.dataHandler.news!.length,
            loadMore: () async {
              min += max;
              DataHandler dataHandler = await GetIt.instance
                  .get<DataParser>()
                  .fetchNews(min: min, max: max);
              if (dataHandler.news!.length > 0) {
                for (News news in dataHandler.news!) {
                  provider.dataHandler.news?.add(news);
                }
              }
              provider.setHasMore(dataHandler.news?.length == max);
            },
            onLoadMore: () {},
            onLoadMoreFinished: () {
              var provider =
                  Provider.of<DataHandlerNotifier>(context, listen: false);
              provider.setCountNotify(provider.dataHandler.news?.length);
              if (provider.hasMore) provider.notifyListenersOnly();
            },
            loadMoreOffsetFromBottom: 1,
            itemBuilder: (context, index) {
              final news = provider.dataHandler.news![index];
              if ((provider.hasMore) &&
                  index == provider.dataHandler.news!.length - 1) {
                return Column(
                  children: <Widget>[
                    NewsWidget(
                        news: news,
                        callback: () {
                          GetIt.instance
                              .get<Services>()
                              .launchURL(news.newsUrl);
                        }),
                    LoadingWidget(
                      size: 30.0,
                      iconColor: Theme.of(context).accentColor,
                    )
                  ],
                );
              }
              return NewsWidget(
                  news: news,
                  callback: () {
                    GetIt.instance.get<Services>().launchURL(news.newsUrl);
                  });
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
                    child: Selector<DataHandlerNotifier, int>(
                      selector: (ctxN, provider) => provider.count,
                      builder: (ctxN, count, widgt) => Text(
                        sprintf("%d %s", [count, tr("news_found")]),
                        style: TextStyle(
                            fontWeight: FontWeight.w100,
                            fontSize: 16,
                            color: Theme.of(context).textTheme.caption!.color),
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
                  tr("news"),
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
