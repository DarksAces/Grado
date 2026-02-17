import 'package:flutter/material.dart';
import '../widgets/custom_button.dart';

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
              text: 'Jugar Quiz',
              color: Colors.green,
              onPressed: () {
                // Navegación o lógica aquí
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Comenzando juego...')),
                );
              },
            ),
            const SizedBox(height: 16),
            CustomButton(
              text: 'Crear Quiz',
              color: Colors.blue,
              onPressed: () {
                 ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Creando nuevo quiz...')),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
