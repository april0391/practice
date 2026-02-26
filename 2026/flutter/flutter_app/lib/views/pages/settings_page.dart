import 'package:flutter/material.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key, required this.title});

  final String title;

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  final controller = TextEditingController();
  bool? isChecked = false;
  bool isSwitched = false;
  var sliderValue = 0.0;
  String? menuItem;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        leading: BackButton(
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        automaticallyImplyActions: false,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            children: [
              ElevatedButton(
                onPressed: () {
                  ScaffoldMessenger.of(
                    context,
                  ).showSnackBar(
                    SnackBar(
                      content: Text("Snackbar"),
                      duration: Duration(seconds: 2),
                      behavior: SnackBarBehavior.floating,
                    ),
                  );
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.teal,
                  foregroundColor: Colors.white,
                ),
                child: Text("open snackbar"),
              ),
              Divider(
                color: Colors.amber,
                thickness: 5.0,
                endIndent: 50,
              ),
              ElevatedButton(
                onPressed: () {
                  showDialog(
                    context: context,
                    builder: (context) {
                      return AlertDialog(
                        title: Text("Alert Title"),
                        content: Text("Alert content"),
                        actions: [
                          FilledButton(
                            onPressed: () {
                              Navigator.pop(context);
                            },
                            child: Text("Close"),
                          ),
                        ],
                      );
                    },
                  );
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.teal,
                  foregroundColor: Colors.white,
                ),
                child: Text("open dialog"),
              ),
              DropdownButton(
                value: menuItem,
                items: List.generate(
                  10,
                  (index) => DropdownMenuItem(
                    value: "item_$index",
                    child: Text("Element $index"),
                  ),
                ),
                onChanged: (value) => setState(() {
                  menuItem = value;
                }),
              ),
              TextField(
                controller: controller,
                decoration: InputDecoration(
                  border: OutlineInputBorder(),
                ),
                onEditingComplete: () {
                  setState(() {});
                },
              ),
              Text(controller.text),
              Checkbox.adaptive(
                value: isChecked,
                onChanged: (value) => setState(() {
                  isChecked = value;
                }),
              ),
              CheckboxListTile(
                title: Text("Click"),
                value: isChecked,
                onChanged: (value) => setState(() {
                  isChecked = value;
                }),
              ),
              Switch.adaptive(
                value: isSwitched,
                onChanged: (value) => setState(() {
                  isSwitched = value;
                }),
              ),
              SwitchListTile(
                title: Text("Switch"),
                value: isSwitched,
                onChanged: (value) => setState(() {
                  isSwitched = value;
                }),
              ),
              Slider.adaptive(
                max: 10.0,
                divisions: 10,
                value: sliderValue,
                onChanged: (value) => setState(() {
                  print(value);
                  sliderValue = value;
                }),
              ),
              InkWell(
                onTap: () {
                  print("image selected");
                },
                child: Container(
                  height: 50,
                  width: double.infinity,
                  color: Colors.white12,
                ),
              ),
              ElevatedButton(
                onPressed: () {},
                child: Text("click me"),
              ),
              FilledButton(
                onPressed: () {},
                child: Text("click me"),
              ),
              TextButton(
                onPressed: () {},
                child: Text("click me"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
