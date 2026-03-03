import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class _BottomNavItem {
  final int index;
  final IconData icon;
  final String label;

  _BottomNavItem(this.index, this.icon, this.label);
}

class _BottomNavNotifier extends Notifier<int> {
  @override
  int build() {
    return 0;
  }

  void update(int index) {
    state = index;
  }
}

final bottomNavProvider = NotifierProvider<_BottomNavNotifier, int>(
  _BottomNavNotifier.new,
);

final bottomNavItems = [
  _BottomNavItem(0, Icons.home, "provider"),
  _BottomNavItem(1, Icons.home, "futuer_provider"),
  _BottomNavItem(2, Icons.home, "notifier_provider"),
  _BottomNavItem(3, Icons.home, "async_notifier_provider"),
];
