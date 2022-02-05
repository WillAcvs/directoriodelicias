
import 'dart:async';

import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/drawer.dart';
import 'package:provider/provider.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/db/db_provider.dart';
import 'package:directorio_delicias/ui/screens/aboutus_screen.dart';
import 'package:directorio_delicias/ui/screens/category_screen.dart';
import 'package:directorio_delicias/ui/screens/fave_screen.dart';
import 'package:directorio_delicias/ui/screens/featured_screen.dart';
import 'package:directorio_delicias/ui/screens/login_screen.dart';
import 'package:directorio_delicias/ui/screens/my_stores_screen.dart';
import 'package:directorio_delicias/ui/screens/news_screen.dart';
import 'package:directorio_delicias/ui/screens/profile_screen.dart';
import 'package:directorio_delicias/ui/screens/settings_screen.dart';
import 'package:directorio_delicias/ui/screens/side_menu_screen.dart';
import 'package:directorio_delicias/ui/screens/terms_screen.dart';
import '../../main.dart';
import 'home_screen.dart';
import 'map_screen.dart';


class NavigationScreen extends StatefulWidget {
  @override
  _NavigationScreenState createState() => _NavigationScreenState();
}

class _NavigationScreenState extends State<NavigationScreen> {

  static MenuIndex drawerIndex;
  var selectedMenuItemId;
  ZoomDrawerController _drawerController = ZoomDrawerController();
  List<DrawerEntry> sideMenus;

  @override
  void initState() {
    drawerIndex = MenuIndex.HOME;
    super.initState();
  }

  Future<bool> getData() async {
    MyApp.loggedUser =  await DBProvider.instance.getLoggedUser();
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<bool>(
      future: getData(),
      builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
        if (!snapshot.hasData) {
          return Center();
        } else {
          return loadMain();
        }
      },
    );
  }

  Widget loadMain() {
    return Container(
      color: Theme.of(context).backgroundColor,
      child: Scaffold(
        backgroundColor: Theme.of(context).backgroundColor,
        body: ZoomDrawer(
          controller: _drawerController,
          menuScreen: Selector<MenuProvider, bool>(
            selector: (_, provider) => provider.isLogged,
            builder: (_, isLogged, __) {
              return sideMenuScreen();
            }
          ),
          mainScreen: screens(),
          borderRadius: 24.0,
          showShadow: true,
          angle: 0.0,
          backgroundColor: Colors.grey[300],
          slideWidth: MediaQuery.of(context).size.width * 0.65,
          openCurve: Curves.fastOutSlowIn,
          closeCurve: Curves.fastOutSlowIn,
        ),
      ),
    );
  }

  Widget sideMenuScreen() {
    sideMenus = <DrawerEntry>[
      DrawerEntry(
        index: MenuIndex.HOME,
        labelName: tr("home"),
        iconData: Icons.home,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.CATEGORIES,
        labelName: tr("categories"),
        iconData: Icons.list,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.FEATURED,
        labelName: tr("featured"),
        iconData: Icons.fact_check_outlined,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.FAVE,
        labelName: tr("favorites"),
        iconData: Icons.favorite_outline_sharp,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.MAP,
        labelName: tr("map"),
        iconData: Icons.map_outlined,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.NEWS,
        labelName: tr("news"),
        iconData: Icons.text_snippet,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.TERMS,
        labelName: tr("terms"),
        iconData: Icons.featured_play_list_outlined,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.ABOUT,
        labelName: tr("about_us"),
        iconData: Icons.info_outline,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.LOGIN,
        labelName: tr("account"),
        iconData: Icons.person,
        isFullScreen: true
      ),
      DrawerEntry(
        index: MenuIndex.SETTINGS,
        labelName: tr("settings"),
        iconData: Icons.settings,
        isFullScreen: false
      ),
      DrawerEntry(
        index: MenuIndex.MY_STORES,
        labelName: tr("my_stores"),
        iconData: Icons.storefront,
        isFullScreen: false
      ),
      
    ];
    return Container(
      child: Center(
        child: SideMenuScreen(
          sideMenus: sideMenus,
          onCallbackFullScreen: (drawerEntry) async {
            if (drawerEntry.index == MenuIndex.LOGIN) {
              if(MyApp.loggedUser != null) {
                _drawerController.close();
                Timer(Duration(milliseconds: 700), () async {
                  final DataHandler dataHandler = await Navigator.push<dynamic>(
                    context,
                    MaterialPageRoute<dynamic>(
                      builder: (BuildContext context) => ProfileScreen(),
                      fullscreenDialog: true
                    ),
                  );
                  if(dataHandler != null && !dataHandler.isLogged) {
                    Provider.of<MenuProvider>(context, listen: false).setLogged(false);
                  }
                });
              }
              else {
                _drawerController.close();
                Timer(Duration(milliseconds: 700), () async {
                  final DataHandler dataHandler = await Navigator.push<dynamic>(
                    context,
                    MaterialPageRoute<dynamic>(
                      builder: (BuildContext context) => LoginScreen(),
                      fullscreenDialog: true
                    ),
                  );
                  if(dataHandler != null && dataHandler.isLogged) {
                    Provider.of<MenuProvider>(context, listen: false).setLogged(true);
                  }
                });
              }
            } 
          },
        ),
      ),
    );
  }

  Widget screens() {
    return Selector<MenuProvider, int>(
      selector: (_, provider) => provider.currentPage,
      builder: (_, index, __) {
        _drawerController.close();
        DrawerEntry drawerEntry = sideMenus[index];
        drawerIndex = drawerEntry.index; 
        if(drawerIndex == MenuIndex.FEATURED) {
          return const FeaturePage();
        }
        else if (drawerIndex == MenuIndex.CATEGORIES) {
          return const CategoryPage();
        } 
        else if (drawerIndex == MenuIndex.MAP) {
          return const MapPage();
        } 
        else if (drawerIndex == MenuIndex.FAVE) {
          return const FavePage();
        } 
        else if (drawerIndex == MenuIndex.TERMS) {
          return const TermsPage();
        } 
        else if (drawerIndex == MenuIndex.ABOUT) {
          return const AboutUsPage();
        } 
        else if (drawerIndex == MenuIndex.MY_STORES) {
          return const MyStoresPage();
        } 
        else if (drawerIndex == MenuIndex.NEWS) {
          return NewsPage();
        } 
        else if (drawerIndex == MenuIndex.SETTINGS) {
          return SettingsScreen();
        } 
        return MyHomePage();
      }
    );
  }

}
