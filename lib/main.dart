import 'dart:async';
import 'dart:io';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:geolocator/geolocator.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:directorio_delicias/commons/app_builder.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/commons/services.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/db/db_provider.dart';
import 'package:directorio_delicias/models/user.dart';
import 'package:directorio_delicias/models/notification.dart' as notif;
import 'package:directorio_delicias/notifiers/theme_notifier.dart';
import 'package:directorio_delicias/session/session_storage.dart';
import 'package:directorio_delicias/themes/app_theme.dart';
import 'package:directorio_delicias/ui/screens/navigation_screen.dart';
import 'app/config.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:onesignal_flutter/onesignal_flutter.dart';

GetIt locator = GetIt.instance;
SessionStorage sessionStorage = SessionStorage();

Future setupLocator() async {
  sessionStorage = (await SessionStorage.getInstance())!;
  locator.registerSingleton(sessionStorage);
  locator.registerSingleton(Services());
  locator.registerSingleton(DataParser());
}

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await EasyLocalization.ensureInitialized();
  await SystemChrome.setPreferredOrientations(<DeviceOrientation>[
    DeviceOrientation.portraitUp,
    DeviceOrientation.portraitDown
  ]).then((_) => runApp(EasyLocalization(
        child: ChangeNotifierProvider<ThemeNotifier>(
            create: (context) => ThemeNotifier(), child: MyApp()),
        path: 'assets/langs',
        supportedLocales: Config.LOCALES_LIST,
        useOnlyLangCode: true,
      )));

  setupLocator();

  //Remove this method to stop OneSignal Debugging
  OneSignal.shared.setLogLevel(OSLogLevel.verbose, OSLogLevel.none);

  // OneSignal.shared.init(Config.ONESIGNAL_APP_ID, iOSSettings: {
  //   OSiOSSettings.autoPrompt: false,
  //   OSiOSSettings.inAppLaunchUrl: false
  // });
  // OneSignal.shared
  //     .setInFocusDisplayType(OSNotificationDisplayType.notification);

  OneSignal.shared.setAppId(Config.ONESIGNAL_APP_ID);

// The promptForPushNotificationsWithUserResponse function will show the iOS or Android push notification prompt. We recommend removing the following code and instead using an In-App Message to prompt for notification permission
  OneSignal.shared.promptUserForPushNotificationPermission().then((accepted) {
    print("Accepted permission: $accepted");
  });
  // The promptForPushNotificationsWithUserResponse function will show
  // the iOS push notification prompt. We recommend removing the following code and
  // instead using an In-App Message to prompt for notification permission
  await OneSignal.shared
      .promptUserForPushNotificationPermission(fallbackToSettings: true);
  OneSignal.shared.setNotificationWillShowInForegroundHandler(
      (OSNotificationReceivedEvent event) {
    // Will be called whenever a notification is received in foreground
    // Display Notification, pass null param for not displaying the notification
    event.complete(event.notification);
  });
  OneSignal.shared.setNotificationWillShowInForegroundHandler(
      (OSNotificationReceivedEvent notification) {
    // will be called whenever a notification is received
    print(notification.notification.additionalData);
    String formatDateTime = Helpers.formatDateTime('yyyy-MM-dd h:mm a');
    notif.Notification obj = notif.Notification(
        pushTitle:
            notification.notification.additionalData!['push_title'].toString(),
        pushMsg:
            notification.notification.additionalData!['push_msg'].toString(),
        createdAt: notification.notification.additionalData!['created_at_notif']
            .toString(),
        receiveAt: formatDateTime);
    DBProvider.instance.insertNotification(obj);
  });

  OneSignal.shared
      .setNotificationOpenedHandler((OSNotificationOpenedResult result) {
    // will be called whenever a notification is opened/button pressed.
  });

  OneSignal.shared
      .setPermissionObserver((OSPermissionStateChanges changes) async {
    // will be called whenever the permission changes
    // (ie. user taps Allow on the permission prompt in iOS)
  });

  OneSignal.shared
      .setSubscriptionObserver((OSSubscriptionStateChanges changes) {
    // will be called whenever the subscription changes
    //(ie. user gets registered with OneSignal and gets a user ID)
    checkPlayerId();
  });

  OneSignal.shared.setEmailSubscriptionObserver(
      (OSEmailSubscriptionStateChanges emailChanges) {
    // will be called whenever then user's email subscription changes
    // (ie. OneSignal.setEmail(email) is called and the user gets registered
  });
  checkPlayerId();
}

void checkPlayerId() async {
  var status = await OneSignal.shared.getDeviceState();

  var playerId = status?.userId;
  print("playerId = $playerId");
}

// ignore: must_be_immutable
class MyApp extends StatelessWidget {
  static User? loggedUser;
  static Locale? selectedLocale;
  static Timer? timer;
  bool isChecking = false;
  static Position? currentPosition;

