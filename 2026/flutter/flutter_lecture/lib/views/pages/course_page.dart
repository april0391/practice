import 'dart:developer';

import 'package:flutter/material.dart';
import 'dart:convert' as convert;
import 'package:http/http.dart' as http;

class CoursePage extends StatefulWidget {
  const CoursePage({super.key});

  @override
  State<CoursePage> createState() => _CoursePageState();
}

class _CoursePageState extends State<CoursePage> {
  // 1. Future를 변수로 선언 (build마다 재호출 방지)
  late Future<Map<String, dynamic>> _dataFuture;

  @override
  void initState() {
    super.initState();
    // 2. 초기화 시점에 딱 한 번만 데이터를 가져오도록 설정
    _dataFuture = getData();
  }

  Future<Map<String, dynamic>> getData() async {
    var url = Uri.https('bored-api.appbrewery.com', '/random');
    var response = await http.get(url);

    if (response.statusCode == 200) {
      log(response.body);
      return convert.jsonDecode(response.body)
          as Map<String, dynamic>;
    } else {
      throw Exception("Failed to load.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Bored API")),
      body: FutureBuilder<Map<String, dynamic>>(
        future: _dataFuture, // 3. 할당된 Future 변수 사용
        builder: (context, snapshot) {
          // 로딩 중일 때
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          // 에러가 발생했을 때
          if (snapshot.hasError) {
            return Center(child: Text("에러 발생: ${snapshot.error}"));
          }

          // 데이터가 있을 때
          if (snapshot.hasData) {
            final data = snapshot.data!;

            return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    "활동: ${data["activity"]}",
                    style: TextStyle(fontSize: 18),
                  ),
                  Text("참여도: ${data["availability"]}"),
                  Text("유형: ${data["type"]}"),
                ],
              ),
            );
          }

          return const Center(child: Text("데이터가 없습니다."));
        },
      ),
    );
  }
}
