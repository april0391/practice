import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:playground/title_text_widget.dart';

final myFutureProvider = FutureProvider<int>((ref) async {
  await Future.delayed(Duration(seconds: 2));
  return Random().nextInt(100);
});

class MyFutureProviderWidget extends ConsumerWidget {
  const MyFutureProviderWidget({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final asyncValue = ref.watch(myFutureProvider);

    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          TitleText("FutureProvider"),

          asyncValue.when(
            data: (data) => Text(data.toString()),
            error: (error, stackTrace) => Text(error.toString()),
            loading: () => CircularProgressIndicator(),
          ),

          FilledButton(
            onPressed: () {
              ref.invalidate(myFutureProvider);
            },
            child: Text("invalidate"),
          ),
        ],
      ),
    );
  }
}
