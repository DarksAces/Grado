package practicas_examen;

import java.util.concurrent.*;

/**
 * Ejercicio 3: ThreadPool y Callable.
 * 
 * Este código usa un pool de hilos para calcular el cuadrado de varios números.
 * Responde:
 * 1. ¿Cuántos hilos se usan simultáneamente?
 * 2. ¿Qué hace el método get() de Future?
 * 3. ¿En qué orden se imprimirán los resultados de los cálculos?
 */
public class ExamenHilos2_ThreadPool {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Future<Integer> f1 = executor.submit(() -> {
                Thread.sleep(1500);
                System.out.println("Cálculo 1 completado");
                return 10 * 10;
            });

            Future<Integer> f2 = executor.submit(() -> {
                Thread.sleep(500);
                System.out.println("Cálculo 2 completado");
                return 20 * 20;
            });

            System.out.println("Esperando resultados...");

            // ¿Qué pasa aquí?
            System.out.println("Resultado 1: " + f1.get());
            System.out.println("Resultado 2: " + f2.get());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        System.out.println("Programa finalizado");
    }
}
