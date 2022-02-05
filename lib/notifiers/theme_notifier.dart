
import 'package:flutter/foundation.dart';

class ThemeNotifier extends ChangeNotifier {
  
  bool isDarkMode = false;
 
  void updateTheme(bool isDarkMode) {
    this.isDarkMode = isDarkMode;
    notifyListeners();
  }

  void updateDarkMode(bool isDarkMode) {
    this.isDarkMode = isDarkMode;
  }

}