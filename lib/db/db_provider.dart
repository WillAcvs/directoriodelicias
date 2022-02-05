import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'package:sprintf/sprintf.dart';
import 'package:sqflite/sqflite.dart';
import 'package:directorio_delicias/models/category.dart';
import 'package:directorio_delicias/models/fave.dart';
import 'package:directorio_delicias/models/news.dart';
import 'package:directorio_delicias/models/notification.dart';
import 'package:directorio_delicias/models/photo.dart';
import 'package:directorio_delicias/models/store.dart';
import 'package:directorio_delicias/models/user.dart';

class DBProvider {
  
  static Database _database;

  static final DBProvider _singleton = DBProvider._internal();
  DBProvider._internal();
  static DBProvider get instance => _singleton;

  // factory DBProvider() {
  //   return _singleton;
  // }

  Future<Database> get database async {
		if (_database == null) {
			_database = await initializeDatabase();
		}
		return _database;
	}

  Future<Database> initializeDatabase() async {
		Directory directory = await getApplicationDocumentsDirectory();
		String path = directory.path + 'store_finder_flutter6_15.db';
		var todosDatabase = await openDatabase(path, version: 1, onCreate: _createDb);
		return todosDatabase;
	}

  void _createDb(Database db, int newVersion) async {

    await db.execute("CREATE TABLE faves ("
          "fave_id INTEGER PRIMARY KEY AUTOINCREMENT,"
          "store_id TEXT"
          ")");

    await db.execute("CREATE TABLE notifications ("
          "notification_id INTEGER PRIMARY KEY AUTOINCREMENT,"
          "push_title TEXT,"
          "push_msg TEXT,"
          "created_at_notif TEXT"
          ")");

    await db.execute("CREATE TABLE logged_user ("
          "user_id INTEGER PRIMARY KEY,"
          "full_name TEXT,"
          "username TEXT,"
          "password TEXT,"
          "login_hash TEXT,"
          "facebook_id TEXT,"
          "twitter_id TEXT,"
          "apple_id TEXT,"
          "email TEXT,"
          "thumb_url TEXT,"
          "photo_url TEXT"
          ")");

    await db.execute("CREATE TABLE categories ("
          "category_id INTEGER PRIMARY KEY,"
          "category TEXT, "
          "category_icon TEXT, "
          "map_icon TEXT, "
          "created_at INTEGER, "
          "updated_at INTEGER, "
          "pid INTEGER, "
          "has_sub INTEGER, "
          "is_deleted INTEGER "
          ")");

    await db.execute("CREATE TABLE news ("
          "news_id INTEGER PRIMARY KEY,"
          "news_content TEXT, "
          "news_title TEXT, "
          "news_url TEXT, "
          "photo_url TEXT, "
          "created_at INTEGER, "
          "updated_at INTEGER, "
          "has_sub INTEGER, "
          "is_deleted INTEGER "
          ")");

    await db.execute("CREATE TABLE photos ("
          "photo_id TEXT PRIMARY KEY,"
          "photo_url TEXT, "
          "thumb_url TEXT, "
          "store_id TEXT, "
          "created_at TEXT, "
          "updated_at TEXT, "
          "is_deleted TEXT "
          ")");

    await db.execute("CREATE TABLE stores ("
          "store_id TEXT PRIMARY KEY,"
          "store_name TEXT, "
          "store_address TEXT, "
          "store_desc TEXT, "
          "lat TEXT, "
          "lon TEXT, "
          "sms_no TEXT, "
          "phone_no TEXT, "
          "email TEXT, "
          "website TEXT, "
          "category_id TEXT, "
          "mon_open TEXT, "
          "mon_close TEXT, "

          "tue_open TEXT, "
          "tue_close TEXT, "
          "wed_open TEXT, "
          "wed_close TEXT, "
          "thu_open TEXT, "
          "thu_close TEXT, "
          "fri_open TEXT, "
          "fri_close TEXT, "
          "sat_open TEXT, "
          "sat_close TEXT, "
          "sun_open TEXT, "
          "sun_close TEXT, "
          "created_at TEXT, "
          "updated_at TEXT, "
          "featured TEXT, "
          "is_deleted TEXT, "

          "rating_ave TEXT, "
          "distance TEXT, "
          "rating_count TEXT, "
          "rating_total TEXT, "
          "category TEXT, "
          "map_icon TEXT "
          ")");
  }

