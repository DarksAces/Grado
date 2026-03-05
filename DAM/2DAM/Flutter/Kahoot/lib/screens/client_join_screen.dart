import 'package:flutter/material.dart';
import '../services/firebase_service.dart';
import 'game_screen.dart';

class ClientJoinScreen extends StatefulWidget {
  const ClientJoinScreen({super.key});

  @override
  State<ClientJoinScreen> createState() => _ClientJoinScreenState();
}

class _ClientJoinScreenState extends State<ClientJoinScreen> {
  final TextEditingController _codeController = TextEditingController();
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _valueController = TextEditingController();
  final FirebaseService _firebaseService = FirebaseService();
  bool _isLoading = false;

  void _joinSession() async {
    final code = _codeController.text.trim().toUpperCase();
    final name = _nameController.text.trim();
    final valueStr = _valueController.text.trim();

    if (code.isEmpty || name.isEmpty || valueStr.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please fill all fields')),
      );
      return;
    }
    
    final value = int.tryParse(valueStr);
    if (value == null) {
       ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Value must be a number')),
      );
      return;
    }

    setState(() => _isLoading = true);

    try {
      await _firebaseService.joinSession(code, name, value);
      if (!mounted) return;
      
      // Navigate to waiting/game screen
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => GameScreen(sessionCode: code, isHost: false, nickname: name)),
      );
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error joining: $e')),
      );
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Join Game')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _codeController,
              decoration: const InputDecoration(
                labelText: 'Game Code',
                border: OutlineInputBorder(),
              ),
              textCapitalization: TextCapitalization.characters,
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _nameController,
              decoration: const InputDecoration(
                labelText: 'Nickname',
                border: OutlineInputBorder(),
              ),
            ),
             const SizedBox(height: 16),
             TextField(
              controller: _valueController,
              decoration: const InputDecoration(
                labelText: 'Verification Value (from Host)',
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 32),
            _isLoading
                ? const CircularProgressIndicator()
                : ElevatedButton(
                    onPressed: _joinSession,
                    style: ElevatedButton.styleFrom(
                      minimumSize: const Size(double.infinity, 50),
                    ),
                    child: const Text('Join'),
                  ),
          ],
        ),
      ),
    );
  }
}
