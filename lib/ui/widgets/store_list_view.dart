
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:smooth_star_rating/smooth_star_rating.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/db/db_provider.dart';
import 'package:directorio_delicias/models/fave.dart';
import 'package:directorio_delicias/models/store.dart';

class StoreListView extends StatelessWidget {
  const StoreListView(
      {Key key,
      this.store,
      this.callback,
      this.faveCallback,
      this.isFave = false})
      : super(key: key);

  final VoidCallback callback;
  final Function(bool) faveCallback;
  final Store store;
  final bool isFave;

  @override
  Widget build(BuildContext context) {
    String photoUrl = "";
    if(store.photos.length > 0)
      photoUrl = store.photos[0].thumbUrl;
      
    return Padding(
      padding: const EdgeInsets.only(
          left: 24, right: 24, top: 8, bottom: 16),
      child: InkWell(
        splashColor: Colors.transparent,
        onTap: () {
          callback();
        },
        child: Container(
          decoration: BoxDecoration(
            borderRadius: const BorderRadius.all(Radius.circular(16.0)),
            boxShadow: <BoxShadow>[
              BoxShadow(
                color: Theme.of(context).shadowColor,
                offset: const Offset(4, 4),
                blurRadius: 16,
              ),
            ],
          ),
          child: ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(16.0)),
            child: Stack(
              children: <Widget>[
                Column(
                  children: <Widget>[
                    AspectRatio(
                      aspectRatio: 2.0,
                      child: Helpers.loadCacheImage(imageUrl: photoUrl),
                    ),
                    Container(
                      color: Theme.of(context).textTheme.bodyText1.color,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Expanded(
                            child: Container(
                              child: Padding(
                                padding: const EdgeInsets.only(
                                    left: 16, top: 8, bottom: 8, right: 16),
                                child: Column(
                                  mainAxisAlignment:MainAxisAlignment.center,
                                  crossAxisAlignment:CrossAxisAlignment.start,
                                  children: <Widget>[
                                    Text(
                                      store.storeName,
                                      maxLines: 1,
                                      overflow: TextOverflow.ellipsis,
                                      textAlign: TextAlign.start,
                                      style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        fontSize: 22,
                                        color: Theme.of(context).textTheme.caption.color,
                                      ),
                                    ),
                                    Row(
                                      crossAxisAlignment:CrossAxisAlignment.center,
                                      mainAxisAlignment:MainAxisAlignment.start,
                                      children: <Widget>[
                                        Expanded(
                                            child: Text(
                                            store.storeAddress,
                                            overflow: TextOverflow.ellipsis,
                                            maxLines: 2,
                                            style: TextStyle(
                                                fontSize: 14,
                                                color: Theme.of(context).textTheme.subtitle1.color,
                                            )
                                          ),
                                        )
                                      ],
                                    ),
                                    Padding(
                                      padding:
                                          const EdgeInsets.only(top: 4),
                                      child: Row(
                                        children: <Widget>[
                                          SmoothStarRating(
                                            allowHalfRating: true,
                                            starCount: 5,
                                            rating: store.ratingAve,
                                            size: 20,
                                            color: Theme.of(context).accentColor,
                                            borderColor: Theme.of(context).accentColor,
                                          ),
                                          Text(
                                            sprintf("%.1f %s", 
                                              [store.ratingAve,
                                              tr("ratings")]),
                                            style: TextStyle(
                                                fontSize: 14,
                                                color: Theme.of(context).textTheme.subtitle1.color),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(
                                right: 16, top: 8, left:16),
                            child: Column(
                              mainAxisAlignment:
                                  MainAxisAlignment.center,
                              crossAxisAlignment:
                                  CrossAxisAlignment.end,
                              children: <Widget>[
                                Text(
                                  sprintf("%.1f %s", 
                                    [store.distance,
                                    tr("km")]
                                  ),
                                  style: TextStyle(
                                    fontSize: 14,
                                    color: Theme.of(context).textTheme.subtitle1.color
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
                Positioned(
                  top: 8,
                  right: 8,
                  child: Material(
                    color: Colors.transparent,
                    child: FutureBuilder<Fave>(
                      future: DBProvider.instance.getFaveByStoreId(store.storeId),
                      builder: (BuildContext context, AsyncSnapshot<Fave> snapshot) {
                        if(!snapshot.hasData || snapshot == null) {
                          return Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Icon(
                              Icons.favorite_border,
                              color: Theme.of(context).accentColor,
                            ),
                          );
                        }
                        else {
                          return Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Icon(
                              Icons.favorite,
                              color: Theme.of(context).accentColor,
                            ),
                          );
                        }
                      }
                    ),
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
