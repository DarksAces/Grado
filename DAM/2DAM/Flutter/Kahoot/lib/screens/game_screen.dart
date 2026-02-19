import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import '../services/firebase_service.dart';

class GameScreen extends StatelessWidget {
  final String sessionCode;
  final bool isHost;
  final FirebaseService _firebaseService = FirebaseService();

  GameScreen({super.key, required this.sessionCode, required this.isHost});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(isHost ? 'Host Game' : 'Player Game')),
      body: StreamBuilder<DocumentSnapshot<Map<String, dynamic>>>(
        stream: _firebaseService.streamSession(sessionCode),
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return const Center(child: CircularProgressIndicator());
          }
          
          final data = snapshot.data!.data();
          if (data == null) return const Center(child: Text('Session ended'));
          
          final String status = data['status'];
          final int? randomValue = data['randomValue'];

          // In Waiting State
          if (status == 'waiting') {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text('Waiting for game to start...'),
                  const SizedBox(height: 20),
                  Text(
                    'Verification Value: $randomValue',
                    style: Theme.of(context).textTheme.headlineMedium,
                  ),
                ],
              ),
            );
          }
          
          // In Active State (Questions would go here)
          return const Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(Icons.check_circle, size: 100, color: Colors.green),
                SizedBox(height: 20),
                Text('Game Started!', style: TextStyle(fontSize: 24)),
                // TODO: Show actual questions
              ],
            ),
          );
        },
      ),
    );
  }
}
