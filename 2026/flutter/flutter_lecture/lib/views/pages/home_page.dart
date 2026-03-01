import 'package:flutter/material.dart';
import 'package:flutter_app/views/pages/course_page.dart';
import 'package:flutter_app/views/widgets/container_widget.dart';
import 'package:flutter_app/views/widgets/hero_widget.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(20.0),
      child: SingleChildScrollView(
        child: Column(
          children: [
            HeroWidget(
              title: "Flutter Mapp",
              nextPage: CoursePage(),
            ),
            for (int i = 0; i < 5; i++)
              ContainerWidget(
                title: "Basic layout $i",
                description: "This is a description",
              ),
          ],
        ),
      ),
    );
  }
}