  static Future<Position?> determinePosition() async {
    // if(currentPosition != null) return currentPosition;

    bool serviceEnabled;
    LocationPermission permission;

    // Test if location services are enabled.
    serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      // Location services are not enabled don't continue
      // accessing the position and request users of the
      // App to enable the location services.
      // return Future.error('Location services are disabled.');
      return Position(
          longitude: Config.DEFAULT_LAT,
          latitude: Config.DEFAULT_LON,
          timestamp: DateTime.now(),
          accuracy: 0.0,
          altitude: 0.0,
          heading: 0.0,
          speed: 0.0,
          speedAccuracy: 0.0);
    }

    permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.deniedForever) {
        // Permissions are denied forever, handle appropriately.
        // return Future.error('Location permissions are permanently denied, we cannot request permissions.');
        return Position(
            longitude: Config.DEFAULT_LAT,
            latitude: Config.DEFAULT_LON,
            timestamp: DateTime.now(),
            accuracy: 0.0,
            altitude: 0.0,
            heading: 0.0,
            speed: 0.0,
            speedAccuracy: 0.0);
      }

      if (permission == LocationPermission.denied) {
        // Permissions are denied, next time you could try
        // requesting permissions again (this is also where
        // Android's shouldShowRequestPermissionRationale
        // returned true. According to Android guidelines
        // your App should show an explanatory UI now.
        // return Future.error('Location permissions are denied');
        return Position(
            longitude: Config.DEFAULT_LAT,
            latitude: Config.DEFAULT_LON,
            timestamp: DateTime.now(),
            accuracy: 0.0,
            altitude: 0.0,
            heading: 0.0,
            speed: 0.0,
            speedAccuracy: 0.0);
      }
    }

    // When we reach here, permissions are granted and we can
    // continue accessing the position of the device.
    currentPosition = await Geolocator.getCurrentPosition();
    return currentPosition;
  }

  void syncPlayerId() async {
    if (isChecking) return;

    isChecking = true;
    var status = await OneSignal.shared.getDeviceState();
    if (status != null && status.userId != null) {
      var playerId = status.userId;
      var synced = await GetIt.instance
          .get<DataParser>()
          .postPlayerId(playerId: playerId);
      if (synced) {
        timer!.cancel();
        print("synced playerId = $playerId");
      }
    } else {
      isChecking = false;
    }
  }

  @override
  Widget build(BuildContext context) {
    // final windowLocale = ui.window.locale;
    // Locale locale;
    // try {
    //   final first = Config.LOCALES_LIST
    //       ?.firstWhere((l) => l?.languageCode == windowLocale?.languageCode);
    //   locale = first != null ? first : Locale('en', 'US');
    // } catch (e) {
    //   print(e);
    // }

    timer = Timer.periodic(Duration(seconds: 10), (Timer t) => syncPlayerId());

    SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle(
      statusBarColor: Colors.transparent,
      statusBarIconBrightness: Brightness.dark,
      statusBarBrightness:
          Platform.isAndroid ? Brightness.dark : Brightness.light,
      systemNavigationBarColor: Colors.white,
      systemNavigationBarDividerColor: Colors.grey,
      systemNavigationBarIconBrightness: Brightness.dark,
    ));

    return AppBuilder(builder: (context) {
      bool isDarkMode = sessionStorage.isDarkMode;
      Provider.of<ThemeNotifier>(context, listen: false)
          .updateDarkMode(isDarkMode);

      if (context.locale == null) context.locale = Locale('es', 'MX');

      selectedLocale = context.locale;
      ZoomDrawer.rtl = selectedLocale!.languageCode.toLowerCase() == "ar";

      return MaterialApp(
        title: tr("app_name"),
        debugShowCheckedModeBanner: false,
        theme: AppTheme.buildLightTheme(),
        darkTheme: AppTheme.buildDarkTheme(),
        themeMode: isDarkMode ? ThemeMode.dark : ThemeMode.light,
        home: ChangeNotifierProvider(
          create: (_) => MenuProvider(),
          child: NavigationScreen(),
        ),
        supportedLocales: EasyLocalization.of(context)!.supportedLocales,
        locale: context.locale,
        localizationsDelegates: [
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
          EasyLocalization.of(context)!.delegate,
        ],
      );
    });
  }
}

class MenuProvider extends ChangeNotifier {
  int _currentPage = 0;
  int get currentPage => _currentPage;
  bool _isLogged = false;
  bool get isLogged => _isLogged;

  void updateCurrentPage(int index) {
    if (index != currentPage) {
      _currentPage = index;
      notifyListeners();
    }
  }

  void setLogged(bool logged) {
    this._isLogged = logged;
    notifyListeners();
  }

  void forceReload() {
    notifyListeners();
  }
}