  insertPhoto(Photo obj) async {
    final db = await database;
    var res = await db.insert("photos", obj.toMap());
    return res;
  }

  insertCategory(Category obj) async {
    final db = await database;
    var res = await db.insert("categories", obj.toMap());
    return res;
  }

  insertStore(Store obj) async {
    final db = await database;
    var res = await db.insert("stores", obj.toMap());
    return res;
  }

  insertNotification(Notification obj) async {
    final db = await database;
    var res = await db.insert("notifications", obj.toMap());
    return res;
  }

  insertLoggedUser(User obj) async {
    final db = await database;
    var res = await db.insert("logged_user", obj.toMap());
    return res;
  }

  insertFave(Fave obj) async {
    final db = await database;
    var res = await db.insert("faves", obj.toMap());
    return res;
  }

  deleteLoggedUser() async{
    final db = await database;
    var res = await db.delete("logged_user", where: null, whereArgs: null);
    return res;
  }

  deleteStore(int id) async{
    final db = await database;
    var res = await db.delete("stores", where: "store_id = ?", whereArgs: [id]);
    return res;
  }

  deleteFave(int id) async{
    final db = await database;
    var res = await db.delete("faves", where: "fave_id = ?", whereArgs: [id]);
    return res;
  }

  deletePhoto(int id) async{
    final db = await database;
    var res = await db.delete("photos", where: "photo_id = ?", whereArgs: [id]);
    return res;
  }

  deleteStores(int id) async{
    final db = await database;
    var res = await db.delete("stores", where: "store_id = ?", whereArgs: [id]);
    return res;
  }

  deleteAllStores() async{
    final db = await database;
    var res = await db.delete("stores", where: null, whereArgs: []);
    return res;
  }

  deletePhotosByStoreId(int id) async{
    final db = await database;
    var res = await db.delete("photos", where: "store_id = ?", whereArgs: [id]);
    return res;
  }

  deleteCategory(int id) async{
    final db = await database;
    var res = await db.delete("categories", where: "category_id = ?", whereArgs: [id]);
    return res;
  }

  deleteAllCategories() async{
    final db = await database;
    var res = await db.delete("categories", where: null, whereArgs: null);
    return res;
  }
  
  deleteAllNotifications() async{
    final db = await database;
    var res = await db.delete("notifications", where: null, whereArgs: null);
    return res;
  }

  deleteAllNews() async{
    final db = await database;
    var res = await db.delete("news", where: null, whereArgs: null);
    return res;
  }

  Future<List<Fave>> getFaves() async {
    final db = await database;
    var res = await  db.rawQuery("SELECT * FROM faves ORDER BY item_id ASC ");
    List<Fave> objs = res.isNotEmpty ? res.map((c) => Fave.fromJson(c)).toList() : [];
    return objs;
  }

  Future<Fave> getFaveByFaveId(int id) async {
    final db = await database;
    String sql = sprintf("SELECT * FROM faves WHERE fave_id = %d", [id]);
    var res = await  db.rawQuery(sql);
    List<Fave> objs = res.isNotEmpty ? res.map((c) => Fave.fromJson(c)).toList() : [];
    return objs.isNotEmpty ? objs.first : null;
  }

  Future<Fave> getFaveByStoreId(int id) async {
    final db = await database;
    String sql = sprintf("SELECT * FROM faves WHERE store_id = %d", [id]);
    var res = await  db.rawQuery(sql);
    List<Fave> objs = res.isNotEmpty ? res.map((c) => Fave.fromJson(c)).toList() : [];
    return objs.isNotEmpty ? objs.first : null;
  }

  Future<List<Store>> getStoresFave() async {
    final db = await database;
    String sql = "SELECT * FROM stores JOIN faves ON stores.store_id = faves.store_id ORDER BY distance ASC";
    var res = await  db.rawQuery(sql);
    List<Store> objs = res.isNotEmpty ? res.map((c) => Store.fromJsonRaw(c)).toList() : [];

    for(Store store in objs) {
      store.photos = await getPhotosByStoreId(store.storeId);
    }
    
    print(objs.length);
    return objs;
  }

  Future<List<Notification>> getNotifications() async {
    final db = await database;
    String sql = "SELECT * FROM notifications ORDER BY created_at_notif ASC";
    var res = await  db.rawQuery(sql);
    List<Notification> objs = res.isNotEmpty ? res.map((c) => Notification.fromJsonRaw(c)).toList() : [];
    print(res.toString());
    return objs;
  }

