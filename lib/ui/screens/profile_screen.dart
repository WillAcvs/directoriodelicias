// ignore_for_file: non_constant_identifier_names

import 'dart:async';
import 'dart:io';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:image_picker/image_picker.dart';
import 'package:rounded_loading_button/rounded_loading_button.dart';
import 'package:directorio_delicias/commons/helpers.dart';
import 'package:directorio_delicias/dataparser/data_handler.dart';
import 'package:directorio_delicias/fetcher/auth_fetcher.dart';
import 'package:directorio_delicias/main.dart';
import 'package:directorio_delicias/models/user.dart';
import 'package:directorio_delicias/ui/widgets/load_widget.dart';

class ProfileScreen extends StatefulWidget {
  ProfileScreen({Key? key}) : super(key: key);

  @override
  _ProfileScreenState createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  @override
  void initState() {
    super.initState();
  }

  final RoundedLoadingButtonController _btnController =
      new RoundedLoadingButtonController();
  final TextEditingController _passwordController = new TextEditingController();
  final TextEditingController _passwordConfirmController =
      new TextEditingController();
  final TextEditingController _fullNameController = new TextEditingController();
  final RoundedLoadingButtonController _btnLogoutController =
      new RoundedLoadingButtonController();
  PickedFile? photoFile;
  final picker = ImagePicker();
  User? loggedUser;

  void _logout() {
    AuthFetcher(
            didLogged: (DataHandler dataHandler) {
              _btnLogoutController.success();
              Timer(Duration(seconds: 2), () {
                dataHandler.isLogged = false;
                MyApp.loggedUser = null;
                Navigator.pop(context, dataHandler);
              });
            },
            didError: (DataHandler) {})
        .logoutUser();
  }

  void update() async {
    AuthFetcher(didError: (DataHandler dataHandler) {
      _btnController.error();
      Timer(Duration(seconds: 2), () {
        _btnController.reset();
      });
    }, didLogged: (DataHandler dataHandler) {
      _btnController.success();
      Timer(Duration(seconds: 2), () {
        Navigator.pop(context, dataHandler);
      });
    }).updateProfile(
        userId: loggedUser!.userId,
        password: _passwordController.text,
        fullName: _fullNameController.text,
        thumbUrl: loggedUser!.thumbUrl,
        photoFile: photoFile != null ? photoFile!.path : null);
  }

