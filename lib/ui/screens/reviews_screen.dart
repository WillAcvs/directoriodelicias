import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/review_services.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/models/reviews.dart';
import 'package:directorio_delicias/models/store.dart';
import 'package:directorio_delicias/models/user.dart';
import 'package:directorio_delicias/notifiers/datahandler_notifier.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';
import 'package:directorio_delicias/ui/widgets/review_widget.dart';
import 'package:directorio_delicias/ui/widgets/tab_header.dart';

class ReviewsScreen extends StatefulWidget {

  const ReviewsScreen({Key key,this.store, this.loggedUser}) : super(key: key);

  final Store store;
  final User loggedUser;
  
  @override
  _ReviewsPageState createState() => _ReviewsPageState();
}

class _ReviewsPageState extends State<ReviewsScreen> {

  final ScrollController _scrollController = ScrollController();
  bool isNearMe = false;
  Widget root;
  bool hasData = false;
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();
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
      builder: (ctx, widget1) {
        return Container(
          child: Scaffold(
            backgroundColor: Theme.of(context).backgroundColor,
            key: _scaffoldKey,
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
            floatingActionButton: FloatingActionButton.extended(
              onPressed: () {
                ReviewServices.showReview(
                  context: context, 
                  storeId: widget.store.storeId,
                  loggedUser: widget.loggedUser,
                  onSuccessReview: () {
                    setState(() {
                      root = null;
                    });
                  }
                );
              },
              foregroundColor: Theme.of(context).floatingActionButtonTheme.foregroundColor,
              label: Text(tr("add")),
              icon: Icon(Icons.add_comment),
              backgroundColor: Theme.of(context).accentColor,
            ),
          ),
        );
      }
    );
  }

  Widget getDataFromServer() {
    return FutureBuilder<DataHandler>(
      future: GetIt.instance.get<DataParser>().getReviews(min: 0, 
        max:Config.MAX_REVIEW_COUNT, storeId: widget.store.storeId),
      builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } 
        else {
          DataHandler dataHandler = snapshot.data;
          hasData = true;
          bool hasMore = dataHandler.rowCount < dataHandler.totalRowCount;
          Provider.of<DataHandlerNotifier>(context, listen: false).setHasMore(hasMore);
          Provider.of<DataHandlerNotifier>(context, listen: false).setDataHandler(dataHandler);
          return showList();
        }
      },
    );      
  }

  Widget showMain() {
    root = Container(
      color: Colors.transparent,
      child: Column(
        children: <Widget>[
          getAppBarUI(),
          Expanded( child: getDataFromServer() )
        ],
      ),
    );
    return root;
  }

  Widget showList() {
    return NestedScrollView(
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
      body: Container(
        color:Theme.of(context).backgroundColor,
        child: Consumer<DataHandlerNotifier>(
          builder: (context, provider, child) => ListView.builder(
            cacheExtent: 9999,
            itemCount: provider.hasMore 
                        ? provider.dataHandler.reviews.length + 1 
                        : provider.dataHandler.reviews.length,

            padding: const EdgeInsets.only(top: 8),
            scrollDirection: Axis.vertical,
            itemBuilder: (BuildContext context, int index) {
              if (index < provider.dataHandler.reviews.length) {
                return ReviewView(
                  callback: () {
                    
                  },
                  review: provider.dataHandler.reviews[index],
                );
              }
              else {
                // Don't trigger if one async loading is already under way
                if (!provider.isLoading) {
                  provider.setIsLoading(true);
                  GetIt.instance.get<DataParser>().getReviews(
                    min: provider.dataHandler.min + provider.dataHandler.max, 
                    max: provider.dataHandler.max, 
                    storeId: widget.store.storeId).then( (DataHandler dataH) {
                      Provider.of<DataHandlerNotifier>(context, listen: false)
                        .setHasMore(dataH.rowCount < dataH.totalRowCount);

                      if (dataH.reviews.isEmpty) {
                        Provider.of<DataHandlerNotifier>(context, listen: false).setIsLoading(false);
                      } else {
                        Provider.of<DataHandlerNotifier>(context, listen: false).setIsLoading(false);
                        List<Review> reviews = Provider.of<DataHandlerNotifier>(context, listen: false).dataHandler.reviews;
                        reviews.addAll(dataH.reviews);
                        dataH.reviews = reviews;
                        Provider.of<DataHandlerNotifier>(context, listen: false).setDataHandlerNotify(dataH);
                      }
                    }
                  );
                  
                }
                return Center(
                  child: SizedBox(
                    child: CircularProgressIndicator(),
                    height: 24,
                    width: 24,
                  ),
                );
              }                          
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
                        sprintf("%d %s %s \"%s\"", 
                          [Provider.of<DataHandlerNotifier>(context, listen: false).dataHandler.totalRowCount, 
                          tr("reviews"),
                          tr("for"),
                          widget.store.storeName]),
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(
                          fontWeight: FontWeight.w100,
                          fontSize: 16,
                          color: Theme.of(context).textTheme.caption.color,
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
                            child: Icon(
                              Icons.sort,
                              color: Colors.transparent
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
              color: Theme.of(context).shadowColor,
              offset: const Offset(0, 2),
              blurRadius: 8.0),
        ],
      ),
      child: Padding(
        padding: EdgeInsets.only(
            top: MediaQuery.of(context).padding.top, left: 15, right: 15),
        child: Row(
          children: <Widget>[
            Container(
              alignment: AlignmentDirectional.centerStart,
              width: AppBar().preferredSize.height + 40,
              height: AppBar().preferredSize.height,
              child: Material(
                color: Colors.transparent,
                child: InkWell(
                  borderRadius:
                      BorderRadius.circular(AppBar().preferredSize.height),
                  child: Icon(
                    Icons.arrow_back_ios,
                    color: Theme.of(context).appBarTheme.color,
                  ),
                  onTap: () {
                    Navigator.pop(context);
                  },
                ),
              ),
            ),
            Expanded(
              child: Center(
                child: Text(
                  tr("reviews"),
                  style: TextStyle(
                    fontWeight: FontWeight.w600,
                    fontSize: 22,
                    color: Theme.of(context).textTheme.caption.color,
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
                          
                        },
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: null,
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

