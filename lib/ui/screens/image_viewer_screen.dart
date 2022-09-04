import 'package:drag_down_to_pop/drag_down_to_pop.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:photo_view/photo_view.dart';
import 'package:photo_view/photo_view_gallery.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/models/photo.dart';

class ImageViewerScreen extends StatefulWidget {
  ImageViewerScreen({Key? key, this.photos}) : super(key: key);

  final List<Photo>? photos;

  @override
  _ImageViewerScreenState createState() => _ImageViewerScreenState();
}

class _ImageViewerScreenState extends State<ImageViewerScreen> {
  List<String> imageUrls = <String>[];
  PhotoViewScaleStateController _controller = PhotoViewScaleStateController();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var left;
    var right;
    if (Helpers.isRTL()) {
      right = 20.0;
    } else {
      left = 20.0;
    }

    return GestureDetector(
      onTap: () {
        Navigator.maybePop(context);
      },
      child: Material(
        child: Stack(
          children: [
            Container(
                color: Colors.black,
                child: PhotoViewGallery.builder(
                  scrollPhysics: const BouncingScrollPhysics(),
                  builder: (BuildContext context, int index) {
                    return PhotoViewGalleryPageOptions(
                      maxScale: 4.0,
                      minScale: PhotoViewComputedScale.contained,
                      tightMode: true,
                      imageProvider:
                          NetworkImage(widget.photos![index].photoUrl),
                      initialScale: PhotoViewComputedScale.contained,
                      scaleStateController: _controller
                        ..scaleState = PhotoViewScaleState.initial,
                    );
                  },
                  itemCount: widget.photos!.length,

                  // loadFailedChild: Center(
                  //   child: Container(
                  //       child: Icon(Icons.image_not_supported_outlined,
                  //           size: 80, color: Colors.grey[600])),
                  // ),
                  loadingBuilder: (context, event) => Center(
                    child: Container(
                      color: Colors.transparent,
                      width: 20.0,
                      height: 20.0,
                      child: SpinKitWave(
                        itemCount: 3,
                        color: Theme.of(context).accentColor,
                        size: 20,
                      ),
                    ),
                  ),
                )),
            Positioned(
              top: AppBar().preferredSize.height,
              left: left,
              right: right,
              child: Material(
                color: Colors.transparent,
                child: InkWell(
                  borderRadius: const BorderRadius.all(
                    Radius.circular(50.0),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Icon(
                      Icons.close,
                      color: Colors.white,
                    ),
                  ),
                  onTap: () {
                    Navigator.maybePop(context);
                  },
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}

class ImageViewerPageRoute extends MaterialPageRoute {
  ImageViewerPageRoute({required WidgetBuilder builder})
      : super(builder: builder, maintainState: false);

  @override
  Widget buildTransitions(BuildContext context, Animation<double> animation,
      Animation<double> secondaryAnimation, Widget child) {
    return const DragDownToPopPageTransitionsBuilder()
        .buildTransitions(this, context, animation, secondaryAnimation, child);
  }

  @override
  bool canTransitionFrom(TransitionRoute previousRoute) {
    return false;
  }

  @override
  bool canTransitionTo(TransitionRoute nextRoute) {
    return false;
  }
}
