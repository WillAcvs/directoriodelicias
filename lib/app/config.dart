
import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:directorio_delicias/models/language.dart';

class Config {
  
  static const String BASE_URL = "https://directoriodelicias.website/admin/";

  // API KEY use to handshake the admin backend.
  // If you wish to change this, you need to change also the PHP Backend under applications > config.php
  // you can create your own key. Make sure it is the same in the PHP backend admin.
  static const String API_KEY = "45090dcae2aYMK";

  // One signal app id can be created here http://onesignal.com/
  static const String ONESIGNAL_APP_ID = "d3b5048a-c9b0-4ef9-a26e-449d6241e9af";

  // Maps api key can be created here http://console.developers.google.com/
  static const String MAPS_API_KEY = "AIzaSyBY93epPcFN30xftvkDg2T65MEa8DFVbSo";

  // Twitter consumer key and secret can be created here
  // http://developer.twitter.com/
  static const String TWITTER_CONSUMER_KEY = "GeUtp7OtnczFiR8vGoq84JImY";

  static const String TWITTER_CONSUMER_SECRET = "7VtKhn47TlQ4BXgcTslFgzMGc5JPmmDjgna5254m9TKNQHK84B";

  // Map box access token for static maps
  static const String MAP_BOX_ACCESS_TOKEN = "pk.eyJ1IjoibWd1c2VyMDAxIiwiYSI6ImNqeGE1cjl0NzAyd2gzeHBhcTM2YnVidm8ifQ.tyON58eWXU-2z0i3rUmEfQ";

  // To be used when sharing to Twitter/Facebook
  static const String WEBSITE_URL = "https://directoriodelicias.website/admin/";
  
  // DO NOT EDIT THIS
  static const String GET_HOME_JSON_URL = BASE_URL + "rest/get_home.php";

  // DO NOT EDIT THIS
  static const String GET_NEWS_JSON_URL = BASE_URL + "rest/get_news.php";

  // DO NOT EDIT THIS
  static const String GET_CATEGORIES_JSON_URL = BASE_URL + "rest/get_categories.php";

  // DO NOT EDIT THIS
  static const String GET_FEATURED_JSON_URL = BASE_URL + "rest/get_featured.php";

  // DO NOT EDIT THIS
  static const String GET_MY_STORES_JSON_URL = BASE_URL + "rest/get_my_stores.php";

  // DO NOT EDIT THIS
  static const String GET_STORES_JSON_URL = BASE_URL + "rest/get_stores.php";

  // DO NOT EDIT THIS
  static const String GET_FAVORITES_JSON_URL = BASE_URL + "rest/get_favorites.php";

  // DO NOT EDIT THIS
  static const String LOGIN_URL = BASE_URL + "rest/login.php";

  // DO NOT EDIT THIS
  static const String REGISTER_URL = BASE_URL + "rest/register.php";

  // DO NOT EDIT THIS
  static const String POST_STORE_URL = BASE_URL + "rest/post_store.php";

  // DO NOT EDIT THIS
  static const String UPDATE_USER_PROFILE_URL = BASE_URL + "rest/update_user_profile.php";

  // DO NOT EDIT THIS
  static const String GET_RATING_USER_URL = BASE_URL + "rest/get_rating_user.php";

  // DO NOT EDIT THIS
  static const String POST_RATING_URL = BASE_URL + "rest/post_rating.php";

  // DO NOT EDIT THIS
  static const String POST_REVIEW_URL = BASE_URL + "rest/post_review.php";

  // DO NOT EDIT THIS
  static const String DELETE_STORE_URL = BASE_URL + "rest/delete_store.php";

  // DO NOT EDIT THIS
  static const String POST_PLAYER_ID_URL = BASE_URL + "rest/post_player_id.php";

  // DO NOT EDIT THIS
  static const String GET_CATEGORY_ITEMS_JSON_URL = BASE_URL + "rest/get_category_items.php";

  // DO NOT EDIT THIS
  static const String GET_REVIEWS_JSON_URL = BASE_URL + "rest/get_reviews.php";

  // DO NOT EDIT THIS
  static const String SEARCH_STORES_URL = BASE_URL + "rest/search_stores.php";

  // Max default radius to fetch stores.
  static const double DEFAULT_RADIUS = 90000.0;

  // Search max stores count per fetch since it will ignore radius limitation.
  static const int MAX_STORES_COUNT_FETCH = 40;

  // Max news count per fetch
  static const int MAX_NEWS_COUNT_FETCH = 40;

  // Language to support in the app
  static const LOCALES_LIST = [
    Locale('es', 'MX'),
    Locale('en', 'US'),
   // Locale('fr', 'CA'),
   // Locale('ar', 'TN'),
   // Locale('sl', 'SI'),
  ];

  // Language definition found in settings screen.
  static final List<Language> languageLocales = [
    Language(
      name: "Spanish", locale: 
      Locale('es', 'MX')),
    Language(
      name: "English", locale: 
      Locale('en', 'US')),
    /*Language(
      name: "French", 
      locale: Locale('fr', 'CA')),
    Language(
      name: "Arabic", 
      locale: Locale('ar', 'TN')),
    Language(
      name: "Slovenian", 
      locale: Locale('sl', 'SI')),*/
  ];

  // Default latitude shown for adding stores.
  static const double DEFAULT_LAT = 28.193329549875017;

  // Default longitude shown for adding stores.
  static const double DEFAULT_LON = -105.47163442843384;

  // Max review count per fetch
  static const int MAX_REVIEW_COUNT = 10;

  static const int MAX_PHOTO_UPLOAD = 5;

  static const String CONTACT_EMAIL = "hola@directoriodelicias.website";

  // DO NOT EDIT THIS
  static const String DEFAULT_EMPTY_PHOTO_PLACEHOLDER = "assets/images/placeholder.jpg";

  // DO NOT EDIT THIS
  static const String DEFAULT_EMPTY_PHOTO_PLACEHOLDER_DARK = "assets/images/placeholder_dark.jpg";

  // DO NOT EDIT THIS
  static const String DEFAULT_THUMB_PHOTO = "assets/images/image_placeholder.jpg";

  // DO NOT EDIT THIS
  static const String DEFAULT_THUMB_PHOTO_DARK = "assets/images/image_placeholder_dark.jpg";

  // DO NOT EDIT THIS
  static const String DEFAULT_MAP_PLACEHOLDER = "assets/images/map_placeholder.jpg";

  // DO NOT EDIT THIS
  static const String DEFAULT_MAP_DARK_PLACEHOLDER = "assets/images/map_placeholder_dark.jpg";

  // DO NOT EDIT THIS
  static const STATIC_GOOGLE_MAPS_URL = "https://api.mapbox.com/styles/v1/mapbox/light-v10/static/pin-l-embassy+f74e4e(%s,%s)/%s,%s,16/600x300?access_token=" + Config.MAP_BOX_ACCESS_TOKEN;
  
  // DO NOT EDIT THIS
  static const STATIC_DARK_GOOGLE_MAPS_URL = "https://api.mapbox.com/styles/v1/mapbox/dark-v10/static/pin-l-embassy+f74e4e(%s,%s)/%s,%s,16/600x300?access_token=" + Config.MAP_BOX_ACCESS_TOKEN;
}
