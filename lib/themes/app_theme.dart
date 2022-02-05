import 'package:flutter/material.dart';

class AppTheme {
  AppTheme._();

  static const Color darkText = Color(0xFF253840);
  static const Color darkerText = Color(0xFF17262A);
  static const Color lightText = Color(0xFF4A6572);
  static const Color themeBackgroundColor = Color(0xFF1C1A1B);

  static const Color primaryColor = Color(0xFFfd872a);
  static const Color secondaryColor = Color(0xFFfd872a);

  static const TextTheme textTheme = TextTheme(
    headline4: display1,
    headline5: headline,
    headline6: title,
    subtitle2: subtitle,
    bodyText1: body2,
    bodyText2: body1,
    caption: caption,
  );

  static const TextStyle display1 = TextStyle(
    // h4 -> display1
    fontFamily: 'WorkSans',
    fontWeight: FontWeight.bold,
    fontSize: 36,
    letterSpacing: 0.4,
    height: 0.9,
    color: darkerText,
  );

  static const TextStyle headline = TextStyle(
    // h5 -> headline
    fontFamily: 'WorkSans',
    fontWeight: FontWeight.bold,
    fontSize: 24,
    letterSpacing: 0.27,
    color: darkerText,
  );

  static const TextStyle title = TextStyle(
    // h6 -> title
    fontFamily: 'WorkSans',
    fontWeight: FontWeight.bold,
    fontSize: 16,
    letterSpacing: 0.18,
    color: darkerText,
  );

  static const TextStyle subtitle = TextStyle(
    // subtitle2 -> subtitle
    fontFamily: 'WorkSans',
    fontWeight: FontWeight.w400,
    fontSize: 14,
    letterSpacing: -0.04,
    color: darkText,
  );

  static const TextStyle body2 = TextStyle(
    // body1 -> body2
    fontFamily: 'WorkSans',
    fontWeight: FontWeight.w400,
    fontSize: 14,
    letterSpacing: 0.2,
    color: darkText,
  );

  static const TextStyle body1 = TextStyle(
    // body2 -> body1
    fontFamily: 'WorkSans',
    fontWeight: FontWeight.w400,
    fontSize: 16,
    letterSpacing: -0.05,
    color: darkText,
  );

  static const TextStyle caption = TextStyle(
    // Caption -> caption
    fontFamily: 'WorkSans',
    fontWeight: FontWeight.w400,
    fontSize: 12,
    letterSpacing: 0.2,
    color: lightText, // was lightText
  );


  static ThemeData buildLightTheme() {
    final ColorScheme colorScheme = const ColorScheme.light().copyWith(
      primary: primaryColor,
      secondary: secondaryColor,
    );

    final ThemeData base = ThemeData.light();
    return base.copyWith(
      brightness: Brightness.light,
      appBarTheme: _buildAppBarLightTheme(base.appBarTheme),
      colorScheme: colorScheme,
      primaryColor: primaryColor,
      buttonColor: primaryColor,
      indicatorColor: Colors.white,
      splashColor: Colors.black38,
      splashFactory: InkRipple.splashFactory,
      accentColor: secondaryColor,
      canvasColor: Color(0xFF1C1A1B),
      backgroundColor: const Color(0xFFFFFFFF),
      scaffoldBackgroundColor: const Color(0xFFF6F6F6),
      errorColor: const Color(0xFFf53558),
      buttonTheme: ButtonThemeData(
        colorScheme: colorScheme,
        textTheme: ButtonTextTheme.primary,
      ),
      floatingActionButtonTheme: FloatingActionButtonThemeData(
        foregroundColor: Colors.white
      ),
      textTheme: _buildTextTheme(base.textTheme),
      primaryTextTheme: _buildTextTheme(base.primaryTextTheme),
      accentTextTheme: _buildTextTheme(base.accentTextTheme),
      platform: TargetPlatform.iOS,
      shadowColor: Colors.grey.withOpacity(0.6)
    );
  }

