import 'dart:async';
import 'dart:math';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:playground/title_text_widget.dart';

final myAsyncNotifierProvider =
    AsyncNotifierProvider<_MyAsyncNotifierProvider, int>(
      _MyAsyncNotifierProvider.new,
    );

class _MyAsyncNotifierProvider extends AsyncNotifier<int> {
  @override
  FutureOr<int> build() async {
    await Future.delayed(Duration(seconds: 2));

    return Random().nextInt(100);
  }

  Future<void> increase() async {
    state = await AsyncValue.guard(() async {
      final currentValue = state.value ?? 0;
      return currentValue + 1;
    });
  }
}

class MyAsyncNotifierProviderWidget extends ConsumerWidget {
  const MyAsyncNotifierProviderWidget({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final asyncValue = ref.watch(myAsyncNotifierProvider);

    return Column(
      children: [
        TitleText("AsyncNotifierProvider"),

        asyncValue.when(
          data: (data) => Text(data.toString()),
          error: (error, stackTrace) => Text(error.toString()),
          loading: () => CircularProgressIndicator(),
        ),

        FilledButton(
          onPressed: () =>
              ref.read(myAsyncNotifierProvider.notifier).increase(),
          child: Text("inc"),
        ),
      ],
    );
  }
}
