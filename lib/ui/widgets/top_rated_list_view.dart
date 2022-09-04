import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/models/store.dart';

class TopRatedListView extends StatefulWidget {
  const TopRatedListView({Key? key, required this.callBack, this.stores})
      : super(key: key);

  final Function(Store) callBack;
  final List<Store>? stores;

  @override
  _TopRatedListViewState createState() => _TopRatedListViewState();
}

class _TopRatedListViewState extends State<TopRatedListView> {
  @override
  void initState() {
    super.initState();
  }

  Future<bool> getData() async {
    await Future<dynamic>.delayed(const Duration(milliseconds: 50));
    return true;
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 16, bottom: 16),
      child: Container(
        height: 134,
        width: double.infinity,
        child: FutureBuilder<bool>(
          future: getData(),
          builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
            if (!snapshot.hasData) {
              return const SizedBox();
            } else {
              return ListView.builder(
                cacheExtent: 9999,
                padding: const EdgeInsets.only(
                    top: 0, bottom: 0, right: 16, left: 16),
                itemCount: widget.stores?.length,
                scrollDirection: Axis.horizontal,
                itemBuilder: (BuildContext context, int index) {
                  return CategoryView(
                    store: widget.stores![index],
                    callback: () {
                      widget.callBack(widget.stores![index]);
                    },
                  );
                },
              );
            }
          },
        ),
      ),
    );
  }
}

class CategoryView extends StatelessWidget {
  const CategoryView({Key? key, required this.store, required this.callback})
      : super(key: key);

  final VoidCallback callback;
  final Store store;

  @override
  Widget build(BuildContext context) {
    String photoUrl = "";
    if (store.photos!.length > 0) photoUrl = store.photos![0].thumbUrl;

    return InkWell(
      splashColor: Colors.transparent,
      onTap: () {
        callback();
      },
      child: SizedBox(
        width: 280,
        child: Stack(
          children: <Widget>[
            Container(
              child: Row(
                children: <Widget>[
                  const SizedBox(
                    width: 48,
                  ),
                  Expanded(
                    child: Container(
                      decoration: BoxDecoration(
                        color: Theme.of(context).textTheme.bodyText2?.color,
                        borderRadius:
                            const BorderRadius.all(Radius.circular(16.0)),
                      ),
                      child: Row(
                        children: <Widget>[
                          const SizedBox(
                            width: 60,
                          ),
                          Expanded(
                            child: Container(
                              padding: const EdgeInsets.only(
                                left: 10,
                                right: 10,
                              ),
                              child: Center(
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    Padding(
                                      padding: const EdgeInsets.only(top: 0),
                                      child: Text(
                                        store.storeName,
                                        textAlign: TextAlign.start,
                                        maxLines: 2,
                                        overflow: TextOverflow.ellipsis,
                                        style: TextStyle(
                                          fontWeight: FontWeight.w600,
                                          fontSize: 17,
                                          letterSpacing: 0.27,
                                          color: Theme.of(context)
                                              .textTheme
                                              .caption!
                                              .color,
                                        ),
                                      ),
                                    ),
                                    Padding(
                                      padding: const EdgeInsets.only(
                                          bottom: 0, top: 10),
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        crossAxisAlignment:
                                            CrossAxisAlignment.center,
                                        children: <Widget>[
                                          Text(
                                            sprintf("%.2f%s",
                                                [store.distance, "KM"]),
                                            textAlign: TextAlign.start,
                                            style: TextStyle(
                                              fontWeight: FontWeight.w200,
                                              fontSize: 12,
                                              letterSpacing: 0.27,
                                              color: Theme.of(context)
                                                  .textTheme
                                                  .subtitle1!
                                                  .color,
                                            ),
                                          ),
                                          Container(
                                            child: Row(
                                              children: <Widget>[
                                                Text(
                                                  sprintf("%.1f",
                                                      [store.ratingAve]),
                                                  textAlign: TextAlign.start,
                                                  style: TextStyle(
                                                    fontWeight: FontWeight.w200,
                                                    fontSize: 18,
                                                    letterSpacing: 0.27,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .subtitle1!
                                                        .color,
                                                  ),
                                                ),
                                                Icon(
                                                  Icons.star,
                                                  color: Theme.of(context)
                                                      .accentColor,
                                                  size: 20,
                                                ),
                                              ],
                                            ),
                                          )
                                        ],
                                      ),
                                    ),
                                    Padding(
                                      padding: const EdgeInsets.only(bottom: 0),
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        crossAxisAlignment:
                                            CrossAxisAlignment.start,
                                        children: <Widget>[
                                          Text(
                                            sprintf("%d %s", [
                                              store.ratingCount,
                                              tr("ratings")
                                            ]),
                                            textAlign: TextAlign.start,
                                            style: TextStyle(
                                              fontWeight: FontWeight.w600,
                                              fontSize: 15,
                                              letterSpacing: 0.27,
                                              color:
                                                  Theme.of(context).accentColor,
                                            ),
                                          ),
                                          Container(
                                            decoration: BoxDecoration(
                                              color: Colors.transparent,
                                              borderRadius:
                                                  const BorderRadius.all(
                                                      Radius.circular(8.0)),
                                            ),
                                            child: Padding(
                                              padding:
                                                  const EdgeInsets.all(4.0),
                                              child: SizedBox(
                                                height: 25,
                                              ),
                                            ),
                                          )
                                        ],
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
                  )
                ],
              ),
            ),
            Container(
              child: Padding(
                padding: const EdgeInsets.only(
                    top: 24, bottom: 24, left: 16, right: 16),
                child: Row(
                  children: <Widget>[
                    ClipRRect(
                        borderRadius:
                            const BorderRadius.all(Radius.circular(16.0)),
                        child: AspectRatio(
                          aspectRatio: 1.0,
                          child: Helpers.loadCacheImage(imageUrl: photoUrl),
                        ))
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
