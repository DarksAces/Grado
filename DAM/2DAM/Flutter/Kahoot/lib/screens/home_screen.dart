import 'package:flutter/material.dart';
import '../widgets/custom_button.dart';
import 'host_lobby_screen.dart';
import 'client_join_screen.dart';
import 'question_creator_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Kahoot Clone'),
        backgroundColor: Colors.purple,
        foregroundColor: Colors.white,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(
              Icons.quiz,
              size: 100,
              color: Colors.purple,
            ),
            const SizedBox(height: 32),
            const Text(
              'Bienvenido a Kahoot!',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 64),
            CustomButton(
              text: 'Jugar Quiz (Cliente)',
              color: Colors.green,
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const ClientJoinScreen()),
                );
              },
            ),
            const SizedBox(height: 16),
            CustomButton(
              text: 'Crear Quiz (Servidor)',
              color: Colors.blue,
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const HostLobbyScreen()),
                );
              },
            ),
            const SizedBox(height: 16),
            CustomButton(
              text: 'Banco de Preguntas (Admin)',
              color: Colors.orange,
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const QuestionCreatorScreen()),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
