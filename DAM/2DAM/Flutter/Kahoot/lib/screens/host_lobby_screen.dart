import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import '../services/firebase_service.dart';
import 'game_screen.dart';

class HostLobbyScreen extends StatefulWidget {
  const HostLobbyScreen({super.key});

  @override
  State<HostLobbyScreen> createState() => _HostLobbyScreenState();
}

class _HostLobbyScreenState extends State<HostLobbyScreen> {
  final FirebaseService _firebaseService = FirebaseService();
  String? _sessionCode;
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _createSession();
  }

  Future<void> _createSession() async {
    try {
      String code = await _firebaseService.createSession();
      setState(() {
        _sessionCode = code;
        _isLoading = false;
      });
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error creating session: $e')),
      );
      setState(() => _isLoading = false);
    }
  }
  
  void _startGame() async {
    if (_sessionCode == null) return;
    await _firebaseService.startGame(_sessionCode!);
    if (!mounted) return;
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (_) => GameScreen(sessionCode: _sessionCode!, isHost: true)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Host Lobby')),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _sessionCode == null
              ? const Center(child: Text('Failed to create session'))
              : Column(
                  children: [
                    const SizedBox(height: 32),
                    const Text('Game Code:', style: TextStyle(fontSize: 24)),
                    Text(
                      _sessionCode!,
                      style: const TextStyle(
                        fontSize: 48,
                        fontWeight: FontWeight.bold,
                        letterSpacing: 8,
                      ),
                    ),
                    const SizedBox(height: 32),
                    const Text('Players joined:', style: TextStyle(fontSize: 18)),
                    Expanded(
                      child: StreamBuilder<List<Map<String, dynamic>>>(
                        stream: _firebaseService.streamPlayers(_sessionCode!),
                        builder: (context, snapshot) {
                          if (!snapshot.hasData) {
                            return const Center(child: CircularProgressIndicator());
                          }
                          final players = snapshot.data!;
                          if (players.isEmpty) {
                            return const Center(child: Text('Waiting for players...'));
                          }
                          return ListView.builder(
                            itemCount: players.length,
                            itemBuilder: (context, index) {
                              return ListTile(
                                leading: const Icon(Icons.person),
                                title: Text(players[index]['name'] ?? 'Unknown'),
                              );
                            },
                          );
                        },
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: ElevatedButton(
                        onPressed: _startGame,
                        style: ElevatedButton.styleFrom(
                          minimumSize: const Size(double.infinity, 50),
                        ),
                        child: const Text('Start Game'),
                      ),
                    ),
                  ],
                ),
    );
  }
}
