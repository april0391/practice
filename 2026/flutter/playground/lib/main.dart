import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:playground/async_notifier_provider.dart';

import 'package:playground/bottom_nav.dart';
import 'package:playground/notifier_provider.dart';
import 'package:playground/provider.dart';
import 'package:playground/future_provider.dart';

void main() {
  runApp(
    const ProviderScope(
      child: MyApp(),
    ),
  );
}

class MyApp extends ConsumerWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final currentIndex = ref.watch(bottomNavProvider);

    return MaterialApp(
      theme: ThemeData.light(),
      home: Scaffold(
        bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          items: bottomNavItems
              .map(
                (e) => BottomNavigationBarItem(
                  icon: Icon(e.icon),
                  label: e.label,
                ),
              )
              .toList(),
          currentIndex: currentIndex,
          onTap: (index) {
            ref.read(bottomNavProvider.notifier).update(index);
          },
        ),
        body: IndexedStack(
          index: currentIndex,
          children: const [
            MyProviderWidget(),
            MyFutureProviderWidget(),
            MyNotifierProviderWidget(),
            MyAsyncNotifierProviderWidget(),
          ],
        ),
      ),
    );
  }
}
