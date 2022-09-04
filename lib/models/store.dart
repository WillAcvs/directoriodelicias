import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:directorio_delicias/models/photo.dart';

class Store {
  Store(
      {required this.storeId,
      required this.storeName,
      required this.storeAddress,
      required this.storeDesc,
      required this.lat,
      required this.lon,
      required this.smsNo,
      required this.phoneNo,
      required this.website,
      required this.categoryId,
      required this.ratingTotal,
      required this.ratingCount,
      required this.ratingAve,
      required this.distance,
      required this.email,
      required this.createdAt,
      required this.updatedAt,
      required this.isDeleted,
      this.photos,
      required this.monOpen,
      required this.monClose,
      required this.tueOpen,
      required this.tueClose,
      required this.wedOpen,
      required this.wedClose,
      required this.thuOpen,
      required this.thuClose,
      required this.friOpen,
      required this.friClose,
      required this.satOpen,
      required this.satClose,
      required this.sunOpen,
      required this.sunClose,
      this.isFave = false,
      required this.featured,
      this.marker,
      required this.category,
      required this.mapIcon,
      this.mapIconDescriptor = BitmapDescriptor.defaultMarker});

  bool isFave;
  int storeId;
  String storeName;
  String storeAddress;
  String storeDesc;
  double lat;
  double lon;
  String smsNo;
  String phoneNo;
  String website;
  int categoryId;
  int ratingTotal;
  int ratingCount;
  double ratingAve;
  double distance;
  int createdAt;
  int updatedAt;
  int isDeleted;
  List<Photo>? photos;

  String monOpen;
  String monClose;

  String tueOpen;
  String tueClose;

  String wedOpen;
  String wedClose;

  String thuOpen;
  String thuClose;

  String friOpen;
  String friClose;

  String satOpen;
  String satClose;

  String sunOpen;
  String sunClose;

  String email;
  int featured;
  Marker? marker;

  String category;
  String mapIcon;
  dynamic mapIconDescriptor;

  factory Store.fromJson(Map<String, dynamic> json) {
    return new Store(
      storeId: int.parse(json['store_id']),
      storeName: json['store_name'],
      storeAddress: json['store_address'],
      storeDesc: json['store_desc'],
      lat: double.parse(json['lat']),
      lon: double.parse(json['lon']),
      smsNo: json['sms_no'],
      phoneNo: json['phone_no'],
      website: json['website'],
      categoryId: int.parse(json['category_id']),
      ratingTotal: int.parse(json['rating_total']),
      ratingCount: int.parse(json['rating_count']),
      ratingAve: double.parse(json['rating_ave']),
      distance: double.parse(json['distance']),
      updatedAt: int.parse(json['updated_at']),
      isDeleted: int.parse(json['is_deleted']),
      createdAt: int.parse(json['created_at']),
      featured: int.parse(json['featured']),
      email: json['email'],
      monOpen: json['mon_open'],
      monClose: json['mon_close'],
      tueOpen: json['tue_open'],
      tueClose: json['tue_close'],
      wedOpen: json['wed_open'],
      wedClose: json['wed_close'],
      thuOpen: json['thu_open'],
      thuClose: json['thu_close'],
      friOpen: json['fri_open'],
      friClose: json['fri_close'],
      satOpen: json['sat_open'],
      satClose: json['sat_close'],
      sunOpen: json['sun_open'],
      sunClose: json['sun_close'],
      category: json['category'],
      mapIcon: json['map_icon'],
      mapIconDescriptor: null,
    );
  }

  Map<String, dynamic> toMap() => {
        "store_id": storeId,
        "store_name": storeName,
        "store_address": storeAddress,
        "store_desc": storeDesc,
        "lat": lat,
        "lon": lon,
        "sms_no": smsNo,
        "phone_no": phoneNo,
        "website": website,
        "category_id": categoryId,
        "rating_total": ratingTotal,
        "rating_count": ratingCount,
        "rating_ave": ratingAve,
        "distance": distance,
        "updated_at": updatedAt,
        "is_deleted": isDeleted,
        "created_at": createdAt,
        "email": email,
        "mon_open": monOpen,
        "mon_close": monClose,
        "tue_open": tueOpen,
        "tue_close": tueClose,
        "wed_open": wedOpen,
        "wed_close": wedClose,
        "thu_open": thuOpen,
        "thu_close": thuClose,
        "fri_open": friOpen,
        "fri_close": friClose,
        "sat_open": satOpen,
        "sat_close": satClose,
        "sun_open": sunOpen,
        "sun_close": sunClose,
        "featured": featured,
        "category": category,
        "map_icon": mapIcon
      };

  factory Store.fromJsonRaw(Map<String, dynamic> json) {
    return new Store(
      storeId: int.parse(json['store_id']),
      storeName: json['store_name'],
      storeAddress: json['store_address'],
      storeDesc: json['store_desc'],
      lat: double.parse(json['lat']),
      lon: double.parse(json['lon']),
      smsNo: json['sms_no'],
      phoneNo: json['phone_no'],
      website: json['website'],
      categoryId: int.parse(json['category_id']),
      ratingTotal: int.parse(json['rating_total']),
      ratingCount: int.parse(json['rating_count']),
      ratingAve: double.parse(json['rating_ave']),
      distance: double.parse(json['distance']),
      updatedAt: int.parse(json['updated_at']),
      isDeleted: int.parse(json['is_deleted']),
      createdAt: int.parse(json['created_at']),
      featured: int.parse(json['featured']),
      email: json['email'],
      monOpen: json['mon_open'],
      monClose: json['mon_close'],
      tueOpen: json['tue_open'],
      tueClose: json['tue_close'],
      wedOpen: json['wed_open'],
      wedClose: json['wed_close'],
      thuOpen: json['thu_open'],
      thuClose: json['thu_close'],
      friOpen: json['fri_open'],
      friClose: json['fri_close'],
      satOpen: json['sat_open'],
      satClose: json['sat_close'],
      sunOpen: json['sun_open'],
      sunClose: json['sun_close'],
      category: json['category'],
      mapIcon: json['map_icon'],
    );
  }
}