  Future<User> getData() async {
    loggedUser = MyApp.loggedUser;
    print(loggedUser?.toMap().toString());
    print(loggedUser?.fullName.toString());
    return loggedUser!;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<User>(
      future: getData(),
      builder: (BuildContext context, AsyncSnapshot<User> snapshot) {
        if (!snapshot.hasData) {
          return LoadingWidget(
            size: 30.0,
            iconColor: Theme.of(context).accentColor,
          );
        } else {
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
                    ]),
              ),
            ),
          );
        }
      },
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
                Center(
                    child: Material(
                  color: Colors.transparent,
                  child: InkWell(
                    borderRadius: const BorderRadius.all(Radius.circular(60.0)),
                    child: Container(
                      height: 120,
                      width: 120,
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        boxShadow: <BoxShadow>[
                          BoxShadow(
                              color: Theme.of(context).shadowColor,
                              offset: const Offset(2.0, 4.0),
                              blurRadius: 8),
                        ],
                      ),
                      child: ClipRRect(
                        borderRadius:
                            const BorderRadius.all(Radius.circular(60.0)),
                        child: photoFile != null
                            ? Image.file(File(photoFile!.path),
                                width: 120, height: 120, fit: BoxFit.cover)
                            : loggedUser?.thumbUrl != null
                                ? FadeInImage(
                                    fit: BoxFit.cover,
                                    placeholder: AssetImage(
                                        Helpers.getThemedPhotoPlaceHolder(
                                            context)),
                                    image: NetworkImage(loggedUser!.thumbUrl),
                                    width: 120,
                                    height: 120,
                                  )
                                : Image.asset(
                                    Helpers.getThemedPhotoPlaceHolder(context)),
                      ),
                    ),
                    onTap: () async {
                      PickedFile? pickedFile =
                          await picker.getImage(source: ImageSource.gallery);
                      print(pickedFile);
                      setState(() {
                        if (pickedFile != null) {
                          photoFile = pickedFile;
                        }
                      });
                    },
                  ),
                )),
                createTextTitle(tr("full_name")),
                _buildFullName(),

                Helpers.checkIfLoggedSocial()
                    ? Center()
                    : createTextTitle(tr("password")),
                Helpers.checkIfLoggedSocial() ? Center() : _buildPassword(),

                Helpers.checkIfLoggedSocial()
                    ? Center()
                    : createTextTitle(tr("confirm_password")),
                Helpers.checkIfLoggedSocial()
                    ? Center()
                    : _buildConfirmPassword(),

                Helpers.checkIfLoggedSocial()
                    ? Container(height: 200)
                    : Container(height: 0),

                // Helpers.checkIfLoggedSocial() ? Container(
                //   padding: EdgeInsets.only(top: 20, bottom: 10),
                //   child: Center(
                //     child: Text(
                //       tr("disabled_social_password"),
                //       style: TextStyle(
                //         fontSize: 17,
                //         fontWeight: FontWeight.normal,
                //         color: Theme.of(context).textTheme.subtitle1.color
                //       ),
                //     ),
                //   ),
                // ) : Container(height: 0),

                Padding(
                  padding: const EdgeInsets.only(top: 16),
                  child: Center(
                    child: RoundedLoadingButton(
                      width: MediaQuery.of(context).size.width,
                      color: Theme.of(context).accentColor,
                      controller: _btnController,
                      onPressed: update,
                      child: Text(
                        tr("update"),
                        style: TextStyle(
                          fontWeight: FontWeight.w500,
                          color: Theme.of(context)
                              .floatingActionButtonTheme
                              .foregroundColor,
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
                      onPressed: _logout,
                      controller: _btnLogoutController,
                      child: Text(
                        tr("logout"),
                        style: TextStyle(
                          fontWeight: FontWeight.w500,
                          color: Theme.of(context)
                              .floatingActionButtonTheme
                              .foregroundColor,
                        ),
                      ),
                    ),
                  ),
                ),
                SizedBox(
                  height: 50,
                ),
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
            color: Theme.of(context).textTheme.subtitle1?.color),
      ),
    );
  }

  Widget _buildPassword() {
    return Padding(
      padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1?.color,
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
                enabled: Helpers.checkIfLoggedSocial() ? false : true,
                obscureText: true,
                controller: _passwordController,
                onChanged: (String txt) {},
                style: TextStyle(
                  fontSize: 16,
                  color: Theme.of(context).textTheme.caption?.color,
                ),
                cursorColor: Theme.of(context).accentColor,
                decoration: InputDecoration(
                    border: InputBorder.none, hintText: tr("your_password")),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildConfirmPassword() {
    return Padding(
      padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1?.color,
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
                enabled: Helpers.checkIfLoggedSocial() ? false : true,
                obscureText: true,
                controller: _passwordConfirmController,
                onChanged: (String txt) {},
                style: TextStyle(
                  fontSize: 16,
                  color: Theme.of(context).textTheme.caption?.color,
                ),
                cursorColor: Theme.of(context).accentColor,
                decoration: InputDecoration(
                    border: InputBorder.none, hintText: tr("confirm_password")),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildFullName() {
    return Padding(
      padding: const EdgeInsets.only(top: 10, left: 0, right: 0),
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).textTheme.bodyText1?.color,
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
                controller: _fullNameController..text = loggedUser!.fullName,
                maxLines: null,
                onChanged: (String txt) {},
                style: TextStyle(
                  fontSize: 16,
                  color: Theme.of(context).textTheme.caption?.color,
                ),
                cursorColor: Theme.of(context).accentColor,
                decoration: InputDecoration(
                    border: InputBorder.none,
                    hintText: tr("full_name_placeholder")),
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
