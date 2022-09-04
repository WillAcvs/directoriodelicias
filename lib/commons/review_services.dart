import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:ndialog/ndialog.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/themes/app_theme.dart';

class ReviewServices {
  static double rating = 0;
  static String reviewStr = "";

  static void showReview(
      {context,
      required int storeId,
      loggedUser,
      Function? onSuccessReview}) async {
    if (loggedUser == null) {
      Helpers.showAlertDialog(
          context: context,
          title: tr("error_not_logged_review"),
          message: tr("error_not_logged_review_details"),
          onTapOk: () {});
      return;
    }

    reviewStr = "";
    Helpers.showReviewDialog(
        context: context,
        onTapOk: () async {
          await Future.delayed(Duration(milliseconds: 500));
          syncReview(
              review: reviewStr,
              loggedUser: loggedUser,
              storeId: storeId,
              context: context,
              onSuccessReview: onSuccessReview!());
        },
        onTapReview: (value) {
          reviewStr = value;
        });
  }

  static void syncReview(
      {review,
      loggedUser,
      required int storeId,
      context,
      onSuccessReview}) async {
    DataHandler? dataHandler = await showDialog<DataHandler>(
      context: context,
      barrierColor: Colors.black87,
      barrierDismissible: true,
      builder: (BuildContext context) {
        return FutureBuilder<DataHandler?>(
          future: GetIt.instance.get<DataParser>().postReview(
              userId: loggedUser.userId,
              loginHash: loggedUser.loginHash,
              storeId: storeId,
              review: review),
          builder:
              (BuildContext context, AsyncSnapshot<DataHandler?> snapshot) {
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
          title: tr("error_review"),
          message: tr("error_problems_ecountered_details"),
          onTapOk: () {});
    } else {
      if (onSuccessReview != null) onSuccessReview();
    }
  }
}
