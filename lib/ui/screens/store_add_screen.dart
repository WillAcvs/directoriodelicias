import 'dart:async';
import 'dart:collection';
import 'dart:io';
import 'dart:typed_data';

import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/foundation.dart' as foundation;
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:image_picker/image_picker.dart';
import 'package:rounded_loading_button/rounded_loading_button.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/commons/store_services.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/db/db_provider.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/models/category.dart';
import 'package:directorio_delicias/models/user.dart';
import 'package:directorio_delicias/session/session_storage.dart';
import 'package:directorio_delicias/ui/screens/store_map_screen.dart';
import 'package:directorio_delicias/ui/widgets/category_selection_item.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';
import 'package:direct_select/direct_select.dart';

class StoreAddScreen extends StatefulWidget {
  StoreAddScreen({Key? key}) : super(key: key);

  @override
  _StoreAddScreenState createState() => _StoreAddScreenState();
}

class _StoreAddScreenState extends State<StoreAddScreen> {
  @override
  void initState() {
    super.initState();
  }

  final RoundedLoadingButtonController _btnController =
      new RoundedLoadingButtonController();
  final TextEditingController _ctrlStoreName = new TextEditingController();
  final TextEditingController _ctrlStoreAddress = new TextEditingController();
  final TextEditingController _ctrlStoreDesc = new TextEditingController();
  final TextEditingController _ctrlSMSNo = new TextEditingController();
  final TextEditingController _ctrlPhoneNo = new TextEditingController();
  final TextEditingController _ctrlEmail = new TextEditingController();
  final TextEditingController _ctrlWebsite = new TextEditingController();

  TimeOfDay _timeStartMon = TimeOfDay(hour: 0, minute: 0);
  TimeOfDay _timeEndMon = TimeOfDay(hour: 23, minute: 59);
  TimeOfDay _timeStartTue = TimeOfDay(hour: 0, minute: 0);
  TimeOfDay _timeEndTue = TimeOfDay(hour: 23, minute: 59);
  TimeOfDay _timeStartWed = TimeOfDay(hour: 0, minute: 0);
  TimeOfDay _timeEndWed = TimeOfDay(hour: 23, minute: 59);
  TimeOfDay _timeStartThu = TimeOfDay(hour: 0, minute: 0);
  TimeOfDay _timeEndThu = TimeOfDay(hour: 23, minute: 59);
  TimeOfDay _timeStartFri = TimeOfDay(hour: 0, minute: 0);
  TimeOfDay _timeEndFri = TimeOfDay(hour: 23, minute: 59);
  TimeOfDay _timeStartSat = TimeOfDay(hour: 0, minute: 0);
  TimeOfDay _timeEndSat = TimeOfDay(hour: 23, minute: 59);
  TimeOfDay _timeStartSun = TimeOfDay(hour: 0, minute: 0);
  TimeOfDay _timeEndSun = TimeOfDay(hour: 23, minute: 59);
  Completer<GoogleMapController> _controller = Completer();

  var storageService = locator<SessionStorage>();
  // final _formKey = GlobalKey<FormState>();
  List<Category>? categories;
  int selectedCategoryIndex = 0;
  Widget? root;
  Uint8List? imageBytes;
  LatLng? selectedLatLng;
  List<String> photoPaths = <String>[];
  final picker = ImagePicker();
  User? loggedUser;

