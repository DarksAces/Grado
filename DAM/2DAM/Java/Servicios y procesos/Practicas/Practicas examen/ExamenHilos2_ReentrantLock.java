package practicas_examen;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

/**
 * Ejercicio 9: ReentrantLock y tryLock.
 * 
 * Analiza el comportamiento del Hilo 2:
 * 1. ¿Cuánto tiempo espera el Hilo 2 antes de rendirse?
 * 2. ¿Entrará el Hilo 2 en el bloque 'if' o en el 'else'?
 * 3. ¿Por qué es importante el bloque finally?
 */
public class ExamenHilos2_ReentrantLock {
    public static void main(String[] args) {
        ReentrantLock cerrojo = new ReentrantLock();

        // Hilo 1: Bloquea el recurso durante 3 segundos
        new Thread(() -> {
            cerrojo.lock();
            try {
                System.out.println("Hilo 1: Tengo el cerrojo. Voy a tardar 3s...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            } finally {
                System.out.println("Hilo 1: Suelto el cerrojo.");
                cerrojo.unlock();
            }
        }).start();

        // Pausa pequeña para asegurar que Hilo 1 pille el cerrojo antes
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        // Hilo 2: Intenta entrar pero solo espera 1 segundo
        new Thread(() -> {
            System.out.println("Hilo 2: Intentando pillar el cerrojo (espero 1s max)...");
            try {
                if (cerrojo.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Hilo 2: ¡Lo conseguí! Trabajando...");
                    } finally {
                        cerrojo.unlock();
                    }
                } else {
                    System.out.println("Hilo 2: No puedo esperar más, me voy a hacer otra cosa.");
                }
            } catch (InterruptedException e) {
            }
        }).start();
    }
}
