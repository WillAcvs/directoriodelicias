

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

class RatingServices {

  static double rating = 0;

  static void rate({context, store}) async {

    User loggedUser = MyApp.loggedUser;
    if(loggedUser == null) {
      Helpers.showAlertDialog(
        context:context,
        title: tr("error_not_logged_rating"),
        message: tr("error_not_logged_rating_details"),
        onTapOk: () { }
      );
      return;
    }

    DataHandler dataHandler = await showDialog<DataHandler>(
      context: context,
      barrierColor: Colors.black87,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return FutureBuilder<DataHandler>(
          future: GetIt.instance.get<DataParser>().getRatingStore(
            userId: loggedUser.userId,
            loginHash: loggedUser.loginHash,
            storeId: store.storeId
          ),
          builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
            if (!snapshot.hasData) {
              return Center(
                child: CircularProgressIndicator(
                  valueColor: AlwaysStoppedAnimation(
                    AppTheme.primaryColor
                  )
                ),
              );
            } else {
              Navigator.pop(context, snapshot.data);
              return Center();
            }
          },
        );
      },
    );

    if(dataHandler == null) {
      Helpers.showAlertDialog(
        context:context,
        title: tr("error_rating"),
        message: tr("error_problems_ecountered_details"),
        onTapOk: () { }
      );
    }
    else if(dataHandler.canRate == 0) {
      Helpers.showAlertDialog(
        context:context,
        title: tr("error_rating"),
        message: tr("error_rating_details"),
        onTapOk: () { }
      );
    }
    else {
      Helpers.showRatingDialog(
        context: context,
        onTapOk: () async{
          await Future.delayed(Duration(milliseconds: 500));
          syncRating(
            ratingValue: rating, 
            loggedUser: loggedUser, 
            storeId: store.storeId, 
            context: context
          );
        },
        onTapRate: (value) {
          rating = value;
        }
      );
    }
  }

  static void syncRating({ratingValue, loggedUser, storeId, context}) async {

    DataHandler dataHandler = await showDialog<DataHandler>(
      context: context,
      barrierColor: Colors.black87,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return FutureBuilder<DataHandler>(
          future: GetIt.instance.get<DataParser>().postRating(
            userId: loggedUser.userId,
            loginHash: loggedUser.loginHash,
            storeId: storeId,
            rating: ratingValue
          ),
          builder: (BuildContext context, AsyncSnapshot<DataHandler> snapshot) {
            if (!snapshot.hasData) {
              return Center(
                child: CircularProgressIndicator(
                  valueColor: AlwaysStoppedAnimation(
                    AppTheme.primaryColor
                  )
                ),
              );
            } else {
              Navigator.pop(context, snapshot.data);
              return Center();
            }
          },
        );
      },
    );
    
    if(dataHandler == null) {
      Helpers.showAlertDialog(
        context:context,
        title: tr("error_rating"),
        message: tr("error_problems_ecountered_details"),
        onTapOk: () { }
      );
    }
    else {
      Helpers.showAlertDialog(
        context:context,
        title: tr("rating_success"),
        message: tr("rating_success_details"),
        onTapOk: () { }
      );
    }
  }
}