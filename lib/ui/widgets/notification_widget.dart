
import 'package:flutter/material.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/models/notification.dart' as notif;

class NotificationView extends StatelessWidget {
  const NotificationView(
      {Key key,
      this.notification,
      this.callback})
      : super(key: key);

  final VoidCallback callback;
  final notif.Notification notification;

  @override
  Widget build(BuildContext context) {
    
    
    return Padding(
      padding: const EdgeInsets.only(
          left: 24, right: 24, top: 0, bottom: 16),
      child: InkWell(
        splashColor: Colors.transparent,
        onTap: () {
          callback();
        },
        child: Container(
          child: ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(16.0)),
            child: Stack(
              children: <Widget>[
                Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisAlignment: MainAxisAlignment.start,
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Container(
                      width: 40,
                      height: 40,
                      child: Center(
                        child: Icon(
                          Icons.notifications_active,
                          color: Theme.of(context).appBarTheme.color,
                          size: 30,
                        ),
                      ),
                    ),
                    Expanded(
                      child: Container(
                        color: Colors.transparent,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            Expanded(
                              child: Container(
                                child: Padding(
                                  padding: const EdgeInsets.only(
                                      left: 16, top: 0, bottom: 8, right: 16),
                                  child: Column(
                                    mainAxisAlignment:MainAxisAlignment.center,
                                    crossAxisAlignment:CrossAxisAlignment.start,
                                    children: <Widget>[
                                      Text(
                                        notification.pushTitle,
                                        textAlign: TextAlign.start,
                                        style: TextStyle(
                                          fontWeight: FontWeight.w600,
                                          fontSize: 18,
                                          color: Theme.of(context).textTheme.caption.color
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.only(top:5.0),
                                        child: Text(
                                          notification.pushMsg,
                                          overflow: TextOverflow.ellipsis,
                                          maxLines: 99999,
                                          style: TextStyle(
                                              fontSize: 14,
                                              color: Theme.of(context).textTheme.subtitle1.color),
                                        ),
                                      ),
                                      Padding(
                                        padding: EdgeInsets.only(top:10, bottom: 15),
                                        child: Text(
                                          Helpers.formatDate(int.parse(notification.createdAt), 'MM/dd/yyyy hh:mm a'),
                                          overflow: TextOverflow.ellipsis,
                                          maxLines: 1,
                                          style: TextStyle(
                                              fontSize: 12,
                                              color: Theme.of(context).textTheme.subtitle1.color),
                                        ),
                                      ),
                                      Divider(height: 1,)
                                    ],
                                  ),
                                ),
                              ),
                            ),
                            
                          ],
                        ),
                      ),
                    ),
                    
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
