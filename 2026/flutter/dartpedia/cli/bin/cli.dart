import 'dart:io'; // Add this line at the top
import 'package:http/http.dart' as http; // Add this line
import 'package:command_runner/command_runner.dart';

const version = '0.0.1';

void main(List<String> arguments) {
  var commandRunner = CommandRunner()..addCommand(HelpCommand());
  commandRunner.run(arguments);
}
