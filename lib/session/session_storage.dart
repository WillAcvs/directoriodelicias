import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/models/user.dart';

class SessionStorage {
  static SessionStorage? _instance;
  static SharedPreferences? _preferences;
  static const String UserKey = 'user';
  static const String AppLanguagesKey = 'languages';
  static const String DarkModeKey = 'darkmode';
  static const String PUSH = 'push';
  static const String LAT = "LAT";
  static const String LON = "LON";
  static const String RADIUS = "RADIUS";

  static Future<SessionStorage?> getInstance() async {
    if (_preferences == null) {
      _preferences = await SharedPreferences.getInstance();
    }
    if (_instance == null) {
      _instance = SessionStorage();
    }
    return _instance;
  }

  User get user {
    var userJson = _getFromDisk(UserKey);
    if (userJson == null) {
      return user;
    }
    return User.fromJson(json.decode(userJson));
  }

  set user(User userToSave) {
    saveStringToDisk(UserKey, json.encode(userToSave.toMap()));
  }

  dynamic _getFromDisk(String key) {
    var value = _preferences!.get(key);
    print('(TRACE) LocalStorageService:_getFromDisk. key: $key value: $value');
    return value;
  }

  void saveStringToDisk(String key, String content) {
    print(
        '(TRACE) LocalStorageService:_saveStringToDisk. key: $key value: $content');
    _preferences!.setString(key, content);
  }

  double get lat {
    var val = _getFromDisk(LAT);
    if (val == null) {
      return Config.DEFAULT_LAT;
    }
    return val;
  }

  set lat(double lat) {
    saveStringToDisk(LAT, lat.toString());
  }

  double get lon {
    var val = _getFromDisk(LON);
    if (val == null) {
      return Config.DEFAULT_LON;
    }
    return val;
  }

  set lon(double lon) {
    saveStringToDisk(LON, lon.toString());
  }

  bool get isDarkMode {
    var val = _getFromDisk(DarkModeKey);
    if (val == "0") {
      return false;
    }
    return true;
  }

  void setDarkMode(bool isDarkMode) {
    saveStringToDisk(DarkModeKey, isDarkMode ? "1" : "0");
  }

  bool get isPushEnabled {
    var val = _getFromDisk(PUSH);

    if (val == "0") {
      return false;
    }
    return true;
  }

  void setPushEnabled(bool isDarkMode) {
    saveStringToDisk(PUSH, isDarkMode ? "1" : "0");
  }

  double get getRadius {
    try {
      var radius = _getFromDisk(RADIUS);
      if (radius == null) return Config.DEFAULT_RADIUS;

      return double.parse(radius);
    } on Exception {}

    return Config.DEFAULT_RADIUS;
  }

  void setRadius(double radius) {
    saveStringToDisk(RADIUS, radius.toString());
  }
}