  Future<List<Photo>> getPhotosByStoreId(int id) async {
    final db = await database;
    String sql = sprintf("SELECT * FROM photos WHERE store_id = %d", [id]);
    var res = await  db.rawQuery(sql);
    List<Photo> objs = res.isNotEmpty ? res.map((c) => Photo.fromJson(c)).toList() : [];
    return objs;
  }

  Future<User> getLoggedUser() async {
    final db = await database;
    String sql = "SELECT * FROM logged_user";
    var res = await  db.rawQuery(sql);
    List<User> objs = res.isNotEmpty ? res.map((c) => User.fromJsonRaw(c)).toList() : [];
    return objs.isNotEmpty ? objs.first : null;
  }

  Future<List<Category>> getCategories() async {
    final db = await database;
    String sql = "SELECT * FROM categories ORDER BY category ASC";
    var res = await  db.rawQuery(sql);
    List<Category> objs = res.isNotEmpty ? res.map((c) => Category.fromJsonRaw(c)).toList() : [];
    return objs;
  }

  Future<List<Category>> getCategoriesLeafNode() async {
    final db = await database;
    String sql = "SELECT t1.category_id, t1.category, t1.created_at, t1.updated_at, t1.is_deleted, t1.pid FROM categories AS t1 LEFT JOIN categories as t2 ON t1.category_id = t2.pid WHERE t2.category_id IS NULL AND t1.is_deleted = 0 AND t1.pid >= 0 ORDER BY t1.category ASC";
    var res = await  db.rawQuery(sql);
    List<Category> objs = res.isNotEmpty ? res.map((c) => Category.fromJsonRaw(c)).toList() : [];
    return objs;
  }

  Future<List<Category>> getCategoriesByPID(pid) async {
    final db = await database;
    String sql = sprintf("SELECT * FROM categories WHERE pid = %d ORDER BY category ASC", [pid]);
    var res = await  db.rawQuery(sql);
    List<Category> objs = res.isNotEmpty ? res.map((c) => Category.fromJsonRaw(c)).toList() : [];
    return objs;
  }
  
  Future<List<Store>> getFeatured() async {
    final db = await database;
    String sql = "SELECT * FROM stores WHERE featured = 1 ORDER BY distance ASC";
    var res = await  db.rawQuery(sql);
    List<Store> objs = res.isNotEmpty ? res.map((c) => Store.fromJsonRaw(c)).toList() : [];

    for(Store store in objs) {
      store.photos = await getPhotosByStoreId(store.storeId);
    }
    
    print(objs.length);
    return objs;
  }

  Future<List<Store>> getStoresNearby() async {
    final db = await database;
    String sql = "SELECT * FROM stores ORDER BY distance ASC";
    var res = await  db.rawQuery(sql);
    List<Store> objs = res.isNotEmpty ? res.map((c) => Store.fromJsonRaw(c)).toList() : [];

    for(Store store in objs) {
      store.photos = await getPhotosByStoreId(store.storeId);
    }
    
    print(objs.length);
    return objs;
  }

  Future<List<Store>> getTopRated() async {
    final db = await database;
    String sql = "SELECT * FROM stores ORDER BY rating_ave DESC";
    var res = await  db.rawQuery(sql);
    List<Store> objs = res.isNotEmpty ? res.map((c) => Store.fromJsonRaw(c)).toList() : [];

    for(Store store in objs) {
      store.photos = await getPhotosByStoreId(store.storeId);
    }
    
    print(objs.length);
    return objs;
  }

  Future<List<Store>> getStoresByCategoryId(id) async {
    final db = await database;
    String sql = sprintf("SELECT * FROM stores WHERE category_id = %d ORDER BY distance ASC", [id]);
    var res = await  db.rawQuery(sql);
    List<Store> objs = res.isNotEmpty ? res.map((c) => Store.fromJsonRaw(c)).toList() : [];

    for(Store store in objs) {
      store.photos = await getPhotosByStoreId(store.storeId);
    }
    
    print(objs.length);
    return objs;
  }

  Future<List<News>> getNews() async {
    final db = await database;
    String sql = "SELECT * FROM news ORDER BY create_at DESC";
    var res = await  db.rawQuery(sql);
    List<News> objs = res.isNotEmpty ? res.map((c) => News.fromJson(c)).toList() : [];    
    print(objs.length);
    return objs;
  }

  insertNews(News obj) async {
    final db = await database;
    var res = await db.insert("news", obj.toMap());
    return res;
  }

}