package practicas_examen;

/**
 * Ejercicio 2: Control de flujo con Sleep y Join.
 * 
 * Analiza el orden de los mensajes en consola.
 * ¿Qué mensaje aparecerá siempre al final?
 * ¿Es posible que "Hilo 1 terminado" salga antes que "Iniciando Hilo 2"?
 */
public class ExamenHilos1_JoinSleep {
    public static void main(String[] args) {
        Thread h1 = new Thread(() -> {
            try {
                System.out.println("Hilo 1: Trabajando...");
                Thread.sleep(1000);
                System.out.println("Hilo 1: Terminado");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread h2 = new Thread(() -> {
            try {
                h1.join(); // h2 espera a h1
                System.out.println("Hilo 2: Trabajando tras H1...");
                Thread.sleep(500);
                System.out.println("Hilo 2: Terminado");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Main: Iniciando hilos");
        h2.start();
        h1.start();

        try {
            h2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main: Fin del programa");
    }
}