  static ThemeData buildDarkTheme() {
    final ColorScheme colorScheme = const ColorScheme.dark().copyWith(
      primary: primaryColor,
      secondary: secondaryColor,
    );
    
    final ThemeData base = ThemeData.dark();
    return base.copyWith(
      brightness: Brightness.dark,
      appBarTheme: _buildAppBarDarkTheme(base.appBarTheme),
      colorScheme: colorScheme,
      primaryColor: primaryColor,
      buttonColor: primaryColor,
      floatingActionButtonTheme: FloatingActionButtonThemeData(
        foregroundColor: const Color(0xFF343a42),
      ),
      indicatorColor: Colors.white,
      splashColor: Colors.white54,
      splashFactory: InkRipple.splashFactory,
      accentColor: secondaryColor,
      canvasColor: Color(0xFF1C1A1B), // for details page background
      backgroundColor: const Color(0xFF343a42),
      scaffoldBackgroundColor: const Color(0xFFF6F6F6),
      errorColor: const Color(0xFFf53558),
      buttonTheme: ButtonThemeData(
        colorScheme: colorScheme,
        textTheme: ButtonTextTheme.primary,
      ),
      textTheme: _buildTextDarkTheme(base.textTheme),
      primaryTextTheme: _buildTextTheme(base.primaryTextTheme),
      accentTextTheme: _buildTextTheme(base.accentTextTheme),
      platform: TargetPlatform.iOS,
      shadowColor: Colors.black.withOpacity(0.2)
    );
  }

  static AppBarTheme _buildAppBarDarkTheme(AppBarTheme base) {
    return base.copyWith(
      color: Colors.white, // for appbar and title color
    );
  }
  static AppBarTheme _buildAppBarLightTheme(AppBarTheme base) {
    return base.copyWith(
      color: const Color(0xFF343a42), // for appbar and title color
    );
  }

  // -- THEMING
  static TextTheme _buildTextTheme(TextTheme base) {
    const String fontName = 'WorkSans';
    return base.copyWith(
      headline1: base.headline1.copyWith(fontFamily: fontName),
      headline2: base.headline2.copyWith(fontFamily: fontName),
      headline3: base.headline3.copyWith(fontFamily: fontName),
      headline4: base.headline4.copyWith(fontFamily: fontName),
      headline5: base.headline5.copyWith(fontFamily: fontName),
      headline6: base.headline6.copyWith(fontFamily: fontName),
      button: base.button.copyWith(fontFamily: fontName, color: Colors.white),
      caption: base.caption.copyWith(fontFamily: fontName, color: Color(0xFF17262A)),
      bodyText1: base.bodyText1.copyWith(fontFamily: fontName, color: Color(0xFFF3F3F3)),
      bodyText2: base.bodyText2.copyWith(fontFamily: fontName, color: Color(0xFFEEEEEE)),
      subtitle1: base.subtitle1.copyWith(fontFamily: fontName, color: Colors.black.withOpacity(0.6)),
      subtitle2: base.subtitle2.copyWith(fontFamily: fontName, color: Color(0xFFF3F3F3)),
      overline: base.overline.copyWith(fontFamily: fontName),
    );
  }
  
  static TextTheme _buildTextDarkTheme(TextTheme base) {
    const String fontName = 'WorkSans';
    return base.copyWith(
      headline1: base.headline1.copyWith(fontFamily: fontName),
      headline2: base.headline2.copyWith(fontFamily: fontName),
      headline3: base.headline3.copyWith(fontFamily: fontName),
      headline4: base.headline4.copyWith(fontFamily: fontName),
      headline5: base.headline5.copyWith(fontFamily: fontName),
      headline6: base.headline6.copyWith(fontFamily: fontName),
      button: base.button.copyWith(fontFamily: fontName, color: Colors.white),
      caption: base.caption.copyWith(fontFamily: fontName, color: Colors.white), // main caption of the app
      bodyText1: base.bodyText1.copyWith(fontFamily: fontName, color: Color(0xFF202428)),
      bodyText2: base.bodyText2.copyWith(fontFamily: fontName, color: Color(0xFF202428)), // background for list entries in home
      subtitle1: base.subtitle1.copyWith(fontFamily: fontName, color: Colors.white.withOpacity(0.8)),
      subtitle2: base.subtitle2.copyWith(fontFamily: fontName, color: Color(0xFF202428)),
      overline: base.overline.copyWith(fontFamily: fontName),
    );
  }
}
