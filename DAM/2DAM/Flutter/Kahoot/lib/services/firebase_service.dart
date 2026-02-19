import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/foundation.dart';
import 'dart:math';

class FirebaseService {
  final FirebaseFirestore _firestore = FirebaseFirestore.instance;

  // --- Host Methods ---

  /// Creates a new game session and returns the session code.
  Future<String> createSession() async {
    String code = _generateRandomCode();
    // Ensure uniqueness (simple check, in real app loop until unique)
    DocumentSnapshot doc = await _firestore.collection('sessions').doc(code).get();
    if (doc.exists) {
      // Very unlikely with 6 chars, but recursive retry just in case
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

  /// Starts the game for the given session.
  Future<void> startGame(String code) async {
    await _firestore.collection('sessions').doc(code).update({
      'status': 'active',
      'currentQuestionIndex': 0,
    });
  }

  /// Streams the list of players for a session.
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
  
  /// Stream session data
  Stream<DocumentSnapshot<Map<String, dynamic>>> streamSession(String code) {
    return _firestore.collection('sessions').doc(code).snapshots();
  }

  // --- Client Methods ---

  /// Joins a session with a nickname and the verification value.
  Future<void> joinSession(String code, String nickname, int value) async {
    DocumentReference sessionRef = _firestore.collection('sessions').doc(code);
    DocumentSnapshot sessionSnapshot = await sessionRef.get();

    if (!sessionSnapshot.exists) {
      throw Exception('Session not found');
    }

    // Verify the random value
    int? serverValue = sessionSnapshot.get('randomValue');
    if (serverValue != value) {
       throw Exception('Incorrect value');
    }

    // Add player
    await sessionRef.collection('players').add({
      'name': nickname,
      'score': 0,
      'joinedAt': FieldValue.serverTimestamp(),
    });
  }

  // --- Utilities ---

  String _generateRandomCode() {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    Random rnd = Random();
    return String.fromCharCodes(Iterable.generate(
        6, (_) => chars.codeUnitAt(rnd.nextInt(chars.length))));
  }
  
  int _generateRandomValue() {
    Random rnd = Random();
    return rnd.nextInt(100); // 0 to 99
  }
}
