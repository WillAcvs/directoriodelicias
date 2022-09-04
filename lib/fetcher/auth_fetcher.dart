import 'package:flutter_login_facebook/flutter_login_facebook.dart';

import 'package:get_it/get_it.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/dataparser/data_parser.dart';
import 'package:directorio_delicias/db/db_provider.dart';
import 'package:directorio_delicias/main.dart';
import 'package:twitter_login/twitter_login.dart';

class AuthFetcher {
  Function(DataHandler) didLogged;
  Function(DataHandler) didError;
  AuthFetcher({required this.didLogged, required this.didError});

  Future<void> loginTwitter() async {
    final twitterLogin = TwitterLogin(
      redirectURI: "",
      apiKey: Config.TWITTER_CONSUMER_KEY,
      apiSecretKey: Config.TWITTER_CONSUMER_SECRET,
    );

    final authResult = await twitterLogin.login();
    if (authResult.status == TwitterLoginStatus.loggedIn) {
      String profileImage =
          sprintf("https://twivatar.glitch.me/%s", [authResult.user?.name]);
      DataHandler dataHandler = await GetIt.instance.get<DataParser>().register(
          "",
          "",
          "",
          authResult.user?.screenName,
          "",
          authResult.user?.id,
          "",
          profileImage,
          null);

      if (dataHandler.user != null) {
        DBProvider.instance.deleteLoggedUser();
        DBProvider.instance.insertLoggedUser(dataHandler.user!);
        MyApp.loggedUser = dataHandler.user;
        if (this.didLogged != null) this.didLogged(dataHandler);
      } else {
        if (this.didError != null) this.didError(dataHandler);
      }
    }
  }

  void loginFacebook() async {
    final fb = FacebookLogin();
    final res = await fb.logIn(permissions: [
      FacebookPermission.publicProfile,
      FacebookPermission.email,
    ]);

    // Check result status
    switch (res.status) {
      case FacebookLoginStatus.success:
        // Logged in
        // Send access token to server for validation and auth
        final FacebookAccessToken? accessToken = res.accessToken;
        print(accessToken);
        // Get profile data
        final profile = await fb.getUserProfile();
        print(profile);
        // Get user profile image url
        final imageUrl = await fb.getProfileImageUrl(width: 200);
        // Get email (since we request email permission)
        final email = await fb.getUserEmail();
        // But user can decline permission

        DataHandler dataHandler = await GetIt.instance
            .get<DataParser>()
            .register("", "", email == null ? "" : email, profile?.name,
                profile?.userId, "", "", imageUrl, null);

        if (dataHandler.user != null) {
          DBProvider.instance.deleteLoggedUser();
          DBProvider.instance.insertLoggedUser(dataHandler.user!);
          MyApp.loggedUser = dataHandler.user;
          if (this.didLogged != null) this.didLogged(dataHandler);
        } else {
          if (this.didError != null) this.didError(dataHandler);
        }
        break;
      case FacebookLoginStatus.cancel:
        // User cancel log in
        break;
      case FacebookLoginStatus.error:
        // Log in failed
        print('Error while log in: ${res.error}');
        break;
      case FacebookLoginStatus.success:
        // TODO: Handle this case.
        break;
      case FacebookLoginStatus.cancel:
        // TODO: Handle this case.
        break;
      case FacebookLoginStatus.error:
        // TODO: Handle this case.
        break;
    }
  }

  void loginUser({username, password}) async {
    DataHandler dataHandler =
        await GetIt.instance.get<DataParser>().login(username, password);
    if (dataHandler.user != null) {
      DBProvider.instance.insertLoggedUser(dataHandler.user!);
      MyApp.loggedUser = dataHandler.user;
      if (this.didLogged != null) this.didLogged(dataHandler);
    } else {
      if (this.didLogged != null) this.didError(dataHandler);
    }
  }

  void register({username, password, email, fullName, photoFile}) async {
    DataHandler dataHandler = await GetIt.instance.get<DataParser>().register(
        username, password, email, fullName, "", "", "", "", photoFile);

    if (dataHandler.user != null) {
      DBProvider.instance.insertLoggedUser(dataHandler.user!);
      MyApp.loggedUser = dataHandler.user;
      if (this.didLogged != null) this.didLogged(dataHandler);
    } else {
      if (this.didLogged != null) this.didError(dataHandler);
    }
  }

  void updateProfile({userId, password, fullName, thumbUrl, photoFile}) async {
    DataHandler dataHandler = await GetIt.instance
        .get<DataParser>()
        .updateProfile(userId, password, fullName, thumbUrl, photoFile);

    if (dataHandler.user != null) {
      DBProvider.instance.deleteLoggedUser();
      DBProvider.instance.insertLoggedUser(dataHandler.user!);
      MyApp.loggedUser = dataHandler.user;
      if (this.didLogged != null) this.didLogged(dataHandler);
    } else {
      if (this.didLogged != null) this.didError(dataHandler);
    }
  }

  void logoutUser() {
    FacebookLogin fbController = FacebookLogin(debug: true);
    fbController.logOut();
    DBProvider.instance.deleteLoggedUser();
    MyApp.loggedUser = null;
    DataHandler dataHandler = new DataHandler();
    dataHandler.isLoggedOut = true;
    if (this.didLogged != null) this.didLogged(dataHandler);
  }
}
