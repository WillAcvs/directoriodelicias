
import 'package:flutter/material.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/models/store.dart';


class NearbyListView extends StatefulWidget {
  const NearbyListView({Key key, this.callBack, this.stores}) : super(key: key);

  final Function(Store) callBack;
  final List<Store> stores;
  @override
  _NearbyListViewState createState() => _NearbyListViewState();
}

class _NearbyListViewState extends State<NearbyListView> {
  
  
  @override
  void initState() {
    super.initState();
  }

  Future<bool> getData() async {
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 8),
      child: FutureBuilder<bool>(
        future: getData(),
        builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
          if (!snapshot.hasData) {
            return const SizedBox();
          } else {
            return GridView(
              cacheExtent: 9999,
              shrinkWrap: true,
              padding: const EdgeInsets.all(8),
              physics: const BouncingScrollPhysics(),
              scrollDirection: Axis.vertical,
              children: List<Widget>.generate(
                widget.stores.length,
                (int index) {
                  return CategoryView(
                    callback: () {
                      widget.callBack(widget.stores[index]);
                    },
                    store: widget.stores[index],
                  );
                },
              ),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2,
                mainAxisSpacing: 20.0,
                crossAxisSpacing: 20.0,
                childAspectRatio: 0.8,
              ),
            );
          }
        },
      ),
    );
  }
}

class CategoryView extends StatelessWidget {
  const CategoryView(
      {Key key,
      this.store,
      this.callback})
      : super(key: key);

  final VoidCallback callback;
  final Store store;

  @override
  Widget build(BuildContext context) {
    String photoUrl = "";
    if(store.photos.length > 0)
      photoUrl = store.photos[0].thumbUrl;
      
    return InkWell(
      splashColor: Colors.transparent,
      onTap: () {
        callback();
      },
      child: SizedBox(
        height: 280,
        child: Stack(
          alignment: AlignmentDirectional.bottomCenter,
          children: <Widget>[
            Container(
              child: Column(
                children: <Widget>[
                  Expanded(
                    child: Container(
                      decoration: BoxDecoration(
                        color: Theme.of(context).textTheme.bodyText2.color,
                        borderRadius: const BorderRadius.all(
                            Radius.circular(16.0)),
                      ),
                      child: Column(
                        children: <Widget>[
                          Expanded(
                            child: Container(
                              child: Column(
                                children: <Widget>[
                                  Padding(
                                    padding: const EdgeInsets.only(
                                        top: 16, left: 16, right: 16),
                                    child: Text(
                                      store.storeName,
                                      maxLines: 2,
                                      overflow: TextOverflow.ellipsis,
                                      textAlign: TextAlign.center,
                                      style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        fontSize: 16,
                                        letterSpacing: 0.27,
                                        color: Theme.of(context).textTheme.caption.color,
                                      ),
                                    ),
                                  ),
                                  Padding(
                                    padding: const EdgeInsets.only(
                                        top: 8,
                                        left: 16,
                                        right: 16,
                                        bottom: 8),
                                    child: Row(
                                      mainAxisAlignment:MainAxisAlignment.spaceBetween,
                                      crossAxisAlignment:CrossAxisAlignment.center,
                                      children: <Widget>[
                                        Text(
                                          sprintf("%.2f%s", [store.distance, "KM"]),
                                          textAlign: TextAlign.left,
                                          style: TextStyle(
                                            fontWeight: FontWeight.w200,
                                            fontSize: 12,
                                            letterSpacing: 0.27,
                                            color: Theme.of(context).textTheme.subtitle1.color,
                                          ),
                                        ),
                                        Container(
                                          child: Row(
                                            children: <Widget>[
                                              Text(
                                                sprintf("%.1f", [store.ratingAve]),
                                                textAlign:TextAlign.left,
                                                style: TextStyle(
                                                  fontWeight:FontWeight.w200,
                                                  fontSize: 18,
                                                  letterSpacing: 0.27,
                                                  color:Theme.of(context).textTheme.subtitle1.color,
                                                ),
                                              ),
                                              Icon(
                                                Icons.star,
                                                color:Theme.of(context).accentColor,
                                                size: 20,
                                              ),
                                            ],
                                          ),
                                        )
                                      ],
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          const SizedBox(
                            width: 48,
                          ),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(
                    height: 48,
                  ),
                ],
              ),
            ),
            Container(
              child: Padding(
                padding:
                    const EdgeInsets.only(top: 24, right: 16, left: 16),
                child: Container(
                  decoration: BoxDecoration(
                    borderRadius:
                        const BorderRadius.all(Radius.circular(16.0)),
                    boxShadow: <BoxShadow>[
                      BoxShadow(
                          color: Theme.of(context).shadowColor,
                          offset: const Offset(0.0, 0.0),
                          blurRadius: 6.0),
                    ],
                  ),
                  child: ClipRRect(
                    borderRadius:
                        const BorderRadius.all(Radius.circular(16.0)),
                    child: AspectRatio(
                        aspectRatio: 1.28,
                        child: AspectRatio(
                          aspectRatio: 1.0,
                          child: Helpers.loadCacheImage(imageUrl: photoUrl),
                        )
                      ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
