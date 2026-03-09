package practicas_examen;

import java.util.concurrent.CompletableFuture;

/**
 * Ejercicio 5: Asincronía con CompletableFuture.
 * 
 * Analiza el flujo de ejecución:
 * 1. ¿Qué mensajes se imprimen si el metodo "tareaLenta" lanza una excepción?
 * 2. ¿Qué significa "thenApply" y "thenAccept"?
 * 3. ¿Por qué necesitamos el join() al final?
 */
public class ExamenHilos2_CompletableFuture {
    public static void main(String[] args) {
        System.out.println("Main: Inicio");

        CompletableFuture<Void> pipeline = CompletableFuture.supplyAsync(() -> {
            System.out.println("Hilo: Ejecutando tarea...");
            esperar(1000);
            return "Datos Procesados";
        })
                .thenApply(resultado -> {
                    System.out.println("Hilo: Transformando: " + resultado);
                    return resultado.length();
                })
                .thenAccept(longitud -> {
                    System.out.println("Hilo: La longitud final es: " + longitud);
                })
                .exceptionally(ex -> {
                    System.err.println("Error detectado: " + ex.getMessage());
                    return null;
                });

        System.out.println("Main: Sigo trabajando mientras el hilo hace lo suyo...");

        // Esperamos a que termine para ver los prints antes de que muera el main
        pipeline.join();

        System.out.println("Main: Fin");
    }

    private static void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
