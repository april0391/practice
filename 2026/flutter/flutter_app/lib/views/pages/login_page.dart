import 'package:flutter/material.dart';
import 'package:flutter_app/views/widget_tree.dart';
import 'package:flutter_app/views/widgets/hero_widget.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key, required this.title});

  final String title;

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final controllerEmail = TextEditingController();
  final controllerPw = TextEditingController();
  String confirmedEmail = '123';
  String confirmedPw = '456';

  @override
  void dispose() {
    controllerEmail.dispose();
    controllerPw.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          children: [
            HeroWidget(
              title: widget.title,
            ),
            SizedBox(height: 10.0),
            TextField(
              controller: controllerEmail,
              decoration: InputDecoration(
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(15.0),
                ),
                hintText: "Email",
              ),
              onEditingComplete: () {
                setState(() {});
              },
            ),
            SizedBox(height: 10.0),
            TextField(
              controller: controllerPw,
              decoration: InputDecoration(
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(15.0),
                ),
                hintText: "Password",
              ),
              onEditingComplete: () {
                setState(() {});
              },
            ),
            SizedBox(height: 20.0),
            ElevatedButton(
              onPressed: () {
                onLoginPressed();
              },
              style: ElevatedButton.styleFrom(
                minimumSize: Size(double.infinity, 40.0),
              ),
              child: Text(widget.title),
            ),
          ],
        ),
      ),
    );
  }

  void onLoginPressed() {
    if (confirmedEmail != controllerEmail.text ||
        confirmedPw != controllerPw.text) {
      return;
    }

    Navigator.pushReplacement(
      context,
      MaterialPageRoute(
        builder: (context) {
          return WidgetTree();
        },
      ),
    );
  }
}
