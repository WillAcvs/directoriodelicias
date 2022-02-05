
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/models/category.dart';

class CategoryListView extends StatelessWidget {
  const CategoryListView(
      {Key key,
      this.category,
      this.padding,
      this.callback})
      : super(key: key);

  final VoidCallback callback;
  final Category category;
  final EdgeInsets padding;

  @override
  Widget build(BuildContext context) {
    String photoUrl = "";
    if(category.categoryIcon.length > 0)
      photoUrl = category.categoryIcon;

    return Padding(
      padding: padding,
      child: InkWell(
        splashColor: Colors.transparent,
        onTap: () {
          callback();
        },
        child: Container(
          decoration: BoxDecoration(
            color: Theme.of(context).textTheme.bodyText1.color,
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
              alignment: AlignmentDirectional.center,
              children: <Widget>[
                Column(
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    AspectRatio(
                      aspectRatio: 2.0,
                      child: Helpers.loadCacheImage(imageUrl: photoUrl)
                    ),
                    Container(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Expanded(
                            child: Container(
                              child: Padding(
                                padding: const EdgeInsets.only(left: 16, top: 8, bottom: 8, right: 16),
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: <Widget>[
                                    Text(
                                      category.category,
                                      textAlign: TextAlign.center,
                                      style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        fontSize: 22,
                                        color: Theme.of(context).textTheme.caption.color,
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