  void send() async {
    FocusScope.of(context).unfocus();
    if (_ctrlStoreName.text.length == 0) {
      Helpers.showAlertDialog(
          context: context,
          message: tr("store_name_empty"),
          title: tr("action_error"),
          onTapOk: null);
      return;
    } else if (selectedLatLng == null) {
      Helpers.showAlertDialog(
          context: context,
          title: tr("location_error"),
          message: tr("location_error_details"),
          onTapOk: null);
      return;
    } else if (photoPaths.length == 0) {
      Helpers.showAlertDialog(
          context: context,
          title: tr("photo_error"),
          message: tr("photo_error_details"),
          onTapOk: null);
      return;
    }

    StoreServices.addStore(
        storeId: "0",
        storeName: _ctrlStoreName.text,
        storeAddress: _ctrlStoreAddress.text,
        storeDesc: _ctrlStoreDesc.text,
        lat: selectedLatLng!.latitude.toString(),
        lon: selectedLatLng!.longitude.toString(),
        smsNo: _ctrlSMSNo.text,
        phoneNo: _ctrlPhoneNo.text,
        email: _ctrlEmail.text,
        website: _ctrlWebsite.text,
        categoryId: categories![selectedCategoryIndex].categoryId.toString(),
        monOpen: _timeStartMon,
        monClose: _timeEndMon,
        tueOpen: _timeStartTue,
        tueClose: _timeEndTue,
        wedOpen: _timeStartWed,
        wedClose: _timeEndWed,
        thuOpen: _timeStartThu,
        thuClose: _timeEndThu,
        friOpen: _timeStartFri,
        friClose: _timeEndFri,
        satOpen: _timeStartSat,
        satClose: _timeEndSat,
        sunOpen: _timeStartSun,
        sunClose: _timeEndSun,
        userId: loggedUser!.userId,
        loginHash: loggedUser!.loginHash,
        photoPaths: photoPaths,
        context: context,
        photoIdsDeleted: <int>[]);
  }

  List<Widget> _buildItems() {
    return categories!
        .map((val) => CategorySelectionItem(
              category: val,
            ))
        .toList();
  }

  Future<bool> getData() async {
    categories = await DBProvider.instance.getCategoriesLeafNode();
    loggedUser = MyApp.loggedUser;
    await Future<dynamic>.delayed(const Duration(milliseconds: 0));
    return true;
  }

