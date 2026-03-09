import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import '../services/firebase_service.dart';

class GameScreen extends StatefulWidget {
  final String sessionCode;
  final bool isHost;
  final String? nickname;

  const GameScreen({super.key, required this.sessionCode, required this.isHost, this.nickname});

  @override
  State<GameScreen> createState() => _GameScreenState();
}

class _GameScreenState extends State<GameScreen> {
  final FirebaseService _firebaseService = FirebaseService();
  List<Map<String, dynamic>> _questions = [];
  bool _isLoadingQuestions = true;
  bool _hasAnswered = false;
  String? _feedback;
  int _lastIndex = -1;

  @override
  void initState() {
    super.initState();
    _loadQuestions();
  }

  Future<void> _loadQuestions() async {
    try {
      final qs = await _firebaseService.getQuestions();
      setState(() {
        _questions = qs;
        _isLoadingQuestions = false;
      });
    } catch (e) {
      setState(() => _isLoadingQuestions = false);
    }
  }

  void _nextQuestion(int currentIndex) async {
    if (currentIndex + 1 < _questions.length) {
      await _firebaseService.updateQuestionIndex(widget.sessionCode, currentIndex + 1);
    } else {
      await _firebaseService.endGame(widget.sessionCode);
    }
  }

  void _submitAnswer(int questionIndex, int selectedIndex) async {
    if (_hasAnswered) return;
    
    final correctAnswer = _questions[questionIndex]['answerIndex'];
    final bool isCorrect = selectedIndex == correctAnswer;
    
    setState(() {
      _hasAnswered = true;
      _feedback = isCorrect ? '¡Correcto!' : 'Incorrecto...';
    });

    await _firebaseService.submitAnswer(widget.sessionCode, widget.nickname!, isCorrect);
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoadingQuestions) {
      return const Scaffold(body: Center(child: CircularProgressIndicator()));
    }

    if (_questions.isEmpty) {
      return Scaffold(
        appBar: AppBar(title: const Text('Error')),
        body: const Center(child: Text('No questions available. Add some in the Question Creator.')),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.isHost ? 'Host: ${widget.sessionCode}' : 'Jugador: ${widget.nickname}'),
        backgroundColor: widget.isHost ? Colors.blue : Colors.green,
      ),
      body: StreamBuilder<DocumentSnapshot<Map<String, dynamic>>>(
        stream: _firebaseService.streamSession(widget.sessionCode),
        builder: (context, snapshot) {
          if (!snapshot.hasData) return const Center(child: CircularProgressIndicator());
          
          final data = snapshot.data!.data();
          if (data == null) return const Center(child: Text('Session ended'));
          
          final String status = data['status'] ?? 'waiting';
          final int currentIndex = data['currentQuestionIndex'];

          // Restablecer el estado de la respuesta local si el índice de la pregunta cambia
          if (status == 'active' && currentIndex != _lastIndex) {
            WidgetsBinding.instance.addPostFrameCallback((_) {
              if (mounted) {
                setState(() {
                  _hasAnswered = false;
                  _feedback = null;
                  _lastIndex = currentIndex;
                });
              }
            });
          }

          if (status == 'waiting') {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                   const CircularProgressIndicator(),
                   const SizedBox(height: 20),
                   const Text('Esperando a que el host inicie...'),
                   Text('Código: ${widget.sessionCode}', style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
                ],
              ),
            );
          }

          if (status == 'finished') {
            return _buildLeaderboard();
          }

          if (currentIndex < 0 || currentIndex >= _questions.length) {
             return const Center(child: Text('Iniciando partida...'));
          }

          final question = _questions[currentIndex];
          
