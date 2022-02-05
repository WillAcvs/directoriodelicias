
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/models/reviews.dart';

class ReviewView extends StatelessWidget {
  const ReviewView(
      {Key key,
      this.review,
      this.callback})
      : super(key: key);

  final VoidCallback callback;
  final Review review;

  @override
  Widget build(BuildContext context) {
    String photoUrl = review.thumbUrl.length > 0 ? review.thumbUrl : Helpers.getThemedPhotoPlaceHolder(context);
    
    return Padding(
      padding: const EdgeInsets.only(
          left: 24, right: 24, top: 8, bottom: 16),
      child: InkWell(
        splashColor: Colors.transparent,
        onTap: () {
          callback();
        },
        child: Container(
          child: ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(16.0)),
            child: Stack(
              children: <Widget>[
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Container(
                      width: 80,
                      height: 80,
                      child: ClipRRect(
                        borderRadius: const BorderRadius.all(Radius.circular(40.0)),
                        child: Helpers.loadCacheImageThumb(imageUrl: photoUrl),
                      ),
                    ),
                    Expanded(
                      child: Container(
                      color: Colors.transparent,
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
                                      review.fullName,
                                      textAlign: TextAlign.start,
                                      style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        fontSize: 18,
                                        color: Theme.of(context).textTheme.caption.color
                                      ),
                                    ),
                                    Padding(
                                      padding: EdgeInsets.only(top:5),
                                      child: Text(
                                        Helpers.formatDate(review.createdAt, 'MM/dd/yyyy hh:mm a'),
                                        overflow: TextOverflow.ellipsis,
                                        maxLines: 1,
                                        style: TextStyle(
                                            fontSize: 12,
                                            color: Theme.of(context).textTheme.subtitle1.color),
                                      ),
                                    ),
                                    Padding(
                                      padding: const EdgeInsets.only(top:5.0),
                                      child: Text(
                                        review.review,
                                        overflow: TextOverflow.ellipsis,
                                        maxLines: 2,
                                        style: TextStyle(
                                            fontSize: 14,
                                            color: Theme.of(context).textTheme.subtitle1.color),
                                      ),
                                    )
                                    
                                  ],
                                ),
                              ),
                            ),
                          ),
                          
                        ],
                      ),
                    ),)
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
