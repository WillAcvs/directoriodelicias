import 'dart:collection';
import 'dart:typed_data';

import 'package:flutter/foundation.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/db/db_provider.dart';
import 'package:directorio_delicias/models/fave.dart';
import 'package:directorio_delicias/models/store.dart';

class DataHandlerNotifier extends ChangeNotifier {
  late DataHandler _dataHandler;
  DataHandler get dataHandler => _dataHandler;

  bool _asc = true;
  bool get asc => _asc;

  late Uint8List _imageBytes;
  Uint8List get imageBytes => _imageBytes;

  Set<Marker> _markers = Set<Marker>();
  Set<Marker> get markers => _markers;

  bool _isOpen = false;
  bool get isOpen => _isOpen;

  bool _isLoading = false;
  bool get isLoading => _isLoading;

  void setIsLoading(bool isLoading) {
    this._isLoading = isLoading;
  }

  bool _hasMore = true;
  bool get hasMore => _hasMore;

  bool _loadingMore = false;
  bool get loadingMore => _loadingMore;

  int _count = 0;
  get count => _count;

  void setCount(count) {
    this._count = count;
  }

  void setCountNotify(count) {
    this._count = count;
    notifyListeners();
  }

  void setLoadingMore(bool loadingMore) {
    this._loadingMore = loadingMore;
  }

  void notifyListenersOnly() {
    notifyListeners();
  }

  void setHasMore(bool hasMore) {
    this._hasMore = hasMore;
  }

  void setIsOpen(bool isOpen) {
    this._isOpen = isOpen;
    notifyListeners();
  }

  Future<Fave?> isFave(storeId) async {
    Fave? fave = await DBProvider.instance.getFaveByStoreId(storeId);
    return fave;
  }

  Future<void> toggleFave(storeId) async {
    Fave? fave = await DBProvider.instance.getFaveByStoreId(storeId);
    if (fave != null) {
      DBProvider.instance.deleteFave(fave.faveId);
    } else {
      fave = Fave.fromJson({"store_id": storeId, "fave_id": 0});
      DBProvider.instance.insertFave(fave);
    }
    notifyListeners();
  }

  void setMakers() {
    _markers = HashSet<Marker>();
    for (Store store in _dataHandler.stores!) {
      Marker marker = Marker(
          consumeTapEvents: false,
          markerId: MarkerId(store.storeId.toString()),
          position: LatLng(store.lat, store.lon),
          icon: store.mapIconDescriptor != null
              ? store.mapIconDescriptor
              : BitmapDescriptor.defaultMarker,
          infoWindow: InfoWindow(
            title: store.storeName,
            snippet: store.storeAddress,
          ));
      store.marker = marker;
      _markers.add(marker);
    }
    notifyListeners();
  }

  void setImageBytes(_imageBytes) {
    this._imageBytes = _imageBytes;
    // notifyListeners();
  }

  void setDataHandler(_dataHandler) {
    this._dataHandler = _dataHandler;
    // notifyListeners();
  }

  void setDataHandlerNotify(_dataHandler) {
    this._dataHandler = _dataHandler;
    notifyListeners();
  }

  void sortCategories(bool asc) {
    this._asc = asc;
    _dataHandler.categories!.sort((a, b) {
      if (!asc) {
        int val = a.category.toLowerCase().compareTo(b.category.toLowerCase());
        if (val < 0)
          return 1;
        else if (val > 0)
          return -1;
        else
          return 0;
      } else {
        return a.category.toLowerCase().compareTo(b.category.toLowerCase());
      }
    });
    notifyListeners();
  }

  void sortFeatured(bool isNearMe) {
    _dataHandler.stores!.sort((a, b) {
      if (isNearMe) {
        if (a.distance < b.distance)
          return -1;
        else if (a.distance > b.distance)
          return 1;
        else
          return 0;
      }
      return a.storeName.toLowerCase().compareTo(b.storeName.toLowerCase());
    });
    notifyListeners();
  }

  void updateDataHandler(Store store1, index, isFave) async {
    Fave? fave = await DBProvider.instance.getFaveByStoreId(store1.storeId);
    if (fave == null) {}

    store1.isFave = isFave;
    _dataHandler.stores!.removeAt(index);
    _dataHandler.stores!.insert(index, store1);
    notifyListeners();
  }

  bool getStoreIsFave(store) {
    Future<Fave?> fave = DBProvider.instance.getFaveByStoreId(store.storeId);
    return fave == null ? false : true;
  }
}
