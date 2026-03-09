package practicas_examen;

/**
 * Ejercicio 7: Interrupción Correcta de Hilos.
 * 
 * Analiza cómo se detiene este hilo:
 * 1. ¿Por qué usamos isInterrupted() en el while?
 * 2. ¿Qué pasaría si quitamos el break en el catch de InterruptedException?
 * 3. ¿El mensaje "Hilo 1: Mi trabajo ha terminado limpiamente" se ejecutará?
 */
public class ExamenHilos1_Interrupcion {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                int i = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Hilo 1: Trabajando... " + i++);
                    Thread.sleep(100); // 100ms
                }
            } catch (InterruptedException e) {
                System.out.println("Hilo 1: Me han despertado mientras dormía para que me dentenga.");
            } finally {
                System.out.println("Hilo 1: Mi trabajo ha terminado limpiamente.");
            }
        });

        t1.start();

        try {
            Thread.sleep(500); // Main espera medio segundo
        } catch (InterruptedException e) {
        }

        System.out.println("Main: Voy a interrumpir a t1...");
        t1.interrupt(); // Mandamos señal de interrupción
    }
}
