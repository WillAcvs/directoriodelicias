import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:ndialog/ndialog.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/models/user.dart';
import 'package:directorio_delicias/themes/app_theme.dart';

class StoreServices {
  static void addStore(
      {required storeId,
      required storeName,
      required storeAddress,
      required storeDesc,
      required lat,
      required lon,
      required smsNo,
      required phoneNo,
      required email,
      required website,
      required categoryId,
      required TimeOfDay monOpen,
      required TimeOfDay monClose,
      required TimeOfDay tueOpen,
      required TimeOfDay tueClose,
      required TimeOfDay wedOpen,
      required TimeOfDay wedClose,
      required thuOpen,
      required TimeOfDay thuClose,
      required TimeOfDay friOpen,
      required TimeOfDay friClose,
      required TimeOfDay satOpen,
      required TimeOfDay satClose,
      required TimeOfDay sunOpen,
      required TimeOfDay sunClose,
      required userId,
      required loginHash,
      required List<dynamic> photoPaths,
      required List<int> photoIdsDeleted,
      required context}) async {
    User? loggedUser = MyApp.loggedUser;
    if (loggedUser == null) {
      Helpers.showAlertDialog(
          context: context,
          title: tr("error_not_logged"),
          message: tr("error_not_logged_details"),
          onTapOk: () {});
      return;
    }

    DataHandler? dataHandler = await showDialog<DataHandler>(
      context: context,
      barrierColor: Colors.black87,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return FutureBuilder<DataHandler>(
          future: GetIt.instance.get<DataParser>().insertStore(
              storeId: storeId.toString(),
              storeName: storeName,
              storeAddress: storeAddress,
              storeDesc: storeDesc,
              lat: lat,
              lon: lon,
              smsNo: smsNo,
              phoneNo: phoneNo,
              email: email,
              website: website,
              categoryId: categoryId,
              monOpen:
                  monOpen.hour.toString() + ":" + monOpen.minute.toString(),
              monClose:
                  monClose.hour.toString() + ":" + monClose.minute.toString(),
              tueOpen:
                  tueOpen.hour.toString() + ":" + tueOpen.minute.toString(),
              tueClose:
                  tueClose.hour.toString() + ":" + tueClose.minute.toString(),
              wedOpen:
                  wedOpen.hour.toString() + ":" + wedOpen.minute.toString(),
              wedClose:
                  wedClose.hour.toString() + ":" + wedClose.minute.toString(),
              thuOpen:
                  thuOpen.hour.toString() + ":" + thuOpen.minute.toString(),
              thuClose:
                  thuClose.hour.toString() + ":" + thuClose.minute.toString(),
              friOpen:
                  friOpen.hour.toString() + ":" + friOpen.minute.toString(),
              friClose:
                  friClose.hour.toString() + ":" + friClose.minute.toString(),
              satOpen:
                  satOpen.hour.toString() + ":" + satOpen.minute.toString(),
              satClose:
                  satClose.hour.toString() + ":" + satClose.minute.toString(),
              sunOpen:
                  sunOpen.hour.toString() + ":" + sunOpen.minute.toString(),
              sunClose:
                  sunClose.hour.toString() + ":" + sunClose.minute.toString(),
              userId: loggedUser.userId,
              photoPaths: photoPaths,
              loginHash: loginHash,
              photoIdsDeleted: photoIdsDeleted),
          builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
            if (!snapshot.hasData) {
              return Center(
                child: CircularProgressIndicator(
                    valueColor: AlwaysStoppedAnimation(AppTheme.primaryColor)),
              );
            } else {
              Navigator.pop(context, snapshot.data);
              return Center();
            }
          },
        );
      },
    );

    if (dataHandler == null) {
      Helpers.showAlertDialog(
          context: context,
          title: tr("action_error"),
          message: tr("add_store_error_details"),
          onTapOk: () {});
    } else {
      dataHandler.isEdited = true;
      Navigator.pop(context, dataHandler);
    }
  }

  static void deleteStore(
      {@required storeId,
      @required userId,
      @required loginHash,
      @required context}) async {
    User? loggedUser = MyApp.loggedUser;
    if (loggedUser == null) {
      Helpers.showAlertDialog(
          context: context,
          title: tr("error_not_logged"),
          message: tr("error_not_logged_details"),
          onTapOk: () {});
      return;
    }

    DataHandler? dataHandler = await showDialog<DataHandler>(
      context: context,
      barrierColor: Colors.black87,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return FutureBuilder<DataHandler>(
          future: GetIt.instance.get<DataParser>().deleteStore(
                storeId: storeId.toString(),
                userId: loggedUser.userId,
                loginHash: loginHash,
              ),
          builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
            if (!snapshot.hasData) {
              return Center(
                child: CircularProgressIndicator(
                    valueColor: AlwaysStoppedAnimation(AppTheme.primaryColor)),
              );
            } else {
              Navigator.pop(context, snapshot.data);
              return Center();
            }
          },
        );
      },
    );

    if (dataHandler == null) {
      Helpers.showAlertDialog(
          context: context,
          title: tr("action_error"),
          message: tr("delete_store_error_details"),
          onTapOk: () {});
    } else {
      dataHandler.isEdited = true;
      Navigator.pop(context, dataHandler);
    }
  }
}
