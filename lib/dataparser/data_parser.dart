
import 'dart:convert';
import 'dart:typed_data';
import 'package:connectivity/connectivity.dart';
import 'package:get_it/get_it.dart';
// import 'package:get_it/get_it.dart';
import 'package:html_unescape/html_unescape.dart';
import 'package:http/http.dart' as http;
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/map_helpers.dart';
import 'package:directorio_delicias/commons/review_services.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/db/db_provider.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/models/category.dart';
import 'package:geolocator/geolocator.dart';
import 'package:directorio_delicias/models/news.dart';
import 'package:directorio_delicias/models/photo.dart';
import 'package:directorio_delicias/models/reviews.dart';
import 'package:directorio_delicias/models/status_code.dart';
import 'package:directorio_delicias/models/store.dart';
import 'package:directorio_delicias/models/user.dart';
import 'package:directorio_delicias/session/session_storage.dart';
import 'package:string_validator/string_validator.dart';

class DataParser {

  Position position;
  var unescape = new HtmlUnescape();
  
  Future<DataHandler> fetchCategories(pid) async {

    DataHandler dataHandler = new DataHandler();
    dataHandler.categories = List<Category>();
    
    if(pid == 0) {
      String strUrl = sprintf("%s?api_key=%s", 
          [Config.GET_CATEGORIES_JSON_URL, 
          Config.API_KEY]
      );
      print(strUrl);

      var connectivityResult = await (Connectivity().checkConnectivity());
      if (connectivityResult == ConnectivityResult.none) {
        dataHandler.categories = await DBProvider.instance.getCategories();
        return dataHandler;
      }
      try {
        var response = await http.get(strUrl);
        if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          var jsonData = dataJson['categories'];
          DBProvider.instance.deleteAllCategories();
          for (var objJson in jsonData) {
            Category cat = Category.fromJson(objJson);
            cat.category = unescape.convert(cat.category);
            DBProvider.instance.insertCategory(cat);
            if(cat.pid == 0) {
              dataHandler.categories.add(cat);
            }
          }
        }
      }
      catch(e) {
        print('(TRACE) = ' + e.toString());
      }
    }
    else {
      dataHandler.categories = await DBProvider.instance.getCategoriesByPID(pid);
    }
    return dataHandler;
  }

  Future<DataHandler> fetchHome() async {
    var connectivityResult = await (Connectivity().checkConnectivity());
    print(connectivityResult);
    if (connectivityResult == ConnectivityResult.none) {
      DataHandler dataHandler = new DataHandler();
      dataHandler.stores = await DBProvider.instance.getFeatured();
      dataHandler.topRated = await DBProvider.instance.getTopRated();
      dataHandler.nearbyStores = await DBProvider.instance.getStoresNearby();
      return dataHandler;
    }

    Position position = await MyApp.determinePosition();
    print(position);

    String strUrl = sprintf("%s?api_key=%s&lat=%f&lon=%f", 
      [Config.GET_HOME_JSON_URL, 
      Config.API_KEY, 
      position.latitude, 
      position.longitude]
    );
    print(strUrl);
    return getContent(strUrl, false);
  }

  Future<DataHandler> fetchFeatured() async {
    Position position = await MyApp.determinePosition();
    print(position);

    double radius = GetIt.instance.get<SessionStorage>().getRadius;
    String strUrl = sprintf("%s?api_key=%s&lat=%f&lon=%f&radius=%f", 
      [Config.GET_FEATURED_JSON_URL, 
      Config.API_KEY, 
      position.latitude, 
      position.longitude,
      radius]
    );
    print(strUrl);
  
    ConnectivityResult connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      DataHandler dataHandler = new DataHandler();
      dataHandler.stores = await DBProvider.instance.getFeatured();
      return dataHandler;
    }

    return getContent(strUrl, false);
  }

  List<Store> getStores(var jsonData) {
    var stores = List<Store>();
    for (var objJson in jsonData) {
      Store obj = Store.fromJson(objJson);

      if(isBase64(obj.storeDesc)) {
        Uint8List decoded = base64Decode(obj.storeDesc);
        String storeDesc = new String.fromCharCodes(decoded);
        obj.storeDesc = storeDesc.replaceAll("\n", "<br/>");
      }

      obj.storeDesc = unescape.convert(obj.storeDesc);
      obj.storeName = unescape.convert(obj.storeName);
      obj.storeAddress = unescape.convert(obj.storeAddress);

      DBProvider.instance.deletePhotosByStoreId(obj.storeId);
      obj.photos = new List<Photo>();
      var photosJSON = objJson['photos'];
      for (var photoJSON in photosJSON) {
        Photo objPhoto = Photo.fromJson(photoJSON);
        DBProvider.instance.insertPhoto(objPhoto);
        obj.photos.add(objPhoto);
      }

      DBProvider.instance.deleteStore(obj.storeId);
      DBProvider.instance.insertStore(obj);
      stores.add(obj);
    }
    return stores;
  }

  Future<DataHandler> getContent(strUrl, isMap) async {
    DataHandler dataHandler = new DataHandler();
    dataHandler.categories = List<Category>();
    dataHandler.news = List<News>();
    dataHandler.nearbyStores = List<Store>();
    dataHandler.stores = List<Store>();
    dataHandler.topRated = List<Store>();

    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult != ConnectivityResult.mobile && connectivityResult != ConnectivityResult.wifi) {
      return dataHandler;
    }
    try {
      var response = await http.get(strUrl);
      if (response.statusCode == 200) {
        var dataJson = json.decode(response.body);
        var jsonData = dataJson['categories'];
        if(jsonData != null) {
          DBProvider.instance.deleteAllCategories();
          for (var objJson in jsonData) {
            Category cat = Category.fromJson(objJson);
            cat.category = unescape.convert(cat.category);
            DBProvider.instance.insertCategory(cat);
            dataHandler.categories.add(cat);
          }
        }

        jsonData = dataJson['news'];
        if(jsonData != null) {
          for (var objJson in jsonData) {
            News obj = News.fromJson(objJson);
            obj.newsTitle = unescape.convert(obj.newsTitle);
            obj.newsContent = unescape.convert(obj.newsContent);
            dataHandler.news.add(obj);
          }
        }

        jsonData = dataJson['nearby_stores'];
        if(jsonData != null)
          dataHandler.nearbyStores = getStores(jsonData);

        jsonData = dataJson['stores'];
        if(jsonData != null) {
          dataHandler.stores = getStores(jsonData);

          if(isMap) {
            for(Store store in dataHandler.stores) {
              if(store.mapIcon.length > 0) {
                store.mapIconDescriptor = await MapHelpers.fetchMapIcon(store.mapIcon);
              }
            }
          }
        }

        jsonData = dataJson['top_rated'];
        if(jsonData != null)
          dataHandler.topRated = getStores(jsonData);
      }
    }
    catch(e) {
      print('(TRACE) = ' + e.toString());
    }
    return dataHandler;
  }

  Future<DataHandler>getSavedStores() async{
    DataHandler dataHandler = new DataHandler();

    List<Store> stores = await DBProvider.instance.getStoresFave();
    for(Store store in stores) {
      store.photos = await DBProvider.instance.getPhotosByStoreId(store.storeId);
    }
    dataHandler.stores = stores;
    return dataHandler;

  }

  Future<DataHandler> fetchStoresNearby() async {
    Position position = await MyApp.determinePosition();
    print(position);

    double radius = GetIt.instance.get<SessionStorage>().getRadius;
    String strUrl = sprintf("%s?api_key=%s&lat=%f&lon=%f&radius=%f", 
      [Config.GET_STORES_JSON_URL, 
      Config.API_KEY, 
      position.latitude, 
      position.longitude,
      radius]
    );
    print(strUrl);

    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      DataHandler dataHandler = new DataHandler();
      dataHandler.stores = await DBProvider.instance.getStoresNearby();
      return dataHandler;
    }
    
    return getContent(strUrl, false);
  }

  Future<DataHandler> fetchCategoryStoresNearby(Category category) async {

    ConnectivityResult connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      DataHandler dataHandler = new DataHandler();
      dataHandler.stores = await DBProvider.instance.getStoresByCategoryId(category.categoryId);
      return dataHandler;
    }

    Position position = await MyApp.determinePosition();
    print(position);

    double radius = GetIt.instance.get<SessionStorage>().getRadius;
    String strUrl = sprintf("%s?api_key=%s&lat=%f&lon=%f&radius=%f&category_id=%d", 
      [Config.GET_STORES_JSON_URL, 
      Config.API_KEY, 
      position.latitude, 
      position.longitude,
      radius,
      category.categoryId]
    );

    print(strUrl);
    return getContent(strUrl, false);
  }


  Future<Position> getPosition() async {
    Position position = await MyApp.determinePosition();
    return position;
  }

  Future<DataHandler> login(String username, String password) async {

    Map<String, String>params = {
      "username": username,
      "password": password,
      "api_key": Config.API_KEY,
    };
    print(params);
    
    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var response = await http.post(Config.LOGIN_URL, body: params);
        if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          print(dataJson);
          var jsonUser = dataJson['user'];
          if(jsonUser != null) {
            dataHandler.user = User.fromJson(jsonUser);
          }
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return dataHandler;
  }

  Future<DataHandler> register(username, password, email, fullName, facebookId, twitterId, appleId, thumbUrl, thumbFile) async {
    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var request = new http.MultipartRequest("POST", Uri.parse(Config.REGISTER_URL));
        request.fields['username'] = username;
        request.fields['password'] = password;
        request.fields['full_name'] = fullName;
        request.fields['facebook_id'] = facebookId;
        request.fields['twitter_id'] = twitterId;
        request.fields['apple_id'] = appleId;
        request.fields['api_key'] = Config.API_KEY;
        request.fields['thumb_url'] = thumbUrl;
        request.fields['email'] = email;
        print(request.fields);

        if(thumbFile != null)
          request.files.add(await http.MultipartFile.fromPath('thumb_file', thumbFile));
          
        var response = await request.send();
        
        // var response = await http.post(Config.REGISTER_URL, body: params);
        if (response.statusCode == 200) {
          var data = await response.stream.bytesToString();
          var dataJson = json.decode(data);
          print("stream =" + data);
          print(dataJson);
          var jsonUser = dataJson['user'];
          if(jsonUser != null) {
            dataHandler.user = User.fromJson(jsonUser);
          }
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return dataHandler;
  }

  Future<DataHandler> updateProfile(userId, password, fullName, thumbUrl, thumbFile) async {
    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var request = new http.MultipartRequest("POST", Uri.parse(Config.UPDATE_USER_PROFILE_URL));
        request.fields['user_id'] = userId.toString();
        request.fields['password'] = password;
        request.fields['full_name'] = fullName;
        request.fields['api_key'] = Config.API_KEY;
        request.fields['thumb_url'] = thumbUrl;
        print(request.fields);

        if(thumbFile != null)
          request.files.add(await http.MultipartFile.fromPath('thumb_file', thumbFile));

        var response = await request.send();
        // var response = await http.post(Config.REGISTER_URL, body: params);
        if (response.statusCode == 200) {
          var dataJson = json.decode(await response.stream.bytesToString());
          print("ok=$dataJson");
          var jsonUser = dataJson['user'];
          if(jsonUser != null) {
            dataHandler.user = User.fromJson(jsonUser);
          }
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return dataHandler;
  }

  Future<DataHandler> getRatingStore({int userId, String loginHash, int storeId}) async {

    Map<String, String>params = {
      "user_id": userId.toString(),
      "login_hash": loginHash,
      "store_id": storeId.toString(),
      "api_key": Config.API_KEY,
    };

    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var response = await http.post(Config.GET_RATING_USER_URL, body: params);
        if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          print(dataJson);
          var jsonRating = dataJson['store_rating'];
          if(jsonRating != null) {
            dataHandler.canRate = jsonRating['can_rate'];
            dataHandler.storeId = jsonRating['storeId'];
          }
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return dataHandler;
  }

  Future<DataHandler> postRating({int userId, String loginHash, int storeId, double rating}) async {

    Map<String, String>params = {
      "user_id": userId.toString(),
      "login_hash": loginHash,
      "store_id": storeId.toString(),
      "rating": rating.toString(),
      "api_key": Config.API_KEY,
    };

    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var response = await http.post(Config.POST_RATING_URL, body: params);
        if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          var jsonRating = dataJson['status'];
          if(jsonRating != null && jsonRating['status_code'] == "-1") {
            return dataHandler;
          }
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return null;
  }

  Future<DataHandler> getReviews({int min, int max, int storeId}) async {

    String strUrl = sprintf("%s?api_key=%s&min=%d&max=%d&store_id=%d", 
      [Config.GET_REVIEWS_JSON_URL, 
      Config.API_KEY, 
      min,
      max, 
      storeId]
    );
    
    DataHandler dataHandler = DataHandler();
    dataHandler.reviews = List<Review>();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var response = await http.get(strUrl);
        if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          
          var jsonData = dataJson['reviews'];
          print(dataJson);
          if(jsonData != null) {
            for (var objJson in jsonData) {
              Review review = Review.fromJson(objJson);
              dataHandler.reviews.add(review);
            }
          }
          dataHandler.min = int.parse(dataJson['min'].toString());
          dataHandler.max = int.parse(dataJson['max'].toString());
          dataHandler.totalRowCount = int.parse(dataJson['total_row_count'].toString());

          dataHandler.rowCount = (dataHandler.min + dataHandler.max);
          return dataHandler;
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return null;
  }

  Future<DataHandler> postReview({int userId, String loginHash, int storeId, String review}) async {

    Map<String, String> params = {
      "user_id": userId.toString(),
      "login_hash": loginHash,
      "store_id": storeId.toString(),
      "review": ReviewServices.reviewStr,
      "api_key": Config.API_KEY,
    };

    print(params);

    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var response = await http.post(Config.POST_REVIEW_URL, body: params);
        if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          var jsonRating = dataJson['status'];
          if(jsonRating != null && jsonRating['status_code'] == "-1") {
            return dataHandler;
          }
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return null;
  }

  Future<DataHandler> fetchMyStores({userId}) async {
    Position position = await MyApp.determinePosition();
    print(position);

    String strUrl = sprintf("%s?api_key=%s&lat=%f&lon=%f&user_id=%d", 
      [Config.GET_MY_STORES_JSON_URL, 
      Config.API_KEY, 
      position.latitude, 
      position.longitude,
      userId]
    );

    print(strUrl);
    return getContent(strUrl, false);
  }

  Future<DataHandler> insertStore({
    storeId, 
    storeName, 
    storeAddress, 
    storeDesc, 
    lat, 
    lon, 
    smsNo, 
    phoneNo, 
    email, 
    website, 
    categoryId, 
    monOpen, 
    monClose, 
    tueOpen, 
    tueClose, 
    wedOpen, 
    wedClose, 
    thuOpen, 
    thuClose, 
    friOpen, 
    friClose, 
    satOpen, 
    satClose, 
    sunOpen, 
    sunClose, 
    userId, 
    List<dynamic> photoPaths,
    List<int> photoIdsDeleted,
    loginHash
  }) async {
    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var request = new http.MultipartRequest("POST", Uri.parse(Config.POST_STORE_URL));
        request.fields['store_id'] = storeId;
        request.fields['store_name'] = storeName;
        request.fields['store_address'] = storeAddress;
        request.fields['store_desc'] = storeDesc;
        request.fields['lat'] = lat.toString();
        request.fields['lon'] = lon.toString();
        request.fields['sms_no'] = smsNo;
        request.fields['phone_no'] = phoneNo;
        request.fields['email'] = email;
        request.fields['website'] = website;
        request.fields['category_id'] = categoryId;
        request.fields['mon_open'] = monOpen;
        request.fields['mon_close'] = monClose;
        request.fields['tue_open'] = tueOpen;
        request.fields['tue_close'] = tueClose;
        request.fields['wed_open'] = wedOpen;
        request.fields['wed_close'] = wedClose;
        request.fields['thu_open'] = thuOpen;
        request.fields['thu_close'] = thuClose;
        request.fields['fri_open'] = friOpen;
        request.fields['fri_close'] = friClose;
        request.fields['sat_open'] = satOpen;
        request.fields['sat_close'] = satClose;
        request.fields['sun_open'] = sunOpen;
        request.fields['sun_close'] = sunClose;
        request.fields['login_hash'] = loginHash;
        request.fields['user_id'] = userId.toString();
        request.fields['api_key'] = Config.API_KEY;
        request.fields['max_photos_count'] = "0";
        
        int ind = 0;
        for(int x = 0; x < photoPaths.length; x++) {
          if( photoPaths[x].runtimeType == String ) {
            String strPath = sprintf("thumb_file_%d", [ind]);
            request.files.add(await http.MultipartFile.fromPath(strPath, photoPaths[x]));
            ind += 1;
          }
          request.fields['max_photos_count'] = ind.toString();
        }
        
        String ids = "";
        for(int x = 0; x < photoIdsDeleted.length; x++) {
          int photoId = photoIdsDeleted[x];
          ids += "" + photoId.toString();

          if(x < photoIdsDeleted.length - 1)
            ids += ",";
        }
        request.fields['photo_ids_deleted'] = ids;
        print(request.fields);

        var response = await request.send();
        if (response.statusCode == 200) {
          var dataJson = json.decode(await response.stream.bytesToString());
          print(dataJson);
          dataHandler.status = StatusCode.fromJson(dataJson['status']);
          return dataHandler;
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return dataHandler;
  }

  Future<DataHandler> fetchNews({int min, int max}) async {
    Position position = await MyApp.determinePosition();
    print(position);

    String strUrl = sprintf("%s?api_key=%s&min=%d&max=%d", 
      [Config.GET_NEWS_JSON_URL, 
      Config.API_KEY,
      min,
      max]
    );
    print(strUrl);

    DataHandler dataHandler = new DataHandler();
    dataHandler.news = List<News>();
  
    ConnectivityResult connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      DataHandler dataHandler = new DataHandler();
      dataHandler.news = await DBProvider.instance.getNews();
    }
    else {
      var response = await http.get(strUrl);
      if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          DBProvider.instance.deleteAllNews();
          var jsonData = dataJson['news'];
          for (var objJson in jsonData) {

            News news = News.fromJson(objJson);

            if(isBase64(news.newsContent)) {
              Uint8List decoded = base64Decode(news.newsContent);
              String desc = new String.fromCharCodes(decoded);
              news.newsContent = desc.replaceAll("\n", "<br/>");
            }

            news.newsContent = unescape.convert(news.newsContent);
            news.newsTitle = unescape.convert(news.newsTitle);

            DBProvider.instance.insertNews(news);
            dataHandler.news.add(news);
          }
      }
    }
    return dataHandler;
  }

  Future<DataHandler> searchStores({int min, int max, String searchStr}) async {
    Position position = await MyApp.determinePosition();
    print(position);

    Map<String, String>params = {
      "min": min.toString(),
      "max": max.toString(),
      "lat": position.latitude.toString(),
      "lon": position.longitude.toString(),
      "search_str": searchStr.toString(),
      "api_key": Config.API_KEY,
    };

    DataHandler dataHandler = new DataHandler();
    dataHandler.stores = List<Store>();

    ConnectivityResult connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {  
      dataHandler.stores = await DBProvider.instance.getStoresNearby();
    }
    else {
      var response = await http.post(Config.SEARCH_STORES_URL, body: params);
      if (response.statusCode == 200) {
        var dataJson = json.decode(response.body);
        print(dataJson);
        DBProvider.instance.deleteAllStores();
        var jsonData = dataJson['stores'];
        if(jsonData != null) {
          dataHandler.stores = getStores(jsonData);
        }
      }
    }
    return dataHandler;
  }

  Future<DataHandler> fetchStoresMap() async {
    Position position = await MyApp.determinePosition();
    print(position);

    double radius = GetIt.instance.get<SessionStorage>().getRadius;
    String strUrl = sprintf("%s?api_key=%s&lat=%f&lon=%f&radius=%f", 
      [Config.GET_STORES_JSON_URL, 
      Config.API_KEY, 
      position.latitude, 
      position.longitude,
      radius]
    );
    print(strUrl);

    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      DataHandler dataHandler = new DataHandler();
      dataHandler.stores = await DBProvider.instance.getStoresNearby();
      return dataHandler;
    }
  
    return getContent(strUrl, true);
  }

  Future<bool> postPlayerId({String playerId}) async {

    Map<String, String> params = {
      "player_id": playerId,
      "api_key": Config.API_KEY,
    };

    print(params);
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var response = await http.post(Config.POST_PLAYER_ID_URL, body: params);
        if (response.statusCode == 200) {
          var dataJson = json.decode(response.body);
          var jsonRating = dataJson['status'];
          if(jsonRating != null && jsonRating['status_code'] == "-1") {
            return true;
          }
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return false;
  }

  Future<DataHandler> deleteStore({
    storeId, 
    userId, 
    loginHash
  }) async {
    DataHandler dataHandler = DataHandler();
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi) {
      try {
        var request = new http.MultipartRequest("POST", Uri.parse(Config.DELETE_STORE_URL));
        request.fields['store_id'] = storeId;
        request.fields['login_hash'] = loginHash;
        request.fields['user_id'] = userId.toString();
        request.fields['api_key'] = Config.API_KEY;
        
        var response = await request.send();
        if (response.statusCode == 200) {
          var dataJson = json.decode(await response.stream.bytesToString());
          print(dataJson);
          dataHandler.status = StatusCode.fromJson(dataJson['status']);
          return dataHandler;
        }
      }
      catch(e) {
        print(e.toString());
      }
    }
    return dataHandler;
  }

}

