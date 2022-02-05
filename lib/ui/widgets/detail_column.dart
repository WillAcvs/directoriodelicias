
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:social_share_plugin/social_share_plugin.dart';
import 'package:sprintf/sprintf.dart';
import 'package:directorio_delicias/app/config.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/commons/services.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/models/store.dart';

import 'icon_tile.dart';

class DetailColumn extends StatelessWidget {
  final Store store;
  
  const DetailColumn({Key key, this.store}) : super(key: key);


  @override
  Widget build(BuildContext context) {

    Services _service = locator<Services>();

    return Column(
      children: <Widget>[
        
        
        
        InkWell(
          child: IconTile(
            width: 60,
            height: 60,
            iconData: Icons.call,
            backgroundColor: Color(0xFF8e43ac),
            iconColor: Theme.of(context).floatingActionButtonTheme.foregroundColor,
          ),
          onTap: () {
            if(store.phoneNo == null || store.phoneNo.isEmpty) {
              Helpers.showAlertDialog(
                context: context,
                title: tr("action_error"),
                message: tr("error_call_details"),
                onTapOk: () {

                }
              );
              return;
            }
            _service.call(store.phoneNo.replaceAll(" ", ""));
            print(store.phoneNo);
          },
        ),
        SizedBox(height: 20),
        
        
        InkWell(
          child: IconTile(
          width: 60,
          height: 60,
          iconData: Icons.alt_route_rounded,
          backgroundColor: Color(0xFFd22d2d),
          iconColor: Theme.of(context).floatingActionButtonTheme.foregroundColor,
        ),
          onTap: () {
            _service.getLocation().then((value) {
              String urlStr = sprintf("http://maps.google.com/maps?daddr=%f,%f&saddr=%f,%f", 
                                    [store.lat,
                                    store.lon,
                                    value.latitude,
                                    value.longitude]);
              _service.launchURL(urlStr);
              print(urlStr);
            });                
          },
        ),
        SizedBox(height:50),
      ],
    );
  }
}