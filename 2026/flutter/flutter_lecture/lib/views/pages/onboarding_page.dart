import 'package:flutter/material.dart';
import 'package:flutter_app/views/pages/login_page.dart';
import 'package:lottie/lottie.dart';

class OnboardingPage extends StatelessWidget {
  const OnboardingPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          children: [
            Lottie.asset(
              "assets/lotties/Home 3d illustration.json",
              height: 400.0,
            ),
            SizedBox(height: 20.0),
            Text("Onboarding"),
            FilledButton(
              onPressed: () => Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) {
                    return LoginPage(title: "Register");
                  },
                ),
              ),
              style: ElevatedButton.styleFrom(
                minimumSize: Size(double.infinity, 40.0),
              ),
              child: Text("Next"),
            ),
          ],
        ),
      ),
    );
  }
}