          if (widget.isHost) {
            return _buildHostView(question, currentIndex);
          } else {
            return _buildPlayerView(question, currentIndex);
          }
        },
      ),
    );
  }

  Widget _buildHostView(Map<String, dynamic> question, int index) {
    return Padding(
      padding: const EdgeInsets.all(24.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text('Pregunta ${index + 1} de ${_questions.length}', style: const TextStyle(fontSize: 18)),
          const SizedBox(height: 20),
          Text(question['question'], style: const TextStyle(fontSize: 28, fontWeight: FontWeight.bold), textAlign: TextAlign.center),
          const SizedBox(height: 40),
          Expanded(
            child: GridView.count(
              crossAxisCount: 2,
              mainAxisSpacing: 10,
              crossAxisSpacing: 10,
              children: List.generate(4, (i) {
                return Card(
                  color: _getOptionColor(i),
                  child: Center(
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Text(question['options'][i], style: const TextStyle(color: Colors.white, fontSize: 18), textAlign: TextAlign.center),
                    ),
                  ),
                );
              }),
            ),
          ),
          ElevatedButton(
            onPressed: () => _nextQuestion(index),
            style: ElevatedButton.styleFrom(minimumSize: const Size(double.infinity, 60)),
            child: Text(index + 1 < _questions.length ? 'Siguiente Pregunta' : 'Finalizar Juego'),
          ),
        ],
      ),
    );
  }

  Widget _buildPlayerView(Map<String, dynamic> question, int index) {
    // Restablecer el estado de respondido cuando la pregunta cambia
    // Esto es un poco complicado con StreamBuilder, por lo que rastreamos el índice actual
    // Simplificado: el reinicio del estado local se maneja en la lógica de construcción o similar a useEffect
    
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        children: [
          Text('Pregunta ${index + 1}', style: const TextStyle(fontSize: 20)),
          const SizedBox(height: 20),
          if (!_hasAnswered) ...[
             const Text('¡Elige la respuesta correcta!', style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold)),
             const SizedBox(height: 40),
             Expanded(
               child: GridView.count(
                 crossAxisCount: 2,
                 mainAxisSpacing: 10,
                 crossAxisSpacing: 10,
                 children: List.generate(4, (i) {
                   return ElevatedButton(
                     style: ElevatedButton.styleFrom(backgroundColor: _getOptionColor(i)),
                     onPressed: () => _submitAnswer(index, i),
                     child: Text(question['options'][i], style: const TextStyle(color: Colors.white, fontSize: 16), textAlign: TextAlign.center),
                   );
                 }),
               ),
             ),
          ] else ...[
            Center(
              child: Column(
                children: [
                  Icon(_feedback == '¡Correcto!' ? Icons.check_circle : Icons.error, size: 100, color: _feedback == '¡Correcto!' ? Colors.green : Colors.red),
                  Text(_feedback!, style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold, color: _feedback == '¡Correcto!' ? Colors.green : Colors.red)),
                  const SizedBox(height: 20),
                  const Text('Esperando a la siguiente pregunta...', style: TextStyle(fontSize: 18)),
                ],
              ),
            ),
          ],
        ],
      ),
    );
  }

  Widget _buildLeaderboard() {
    return StreamBuilder<List<Map<String, dynamic>>>(
      stream: _firebaseService.streamPlayers(widget.sessionCode),
      builder: (context, snapshot) {
        if (!snapshot.hasData) return const Center(child: CircularProgressIndicator());
        final players = snapshot.data!;
        players.sort((a, b) => (b['score'] ?? 0).compareTo(a['score'] ?? 0));

        return Column(
          children: [
            const Padding(
              padding: EdgeInsets.all(32.0),
              child: Text('Podio Final', style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold)),
            ),
            Expanded(
              child: ListView.builder(
                itemCount: players.length,
                itemBuilder: (context, index) {
                  return ListTile(
                    leading: CircleAvatar(child: Text('${index + 1}')),
                    title: Text(players[index]['name']),
                    trailing: Text('${players[index]['score'] ?? 0} pts', style: const TextStyle(fontWeight: FontWeight.bold)),
                  );
                },
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: ElevatedButton(
                onPressed: () => Navigator.of(context).popUntil((route) => route.isFirst),
                child: const Text('Salir al Inicio'),
              ),
            )
          ],
        );
      },
    );
  }

  Color _getOptionColor(int index) {
    switch (index) {
      case 0: return Colors.red;
      case 1: return Colors.blue;
      case 2: return Colors.amber.shade700;
      case 3: return Colors.green;
      default: return Colors.grey;
    }
  }
}
