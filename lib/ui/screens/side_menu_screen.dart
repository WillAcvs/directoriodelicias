
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/themes/app_theme.dart';


class SideMenuScreen extends StatefulWidget {

  SideMenuScreen({Key key, this.onCallbackFullScreen, this.sideMenus}) : super(key: key);

  final Function(DrawerEntry) onCallbackFullScreen;
  final List<DrawerEntry> sideMenus;

  @override
  _SideMenuScreenState createState() => _SideMenuScreenState();
}

class _SideMenuScreenState extends State<SideMenuScreen> {
  
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: AppTheme.themeBackgroundColor,
      child: Center(
        child: Container(
          height: 700,
          child: ListView.builder(
            padding: EdgeInsets.all(0),
            scrollDirection: Axis.vertical,
            itemCount: MyApp.loggedUser != null ? widget.sideMenus.length : widget.sideMenus.length - 1,
            itemBuilder: (context, index) {
              return Material(
                color: Colors.transparent,
                child: InkWell(
                  splashColor: Theme.of(context).accentColor,
                  child: Container(
                    padding: EdgeInsets.only(left: 20, top: 10, right: 10, bottom: 10),
                    height: 65,
                    alignment: Alignment.centerLeft,
                    child: Row(
                      children: [
                        Icon(
                          widget.sideMenus[index].iconData,
                          color: Colors.white,
                        ),
                        SizedBox(width: 10),
                        Text(
                          widget.sideMenus[index].labelName,
                          textAlign: TextAlign.start,
                          style: TextStyle(
                            fontSize: 20,
                            fontWeight: FontWeight.w400,
                            color: Colors.white
                          ),
                        )
                      ],
                    ),
                  ),
                  onTap: () {
                    DrawerEntry drawerEntry = widget.sideMenus[index];
                    if(drawerEntry.isFullScreen) {
                      widget.onCallbackFullScreen(drawerEntry);
                      print(drawerEntry.index);
                    }
                    else {
                      MenuProvider provider = Provider.of<MenuProvider>(context, listen: false);
                      if(provider.currentPage == index) 
                        return;
                        
                      provider.updateCurrentPage(index);
                    }
                  },
                ),
              );
            },
          ),
        ),
      ),
    );
  }

}

enum MenuIndex {
  HOME,
  FEATURED,
  FAVE,
  MAP,
  LOGIN,
  REGISTER,
  PROFILE,
  TERMS,
  SHARE,
  ABOUT,
  CATEGORIES,
  MY_STORES,
  NEWS,
  SETTINGS
}

class DrawerEntry {
  DrawerEntry({
    this.isAssetsImage = false,
    this.labelName = '',
    this.iconData,
    this.index,
    this.imageName = '',
    this.isFullScreen = false
  });

  String labelName;
  IconData iconData;
  bool isAssetsImage;
  String imageName;
  MenuIndex index;
  bool isFullScreen;
}

