package practicas_examen;

import java.util.concurrent.Semaphore;

/**
 * Ejercicio 8: Semáforos (Control de Acceso).
 * 
 * Tenemos 2 plazas de aparcamiento para 4 coches.
 * 1. ¿Cuántos coches estarán "Aparcando" al mismo tiempo como máximo?
 * 2. ¿Qué ocurre cuando un coche hace release()?
 * 3. ¿El orden de salida de los coches será siempre 1, 2, 3, 4?
 */
public class ExamenHilos2_Semaforo {
    public static void main(String[] args) {
        // Solo 2 permisos disponibles (2 plazas)
        Semaphore plazas = new Semaphore(2);

        for (int i = 1; i <= 4; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.println("Coche " + id + ": Esperando plaza...");
                    plazas.acquire(); // Intentar pillar plaza

                    System.out.println("Coche " + id + ": ¡APARCADO!");
                    Thread.sleep(2000); // Se queda aparcado 2 segundos

                    System.out.println("Coche " + id + ": Saliendo del parking...");
                    plazas.release(); // Libera la plaza

                } catch (InterruptedException e) {
                }
            }).start();
        }
    }
}
