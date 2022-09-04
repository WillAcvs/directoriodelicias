import 'dart:convert';

import 'package:crypto/crypto.dart';
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:random_string/random_string.dart';
import 'package:directorio_delicias/main.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:http/http.dart' as http;

class Services {
  void call(String number) => launch("tel:$number");
  void sendSms(String number) => launch("sms:$number");
  void sendEmail(String email) => launch("mailto:$email");

  void launchURL(String url) async {
    String newUrl = url;
    if (!url.contains("http")) newUrl = "http://" + url;

    if (await canLaunch(newUrl)) {
      await launch(newUrl);
    } else {
      throw 'Could not launch $newUrl';
    }
  }

  Future<Position?> getLocation() async {
    Position? position = await MyApp.determinePosition();
    return position;
  }

  LatLngBounds boundsFromLatLngList(List<LatLng> list) {
    assert(list.isEmpty);
    double? x0, x1, y0, y1;
    for (LatLng latLng in list) {
      if (x0 == null) {
        x0 = x1 = latLng.latitude;
        y0 = y1 = latLng.longitude;
      } else {
        if (latLng.latitude > x1!) x1 = latLng.latitude;
        if (latLng.latitude < x0) x0 = latLng.latitude;
        if (latLng.longitude > y1!) y1 = latLng.longitude;
        if (latLng.longitude < y0!) y0 = latLng.longitude;
      }
    }
    return LatLngBounds(
        northeast: LatLng(x1!, y1!), southwest: LatLng(x0!, y0!));
  }

  static String generateSignature(String method, String base,
      List<String> sortedItems, String secret, String key1) {
    String param = '';

    for (int i = 0; i < sortedItems.length; i++) {
      if (i == 0)
        param = sortedItems[i];
      else
        param += '&${sortedItems[i]}';
    }

    String sig =
        '$method&${Uri.encodeComponent(base)}&${Uri.encodeComponent(param)}';
    String key = '${Uri.encodeComponent(key1)}&${Uri.encodeComponent(secret)}';
    var digest = Hmac(sha1, utf8.encode(key)).convert(utf8.encode(sig));
    return base64.encode(digest.bytes);
  }

  Future<http.Response> _twitterGet(String base, List<List<String>> params,
      String _consumerKey, String token, String secret) async {
    String oauthConsumer =
        'oauth_consumer_key="${Uri.encodeComponent(_consumerKey)}"';
    String oauthToken = 'oauth_token="${Uri.encodeComponent(token)}"';
    String oauthNonce =
        'oauth_nonce="${Uri.encodeComponent(randomAlphaNumeric(42))}"';
    String oauthVersion = 'oauth_version="${Uri.encodeComponent("1.0")}"';
    String oauthTime =
        'oauth_timestamp="${(DateTime.now().millisecondsSinceEpoch / 1000).toString()}"';
    String oauthMethod =
        'oauth_signature_method="${Uri.encodeComponent("HMAC-SHA1")}"';
    var oauthList = [
      oauthConsumer.replaceAll('"', ""),
      oauthNonce.replaceAll('"', ""),
      oauthMethod.replaceAll('"', ""),
      oauthTime.replaceAll('"', ""),
      oauthToken.replaceAll('"', ""),
      oauthVersion.replaceAll('"', "")
    ];
    var paramMap = Map<String, String>();

    for (List<String> param in params) {
      oauthList.add(
          '${Uri.encodeComponent(param[0])}=${Uri.encodeComponent(param[1])}');
      paramMap[param[0]] = param[1];
    }

    oauthList.sort();

    String oauthSig =
        'oauth_signature="${Uri.encodeComponent(generateSignature("GET", "https://api.twitter.com$base", oauthList, secret, _consumerKey))}"';

    return await http
        .get(new Uri.https("api.twitter.com", base, paramMap), headers: {
      "Authorization":
          'Oauth $oauthConsumer, $oauthNonce, $oauthSig, $oauthMethod, $oauthTime, $oauthToken, $oauthVersion',
      "Content-Type": "application/x-www-form-urlencoded"
    }).timeout(Duration(seconds: 15));
  }

  Future<dynamic> getUser(
      String tag, String _consumerKey, String token, String secret) async {
    String base = '/1.1/users/show.json';
    final response = await _twitterGet(
        base,
        [
          ["screen_name", tag],
          ["tweet_mode", "extended"],
        ],
        _consumerKey,
        token,
        secret);

    if (response.statusCode == 200) {
      try {
        return json.decode(response.body);
      } catch (e) {
        print(e);
        return null;
      }
    } else {
      print("Error retrieving user");
      print(response.body);
      return null;
    }
  }
}

class HexColor extends Color {
  HexColor(final String hexColor) : super(_getColorFromHex(hexColor));
  static int _getColorFromHex(String hexColor) {
    hexColor = hexColor.toUpperCase().replaceAll('#', '');
    if (hexColor.length == 6) {
      hexColor = 'FF' + hexColor;
    }
    return int.parse(hexColor, radix: 16);
  }
}
