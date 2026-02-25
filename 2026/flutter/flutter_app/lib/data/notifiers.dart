import 'package:flutter/widgets.dart';

// ValueNotifier: hold the data
// ValueListenableBuilder: listen to the data

ValueNotifier<int> selectedPageNotifier = ValueNotifier(0);
ValueNotifier<bool> isDarkModeNotifier = ValueNotifier(true);
