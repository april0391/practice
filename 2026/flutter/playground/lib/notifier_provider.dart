import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:playground/title_text_widget.dart';

final myNotifierProvider = NotifierProvider<_MyNotifierProvider, int>(
  _MyNotifierProvider.new,
);

class _MyNotifierProvider extends Notifier<int> {
  @override
  int build() {
    return 0;
  }

  void increase() {
    state++;
  }

  void set(String value) {
    if (int.tryParse(value) != null) {
      state = value as int;
    }
  }
}

class MyNotifierProviderWidget extends ConsumerWidget {
  const MyNotifierProviderWidget({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final value = ref.watch(myNotifierProvider);

    return Center(
      child: Column(
        children: [
          TitleText("NotifierProvider"),
          Text(value.toString()),
          ElevatedButton(
            onPressed: () {
              ref.read(myNotifierProvider.notifier).increase();
            },
            child: Text("increase"),
          ),
          TextField(
            decoration: const InputDecoration(
              border: OutlineInputBorder(),
            ),
            onChanged: (text) {
              ref.read(myNotifierProvider.notifier).set(text);
            },
          ),
        ],
      ),
    );
  }
}
