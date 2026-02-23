import 'package:flutter/material.dart';

// void main() {
//   runApp(const MyApp());
// }

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // 이 위젯은 애플리케이션의 루트입니다.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // 이것은 애플리케이션의 테마입니다.
        //
        // 시도해 보세요: "flutter run"으로 애플리케이션을 실행해 보세요.
        // 애플리케이션에 보라색 툴바가 나타날 것입니다. 그런 다음, 앱을 종료하지 않고
        // 아래 colorScheme의 seedColor를 Colors.green으로 변경한 다음
        // "핫 리로드"를 호출해 보세요 (변경 사항을 저장하거나 Flutter 지원 IDE에서
        // "핫 리로드" 버튼을 누르거나, 명령줄을 사용하여 앱을 시작했다면 "r"을 누르세요).
        //
        // 카운터가 0으로 재설정되지 않는 것을 확인하세요. 애플리케이션
        // 상태는 리로드 중에도 손실되지 않습니다. 상태를 재설정하려면 핫
        // 리스타트를 사용하세요.
        //
        // 이것은 코드에도 적용됩니다. 값뿐만 아니라 대부분의 코드 변경 사항은
        // 핫 리로드만으로 테스트할 수 있습니다.
        colorScheme: .fromSeed(seedColor: Colors.deepOrangeAccent),
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  // 이 위젯은 애플리케이션의 홈 페이지입니다. 상태를 가지는(stateful) 위젯으로,
  // 아래에 정의된 State 객체를 가지며, 이 객체는 위젯의
  // 모양에 영향을 미치는 필드를 포함합니다.

  // 이 클래스는 상태에 대한 구성입니다. 이 클래스는 부모(이 경우 App 위젯)가 제공한
  // 값(이 경우 title)을 보유하며, State의 build 메서드에서 사용됩니다.
  // Widget 서브클래스의 필드는 항상 "final"로 표시됩니다.

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // setState 호출은 Flutter 프레임워크에 이 State에서 무언가 변경되었음을 알립니다.
      // 이로 인해 아래 build 메서드가 다시 실행되어
      // 업데이트된 값을 화면에 반영할 수 있습니다. 만약 setState()를 호출하지 않고
      // _counter를 변경했다면, build 메서드는 다시 호출되지 않을 것이고,
      // 아무 일도 일어나지 않을 것입니다.
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    // 이 메서드는 setState가 호출될 때마다 다시 실행됩니다. 예를 들어,
    // 위 _incrementCounter 메서드에서처럼요.
    //
    // Flutter 프레임워크는 build 메서드를 빠르게 다시 실행하도록 최적화되어 있습니다.
    // 따라서 위젯의 개별 인스턴스를 변경하는 대신
    // 업데이트해야 하는 모든 것을 다시 빌드할 수 있습니다.
    return Scaffold(
      appBar: AppBar(
        // 시도해 보세요: 여기 색상을 특정 색상(예: Colors.amber)으로 변경하고
        // 핫 리로드를 트리거하여 다른 색상은 그대로 유지하면서
        // AppBar 색상이 변경되는 것을 확인해 보세요.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // 여기서는 App.build 메서드에 의해 생성된 MyHomePage 객체의 값을 가져와
        // 앱바 제목을 설정하는 데 사용합니다.
        title: Text(widget.title),
      ),
      body: Center(
        // Center는 레이아웃 위젯입니다. 단일 자식을 받아
        // 부모의 중앙에 배치합니다.
        child: Column(
          // Column 또한 레이아웃 위젯입니다. 자식 위젯 목록을 받아
          // 수직으로 정렬합니다. 기본적으로 자식 위젯의 너비에 맞춰 크기를 조정하고
          // 부모만큼 높이를 가지려고 합니다.
          //
          // Column은 크기 조정 및 자식 위젯 배치 방법을 제어하는 다양한 속성을 가집니다.
          // 여기서는 mainAxisAlignment를 사용하여 자식 위젯을 수직으로 중앙에 배치합니다.
          // Column은 수직이므로 여기서 주 축은 수직 축입니다 (교차 축은 수평 축이 됩니다).
          //
          // 시도해 보세요: "디버그 페인팅"을 호출하여 (IDE에서 "Toggle Debug Paint"
          // 액션을 선택하거나 콘솔에서 "p"를 누르세요) 각 위젯의
          // 와이어프레임을 확인해 보세요.
          mainAxisAlignment: .center,
          children: [
            const Text('You have pushed the button this many times:'),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
