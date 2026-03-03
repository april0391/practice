import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:playground/title_text_widget.dart';

final myProvider = Provider((ref) => "Hello provider");

class MyProviderWidget extends ConsumerWidget {
  const MyProviderWidget({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final value = ref.watch(myProvider);

    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          TitleText("Provider"),
          Text(value),
        ],
      ),
    );
  }
}
