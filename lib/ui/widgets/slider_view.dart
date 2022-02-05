
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/models/store.dart';


class SliderView extends StatefulWidget {
  const SliderView({Key key, this.callBack, this.stores, this.onPageChanged}) : super(key: key);

  final Function(Store) callBack;
  final Function(int index, CarouselPageChangedReason reason) onPageChanged;

  final List<Store> stores;
  @override
  _SliderViewState createState() => _SliderViewState();
}

class _SliderViewState extends State<SliderView> {

  CarouselController controller = CarouselController();

  @override
  void initState() {
    super.initState();
  }

  Future<bool> getData() async {
    await Future<dynamic>.delayed(const Duration(milliseconds: 200));
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
            return CarouselSlider(
              options: CarouselOptions(
                height: 200.0,
                onPageChanged: (index, reason) => widget.onPageChanged(index, reason),
              ),
              items: List.generate(
                widget.stores.length, 
                (int index) {
                    return SliderWidget(
                      callback: () {
                        widget.callBack(widget.stores[index]);
                      },
                      store: widget.stores[index],
                    );
                }
              ),
              carouselController: controller,
            );
          }
        },
      ),
    );
  }
}

class SliderWidget extends StatelessWidget {
  const SliderWidget(
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
        child: Padding(
          padding: const EdgeInsets.only(left: 5.0, right: 5.0),
          child: Stack(
            alignment: AlignmentDirectional.topCenter,
            children: <Widget>[
              Container(
                child: Column(
                  children: <Widget>[
                    const SizedBox(
                      height: 40,
                    ),
                    Expanded(
                      child: Container(
                        width: 290,
                        decoration: BoxDecoration(
                          color: Theme.of(context).textTheme.bodyText2.color,
                          borderRadius: const BorderRadius.all(Radius.circular(16.0)),
                        ),
                        child: Column(
                          children: <Widget>[
                            const SizedBox(
                              height: 120,
                            ),
                            Expanded(
                              child: Container(
                                child: Column(
                                  children: <Widget>[
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
                                          Expanded(
                                            child: Text(
                                              store.storeName,
                                              overflow: TextOverflow.fade,
                                              maxLines: 1,
                                              textAlign: TextAlign.start,
                                              style: TextStyle(
                                                fontWeight: FontWeight.w600,
                                                fontSize: 16,
                                                letterSpacing: 0.27,
                                                color: Theme.of(context).textTheme.caption.color,
                                              ),
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
                                                    color: Theme.of(context).textTheme.subtitle1.color,
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
                            
                          ],
                        ),
                      ),
                    ),
                    
                  ],
                ),
              ),
              Container(
                child: Padding(
                  padding: const EdgeInsets.only(top: 16, right: 16, left: 16, bottom: 16),
                  child: Container(
                    width: 280,
                    height: 140,
                    decoration: BoxDecoration(
                      borderRadius: const BorderRadius.all(Radius.circular(16.0)),
                      boxShadow: <BoxShadow>[
                        BoxShadow(
                            color: Theme.of(context).shadowColor,
                            offset: const Offset(0.0, 0.0),
                            blurRadius: 6.0),
                      ],
                    ),
                    child: ClipRRect(
                      borderRadius: const BorderRadius.all(Radius.circular(16.0)),
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
      ),
    );
  }
}
