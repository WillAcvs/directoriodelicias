import 'dart:typed_data';

import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:directorio_delicias/models/category.dart';
import 'package:directorio_delicias/models/news.dart';
import 'package:directorio_delicias/models/notification.dart';
import 'package:directorio_delicias/models/reviews.dart';
import 'package:directorio_delicias/models/status_code.dart';
import 'package:directorio_delicias/models/store.dart';
import 'package:directorio_delicias/models/user.dart';

class DataHandler {
  StatusCode? status;
  List<Category>? categories;
  List<News>? news;
  List<Store>? nearbyStores;
  List<Store>? topRated;
  List<Store>? stores;
  List<Review>? reviews;
  List<Notification>? notifications;
  User? user;
  bool? isLoggedOut;
  int? storeId;
  int? canRate;
  int min;
  int max;
  int? totalRowCount;
  int? rowCount;
  LatLng? latLng;
  Uint8List? bytes;
  bool? isEdited;
  bool? isLogged;

  DataHandler({
    this.latLng,
    this.bytes,
    this.categories,
    this.nearbyStores,
    this.news,
    this.status,
    this.topRated,
    this.stores,
    this.user,
    this.storeId,
    this.canRate,
    this.isLoggedOut,
    this.reviews,
    this.min = 0,
    this.max = 0,
    this.totalRowCount,
    this.rowCount,
    this.isEdited,
    this.isLogged,
    this.notifications,
  });
}