  Widget showMain() {
    root = Container(
      color: Theme.of(context).backgroundColor,
      child: SafeArea(
        top: false,
        child: Scaffold(
          backgroundColor: Theme.of(context).backgroundColor,
          body: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                getAppBarUI(),
                main(),
              ]),
        ),
      ),
    );
    return root!;
  }

  Widget googleMap() {
    final CameraPosition cameraPosition = CameraPosition(
      target: LatLng(Config.DEFAULT_LAT, Config.DEFAULT_LON),
      zoom: 18,
    );

    Marker marker = Marker(
        consumeTapEvents: false,
        markerId: MarkerId("CURRENT_LOCATION"),
        position: LatLng(Config.DEFAULT_LAT, Config.DEFAULT_LON),
        anchor: Offset(0, -0.75),
        icon: BitmapDescriptor.defaultMarker,
        infoWindow: InfoWindow(
          title: tr("tap_and_hold"),
          snippet: tr("drag_me_around"),
        ));

    Set<Marker> markers = HashSet<Marker>();
    markers.add(marker);

    return ClipRRect(
        borderRadius: BorderRadius.all(Radius.circular(10)),
        child: GoogleMap(
          gestureRecognizers:
              <foundation.Factory<OneSequenceGestureRecognizer>>[
            new foundation.Factory<OneSequenceGestureRecognizer>(
              () => new EagerGestureRecognizer(),
            ),
          ].toSet(),
          zoomControlsEnabled: false,
          zoomGesturesEnabled: false,
          compassEnabled: false,
          mapToolbarEnabled: false,
          scrollGesturesEnabled: false,
          rotateGesturesEnabled: false,
          tiltGesturesEnabled: false,
          markers: markers,
          myLocationButtonEnabled: false,
          mapType: MapType.normal,
          initialCameraPosition: cameraPosition,
          onMapCreated: (GoogleMapController controller) {
            _controller.complete(controller);
            controller
                .animateCamera(CameraUpdate.newCameraPosition(cameraPosition));
            controller.showMarkerInfoWindow(marker.markerId);
          },
        ));
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<bool>(
      future: getData(),
      builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
          return showMain();
        }
      },
    );
  }

  Widget main() {
    var isDark = Theme.of(context).brightness == Brightness.dark;
    String mapPlaceholder = isDark
        ? Config.DEFAULT_MAP_DARK_PLACEHOLDER
        : Config.DEFAULT_MAP_PLACEHOLDER;

    var categoryWidget;
    if (selectedCategoryIndex < categories!.length) {
      categoryWidget = DirectSelect(
          itemExtent: 50.0,
          mode: DirectSelectMode.tap,
          selectedIndex: selectedCategoryIndex,
          backgroundColor: Theme.of(context).accentColor,
          child: CategorySelectionItem(
            isForList: false,
            category: categories![selectedCategoryIndex],
          ),
          onSelectedItemChanged: (index) {
            setState(() {
              selectedCategoryIndex = index!;
            });
          },
          items: _buildItems());
    } else {
      categoryWidget = Helpers.createFormField(
          controller: _ctrlStoreAddress,
          placeholder: tr("no_categories_to_show"),
          validatorText: null,
          context: context,
          enabled: false);
    }

    double leftPhoto = 0.0;
    double rightPhoto = 0.0;

    if (Helpers.isRTL()) {
      rightPhoto = 8.0;
    } else {
      leftPhoto = 8.0;
    }

    return Expanded(
      child: Padding(
        padding: EdgeInsets.all(20),
        child: SingleChildScrollView(
          child: SizedBox(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Helpers.createHeader(
                  context: context,
                  text: tr("store_name"),
                ),
                Helpers.createFormField(
                    controller: _ctrlStoreName,
                    placeholder: tr("store_name"),
                    // validatorText: tr("store_name_empty"),
                    context: context),
                Helpers.createHeader(
                  context: context,
                  text: tr("store_address"),
                ),
                Helpers.createFormField(
                    controller: _ctrlStoreAddress,
                    placeholder: tr("store_address"),
                    validatorText: null,
                    context: context),
                Helpers.createHeader(
                  context: context,
                  text: tr("sms_no"),
                ),
                Helpers.createFormField(
                    controller: _ctrlSMSNo,
                    placeholder: tr("sms_no"),
                    validatorText: null,
                    context: context),
                Helpers.createHeader(
                  context: context,
                  text: tr("phone_no"),
                ),
                Helpers.createFormField(
                    controller: _ctrlPhoneNo,
                    placeholder: tr("phone_no"),
                    validatorText: null,
                    context: context),
                Helpers.createHeader(
                  context: context,
                  text: tr("email"),
                ),
                Helpers.createFormField(
                    controller: _ctrlEmail,
                    placeholder: tr("email"),
                    validatorText: null,
                    context: context),
                Helpers.createHeader(
                  context: context,
                  text: tr("website"),
                ),
                Helpers.createFormField(
                    controller: _ctrlWebsite,
                    placeholder: tr("website"),
                    validatorText: null,
                    context: context),
                Helpers.createHeader(
                  context: context,
                  text: tr("category"),
                ),
                categoryWidget,
                Helpers.createHeader(
                  context: context,
                  text: tr("desc"),
                ),
                Helpers.createFormField(
                    controller: _ctrlStoreDesc,
                    placeholder: tr("desc"),
                    validatorText: null,
                    context: context,
                    height: 150),
                SizedBox(
                  height: 50,
                ),
                Helpers.createHeader(
                  context: context,
                  text: tr("operating_hours"),
                ),
                SizedBox(
                  height: 20,
                ),
                Helpers.createHeader(
                  context: context,
                  text: tr("mon"),
                ),
                Helpers.createTimeRange(
                    context: context,
                    startTime: _timeStartMon,
                    endTime: _timeEndMon,
                    onTimeStartChanged: (newTime) {
                      setState(() {
                        _timeStartMon = newTime;
                      });
                    },
                    onTimeEndChanged: (newTime) {
                      setState(() {
                        _timeEndMon = newTime;
                      });
                    }),
                Helpers.createHeader(
                  context: context,
                  text: tr("tue"),
                ),
                Helpers.createTimeRange(
                    context: context,
                    startTime: _timeStartTue,
                    endTime: _timeEndTue,
                    onTimeStartChanged: (newTime) {
                      setState(() {
                        _timeStartTue = newTime;
                      });
                    },
                    onTimeEndChanged: (newTime) {
                      setState(() {
                        _timeEndTue = newTime;
                      });
                    }),
                Helpers.createHeader(
                  context: context,
                  text: tr("wed"),
                ),
                Helpers.createTimeRange(
                    context: context,
                    startTime: _timeStartWed,
                    endTime: _timeEndWed,
                    onTimeStartChanged: (newTime) {
                      setState(() {
                        _timeStartWed = newTime;
                      });
                    },
                    onTimeEndChanged: (newTime) {
                      setState(() {
                        _timeEndWed = newTime;
                      });
                    }),
                Helpers.createHeader(
                  context: context,
                  text: tr("thu"),
                ),
                Helpers.createTimeRange(
                    context: context,
                    startTime: _timeStartThu,
                    endTime: _timeEndThu,
                    onTimeStartChanged: (newTime) {
                      setState(() {
                        _timeStartThu = newTime;
                      });
                    },
                    onTimeEndChanged: (newTime) {
                      setState(() {
                        _timeEndThu = newTime;
                      });
                    }),
                Helpers.createHeader(
                  context: context,
                  text: tr("fri"),
                ),
                Helpers.createTimeRange(
                    context: context,
                    startTime: _timeStartFri,
                    endTime: _timeEndFri,
                    onTimeStartChanged: (newTime) {
                      setState(() {
                        _timeStartFri = newTime;
                      });
                    },
                    onTimeEndChanged: (newTime) {
                      setState(() {
                        _timeEndFri = newTime;
                      });
                    }),
                Helpers.createHeader(
                  context: context,
                  text: tr("sat"),
                ),
                Helpers.createTimeRange(
                    context: context,
                    startTime: _timeStartSat,
                    endTime: _timeEndSat,
                    onTimeStartChanged: (newTime) {
                      setState(() {
                        _timeStartSat = newTime;
                      });
                    },
                    onTimeEndChanged: (newTime) {
                      setState(() {
                        _timeEndSat = newTime;
                      });
                    }),
                Helpers.createHeader(
                  context: context,
                  text: tr("sun"),
                ),
                Helpers.createTimeRange(
                    context: context,
                    startTime: _timeStartSun,
                    endTime: _timeEndSun,
                    onTimeStartChanged: (newTime) {
                      setState(() {
                        _timeStartSun = newTime;
                      });
                    },
                    onTimeEndChanged: (newTime) {
                      setState(() {
                        _timeEndSun = newTime;
                      });
                    }),
                SizedBox(
                  height: 50,
                ),
                Helpers.createHeader(
                  context: context,
                  text: tr("store_location"),
                ),
                SizedBox(
                  height: 10,
                ),
                InkWell(
                    onTap: () async {
                      FocusScope.of(context).unfocus();
                      DataHandler dataHandler = await Navigator.push<dynamic>(
                        context,
                        MaterialPageRoute<dynamic>(
                            builder: (BuildContext context) => StoreMapScreen(),
                            fullscreenDialog: true),
                      );
                      if (dataHandler != null) {
                        setState(() {
                          imageBytes = dataHandler.bytes;
                          selectedLatLng = dataHandler.latLng;
                        });
                      }
                    },
                    child: Container(
                        height: 200,
                        width: MediaQuery.of(context).size.width,
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(8),
                          child: AspectRatio(
                              aspectRatio: 1.2,
                              child: imageBytes != null
                                  ? FadeInImage(
                                      fit: BoxFit.cover,
                                      placeholder: AssetImage(mapPlaceholder),
                                      image: MemoryImage(imageBytes!),
                                    )
                                  : FadeInImage(
                                      fit: BoxFit.cover,
                                      placeholder: AssetImage(mapPlaceholder),
                                      image: AssetImage(mapPlaceholder),
                                    )),
                        ))),
                SizedBox(
                  height: 50,
                ),
                Helpers.createHeader(
                  context: context,
                  text: tr("store_photos"),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 10),
                  child: SizedBox(
                    height: 140,
                    child: Row(
                      children: [
                        Expanded(
                          child: Container(
                            decoration: BoxDecoration(
                              color:
                                  Theme.of(context).textTheme.bodyText1?.color,
                              borderRadius: BorderRadius.circular(8),
                            ),
                            child: ListView.builder(
                              itemCount: photoPaths.length,
                              scrollDirection: Axis.horizontal,
                              itemBuilder: (BuildContext context, int index) {
                                return InkWell(
                                  child: Padding(
                                      padding: EdgeInsets.only(
                                          left: leftPhoto,
                                          top: 8.0,
                                          bottom: 8.0,
                                          right: rightPhoto),
                                      child: Container(
                                          height: 140,
                                          width: 140,
                                          child: ClipRRect(
                                            borderRadius:
                                                BorderRadius.circular(8),
                                            child: AspectRatio(
                                                aspectRatio: 1.2,
                                                child: FadeInImage(
                                                  fit: BoxFit.cover,
                                                  placeholder: AssetImage(Helpers
                                                      .getThemedPhotoPlaceHolder(
                                                          context)),
                                                  image: FileImage(
                                                      File(photoPaths[index])),
                                                )),
                                          ))),
                                  onTap: () {
                                    Helpers.showDualButtonDialog(
                                        context: context,
                                        title: tr("delete_photo"),
                                        message: tr("delete_photo_details"),
                                        onTapDismiss: () {},
                                        onTapOk: () {
                                          setState(() {
                                            photoPaths.removeAt(index);
                                          });
                                        });
                                  },
                                );
                              },
                            ),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(left: 10, right: 10),
                          child: Container(
                              width: 50,
                              height: MediaQuery.of(context).size.height,
                              decoration: BoxDecoration(
                                color: Theme.of(context).accentColor,
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: Material(
                                color: Theme.of(context).accentColor,
                                borderRadius: BorderRadius.circular(8),
                                child: InkWell(
                                    borderRadius: BorderRadius.circular(8),
                                    child: Icon(
                                      Icons.add_a_photo,
                                      color: Theme.of(context)
                                          .floatingActionButtonTheme
                                          .foregroundColor,
                                    ),
                                    onTap: () async {
                                      if (photoPaths.length ==
                                          Config.MAX_PHOTO_UPLOAD) {
                                        Helpers.showAlertDialog(
                                          context: context,
                                          title: tr("max_photos_reached"),
                                          message:
                                              tr("max_photos_reached_details"),
                                        );
                                        return;
                                      }
                                      PickedFile? pickedFile =
                                          await picker.getImage(
                                              source: ImageSource.gallery,
                                              imageQuality: 90);
                                      setState(() {
                                        if (pickedFile != null) {
                                          photoPaths.add(pickedFile.path);
                                        }
                                      });
                                    }),
                              )),
                        )
                      ],
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 16),
                  child: Center(
                    child: RoundedLoadingButton(
                      animateOnTap: false,
                      width: MediaQuery.of(context).size.width,
                      color: Theme.of(context).accentColor,
                      controller: _btnController,
                      onPressed: send,
                      child: Text(
                        tr("add"),
                        style: TextStyle(
                          fontWeight: FontWeight.w500,
                          color: Theme.of(context)
                              .floatingActionButtonTheme
                              .foregroundColor,
                        ),
                      ),
                    ),
                  ),
                ),
                SizedBox(
                  height: 50,
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget getAppBarUI() {
    return Container(
      decoration: BoxDecoration(
        color: Theme.of(context).backgroundColor,
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
                    Navigator.pop(context);
                  },
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Icon(Icons.close),
                  ),
                ),
              ),
            ),
            Expanded(
              child: Center(
                child: Text(
                  tr("add_store"),
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
            )
          ],
        ),
      ),
    );
  }
}
