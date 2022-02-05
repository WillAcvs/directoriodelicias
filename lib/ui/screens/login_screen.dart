
import 'dart:async';

import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:rounded_loading_button/rounded_loading_button.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/commons/services.dart';
import 'package:directorio_delicias/fetcher/auth_fetcher.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/ui/screens/register_screen.dart';

class LoginScreen extends StatefulWidget {

  LoginScreen({Key key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  @override
  void initState() {
    
    super.initState();
  }

  final RoundedLoadingButtonController _btnController = new RoundedLoadingButtonController();
  final RoundedLoadingButtonController _btnRegisterController = new RoundedLoadingButtonController();
  final TextEditingController _usernameController = new TextEditingController();
  final TextEditingController _passwordController = new TextEditingController();

  /*void _loginTwitter () async {
    AuthFetcher(
      didError: (DataHandler dataHandler) {
        Helpers.showAlertDialog(
          context: context, 
          title: tr("action_error"), 
          message: tr('error_signin_twitter'),
          onTapOk: () {

        });
      },
      didLogged: (DataHandler dataHandler) {
        Timer(Duration(seconds: 2), () {
          dataHandler.isLogged = true;
          MyApp.loggedUser = dataHandler.user;
          Navigator.pop(context, dataHandler);
        });
      }
    ).loginTwitter();
  }*/

  void _register() async {
    final dataHandler = await Navigator.push<dynamic>(
      context,
      MaterialPageRoute<dynamic>(
          builder: (BuildContext context) => RegisterScreen(),
          fullscreenDialog: true),
    );

    if(dataHandler != null) {
      _btnRegisterController.success();
      Timer(Duration(seconds: 2), () {
        dataHandler.isLogged = true;
          Navigator.pop(context, dataHandler);
      });
    }
    else {
      _btnRegisterController.reset();
    }
  }

  void _login() async {
    AuthFetcher(
      didError: (DataHandler dataHandler) {
        _btnController.error();
        Timer(Duration(seconds: 2), () {
          _btnController.reset();
        });
      },
      didLogged: (DataHandler dataHandler) {
        _btnController.success();
        Timer(Duration(seconds: 2), () {
          dataHandler.isLogged = true;
          MyApp.loggedUser = dataHandler.user;
          Navigator.pop(context, dataHandler);
        });
      }
    ).loginUser(
      username:_usernameController.text, 
      password: _passwordController.text
    );
  } 

 /* void _loginFacebook() async {
    AuthFetcher(
      didError: (DataHandler dataHandler) {
        Helpers.showAlertDialog(
          context: context, 
          title: tr("action_error"), 
          message: tr('error_signin_fb'),
          onTapOk: () {

        });
      },
      didLogged: (DataHandler dataHandler) {
        Timer(Duration(seconds: 2), () {
          dataHandler.isLogged = true;
          MyApp.loggedUser = dataHandler.user;
          Navigator.pop(context, dataHandler);
        });
      }
    ).loginFacebook();
  } */

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Theme.of(context).backgroundColor,
      child: SafeArea(
        top: false,
        child: Scaffold(
          backgroundColor: Theme.of(context).backgroundColor,
          body: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              getAppBarUI(),
              main(),
            ]
          ),
        ),
      ),
    );
  }

  Widget main() {
    return Expanded(
    child: Padding(
      padding: EdgeInsets.all(20),
      child: SingleChildScrollView(
        child: SizedBox(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              createTextTitle(tr("username")),
              _buildUsername(),
              createTextTitle(tr("password")),
              _buildPassword(),
              Padding(
                padding: const EdgeInsets.only(top: 16),
                child: Center(
                  child: RoundedLoadingButton(
                    width: MediaQuery.of(context).size.width,
                    color: Theme.of(context).accentColor,
                    controller: _btnController,
                    onPressed: _login,
                    child: Text(
                      tr("login"),
                      style: TextStyle(
                        fontWeight: FontWeight.w500,
                        color: Theme.of(context).floatingActionButtonTheme.foregroundColor,
                      ),
                    ),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 16),
                child: Center(
                  child: RoundedLoadingButton(
                    width: MediaQuery.of(context).size.width,
                    color: Theme.of(context).accentColor,
                    controller: _btnRegisterController,
                    onPressed: _register,
                    child: Text(
                      tr("register"),
                      style: TextStyle(
                        fontWeight: FontWeight.w500,
                        color: Theme.of(context).floatingActionButtonTheme.foregroundColor,
                      ),
                    ),
                  ),
                ),
              ),
              SizedBox(
                height: 50,
              ),
              /*Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  Expanded(
                    child: Divider(height: 1,color: Theme.of(context).textTheme.subtitle1.color,),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left:10, right:10),
                    child: Text(
                      tr("or"),
                      style: TextStyle(
                        fontWeight: FontWeight.w500,
                        color: Theme.of(context).textTheme.subtitle1.color
                      ),
                    ),
                  ),
                  Expanded(
                    child: Divider(height: 1,color: Theme.of(context).textTheme.subtitle1.color,),
                  ),

                ]
              ),
              SizedBox(height: 50),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  InkWell(
                    borderRadius: BorderRadius.all(Radius.circular(30)),
                    child: Container(
                      decoration: BoxDecoration(
                        color: HexColor("#3b5998"),
                        borderRadius: const BorderRadius.all(Radius.circular(60)),
                        boxShadow: <BoxShadow>[
                          BoxShadow(
                              color: Theme.of(context).shadowColor,
                              offset: const Offset(1.1, 1.1),
                              blurRadius: 10.0),
                        ],
                      ),
                      child: Padding(
                        padding: const EdgeInsets.all(20.0),
                        child: Icon(
                          FontAwesomeIcons.facebookF,
                          color: Colors.white,
                          size: 30,
                        ),
                      ),
                    ),
                    onTap: _loginFacebook,
                  ),
                  SizedBox(width: 20),
                  InkWell(
                    borderRadius: BorderRadius.all(Radius.circular(30)),
                    child: Container(
                      decoration: BoxDecoration(
                        color: HexColor("#00acee"),
                        borderRadius: const BorderRadius.all(Radius.circular(60)),
                        boxShadow: <BoxShadow>[
                          BoxShadow(
                              color: Theme.of(context).shadowColor,
                              offset: const Offset(1.1, 1.1),
                              blurRadius: 10.0),
                        ],
                      ),
                      child: Padding(
                        padding: const EdgeInsets.all(20.0),
                        child: Icon(
                          FontAwesomeIcons.twitter,
                          color: Colors.white,
                          size: 30,
                        ),
                      ),
                    ),
                    onTap: _loginTwitter,
                  )

                ]
              ),*/
            ],
          ),
        ),
      ),
    ),
  );
  }

  Widget createTextTitle(String str) {
    return Container(
      padding: const EdgeInsets.only(top: 8),
      child: Text(
        str,
        textAlign: TextAlign.left,
        style: TextStyle(
          fontSize: 20,
          fontWeight: FontWeight.bold,
          color: Theme.of(context).textTheme.subtitle1.color
        ),
      ),
    );
  }

  Widget _buildUsername() {
    return Padding(
      padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1.color,
          borderRadius: BorderRadius.circular(8),
        ),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(25),
          child: Container(
            padding: const EdgeInsets.all(4.0),
            height: 50,
            child: Container(
              padding:
                  const EdgeInsets.only(left: 10, right: 10, top: 0, bottom: 0),
              child: TextField(
                controller: _usernameController,
                maxLines: null,
                onChanged: (String txt) {},
                style: TextStyle(
                  fontSize: 16,
                  color: Theme.of(context).textTheme.caption.color,
                ),
                cursorColor: Theme.of(context).accentColor,
                decoration: InputDecoration(
                    border: InputBorder.none,
                    hintText: tr("enter_username")),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildPassword() {
    return Padding(
      padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1.color,
          borderRadius: BorderRadius.circular(8),
        ),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(25),
          child: Container(
            padding: const EdgeInsets.all(4.0),
            height: 50,
            child: Container(
              padding:
                  const EdgeInsets.only(left: 10, right: 10, top: 0, bottom: 0),
              child: TextField(
                obscureText: true,
                controller: _passwordController,
                onChanged: (String txt) {},
                style: TextStyle(
                  fontSize: 16,
                  color: Theme.of(context).textTheme.caption.color,
                ),
                cursorColor: Theme.of(context).accentColor,
                decoration: InputDecoration(
                    border: InputBorder.none,
                    hintText: tr("your_password")),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget getAppBarUI() {
    return Container(
      decoration: BoxDecoration(
        color: Theme.of(context).backgroundColor,
      ),
      child: Padding(
        padding: EdgeInsets.only(
            top: MediaQuery.of(context).padding.top, left: 8, right: 8),
        child: Row(
          children: <Widget>[
            Container(
              alignment: AlignmentDirectional.centerStart,
              width: AppBar().preferredSize.height + 40,
              height: AppBar().preferredSize.height,
              child: Material(
                color: Colors.transparent,
                child: InkWell(
                  borderRadius: const BorderRadius.all(
                    Radius.circular(32.0),
                  ),
                  onTap: () {
                    Navigator.pop(context);
                  },
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Icon(Icons.close),
                  ),
                ),
              ),
            ),
            
            Container(
              width: AppBar().preferredSize.height + 40,
              height: AppBar().preferredSize.height,
            )
          ],
        ),
      ),
    );
  }
}
