import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/foundation.dart';
import 'dart:math';

class FirebaseService {
  final FirebaseFirestore _firestore = FirebaseFirestore.instance;

  // --- Métodos del Host ---

  /// Crea una nueva sesión de juego y devuelve el código de la sesión.
  Future<String> createSession() async {
    String code = _generateRandomCode();
    // Asegurar unicidad (comprobación simple, en una app real repetir hasta que sea único)
    DocumentSnapshot doc = await _firestore.collection('sessions').doc(code).get();
    if (doc.exists) {
      // Muy poco probable con 6 caracteres, pero reintento recursivo por si acaso
      return createSession();
    }

    await _firestore.collection('sessions').doc(code).set({
      'status': 'waiting',
      'currentQuestionIndex': -1,
      'createdAt': FieldValue.serverTimestamp(),
      'randomValue': _generateRandomValue(), // Valor aleatorio que mostrará el servidor
    });

    return code;
  }

  /// Inicia el juego para la sesión dada.
  Future<void> startGame(String code) async {
    await _firestore.collection('sessions').doc(code).update({
      'status': 'active',
      'currentQuestionIndex': 0,
      'startTime': FieldValue.serverTimestamp(),
    });
  }

  /// Actualiza el índice de la pregunta actual en la sesión.
  Future<void> updateQuestionIndex(String code, int index) async {
    await _firestore.collection('sessions').doc(code).update({
      'currentQuestionIndex': index,
    });
  }

  /// Finaliza la sesión de juego.
  Future<void> endGame(String code) async {
    await _firestore.collection('sessions').doc(code).update({
      'status': 'finished',
    });
  }

  /// Obtiene un flujo (stream) de la lista de jugadores de una sesión.
  Stream<List<Map<String, dynamic>>> streamPlayers(String code) {
    return _firestore
        .collection('sessions')
        .doc(code)
        .collection('players')
        .snapshots()
        .map((snapshot) {
      return snapshot.docs.map((doc) => doc.data()).toList();
    });
  }
  
  /// Obtiene un flujo (stream) de los datos de la sesión.
  Stream<DocumentSnapshot<Map<String, dynamic>>> streamSession(String code) {
    return _firestore.collection('sessions').doc(code).snapshots();
  }

  // --- Métodos del Cliente ---

  /// Se une a una sesión con un apodo y el valor de verificación.
  Future<void> joinSession(String code, String nickname, int value) async {
    DocumentReference sessionRef = _firestore.collection('sessions').doc(code);
    DocumentSnapshot sessionSnapshot = await sessionRef.get();

    if (!sessionSnapshot.exists) {
      throw Exception('Session not found');
    }

    // Verificar el valor aleatorio
    int? serverValue = sessionSnapshot.get('randomValue');
    if (serverValue != value) {
       throw Exception('Incorrect value');
    }

    // Añadir jugador
    await sessionRef.collection('players').add({
      'name': nickname,
      'score': 0,
      'joinedAt': FieldValue.serverTimestamp(),
    });
  }

  /// Añade una nueva pregunta a la colección global de preguntas.
  Future<void> addQuestion(String question, List<String> options, int answerIndex) async {
    await _firestore.collection('questions').add({
      'question': question,
      'options': options,
      'answerIndex': answerIndex,
      'createdAt': FieldValue.serverTimestamp(),
    });
  }

  /// Obtiene todas las preguntas de la base de datos.
  Future<List<Map<String, dynamic>>> getQuestions() async {
    QuerySnapshot snapshot = await _firestore.collection('questions').orderBy('createdAt').get();
    return snapshot.docs.map((doc) {
      var data = doc.data() as Map<String, dynamic>;
      data['id'] = doc.id;
      return data;
    }).toList();
  }

  /// Envía una respuesta para un jugador en una sesión.
  Future<void> submitAnswer(String sessionCode, String playerName, bool isCorrect) async {
    var playersRef = _firestore.collection('sessions').doc(sessionCode).collection('players');
    var playerQuery = await playersRef.where('name', isEqualTo: playerName).get();
    
    if (playerQuery.docs.isNotEmpty) {
      var playerDoc = playerQuery.docs.first;
      if (isCorrect) {
        int currentScore = playerDoc.get('score') ?? 0;
        await playerDoc.reference.update({
          'score': currentScore + 1000,
        });
      }
    }
  }

  // --- Utilidades ---

  String _generateRandomCode() {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    Random rnd = Random();
    return String.fromCharCodes(Iterable.generate(
        6, (_) => chars.codeUnitAt(rnd.nextInt(chars.length))));
  }
  
  int _generateRandomValue() {
    Random rnd = Random();
    return rnd.nextInt(100); // 0 a 99
  }
}
