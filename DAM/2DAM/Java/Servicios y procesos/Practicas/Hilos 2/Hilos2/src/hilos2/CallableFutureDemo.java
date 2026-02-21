package hilos2;

import java.util.ArrayList;

import java.util.List;

import java.util.Random;

import java.util.concurrent.*;

public class CallableFutureDemo {

    public static void main(String[] args) {

        // TEST 1: Variar el tamaño del Pool (Descomenta una opción)
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // ExecutorService executor = Executors.newFixedThreadPool(1);
        // ExecutorService executor = Executors.newCachedThreadPool();

        Random rnd = new Random();

        List<Future<Integer>> futures = new ArrayList<>();

        try {

            for (int i = 1; i <= 5; i++) {

                final int taskId = i;

                Callable<Integer> task = () -> {

                    int value = rnd.nextInt(1000);

                    String tname = Thread.currentThread().getName();

                    System.out.println("DGB Tarea " + taskId + " (" + tname + ") -> " + value);

                    Thread.sleep(200 + rnd.nextInt(300));

                    return value;

                };

                futures.add(executor.submit(task));

            }

            // TEST 3: Cancelación Selectiva (Descomenta para probar)
            futures.get(2).cancel(true);

            // Recoger resultados (AHORA sí esperamos)

            int max = Integer.MIN_VALUE;

            for (Future<Integer> f : futures) {

                try {

                    // TEST 2: Timeout (Intercambia estas líneas para probar)
                    // int v = f.get(); // bloquea hasta que esa tarea termine
                    int v = f.get(400, TimeUnit.MILLISECONDS);

                    max = Math.max(max, v);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                } catch (ExecutionException e) {

                    // System.out.println("DGB Error en tarea: " + e.getCause());

                } catch (TimeoutException e) {
                    // Solo necesario para TEST 2 (Descomenta si usas f.get con tiempo)
                    System.out.println("DGB La tarea tardó demasiado!");

                } catch (CancellationException e) {
                    // Solo necesario para TEST 3
                    System.out.println("DGB Tarea cancelada!");
                }

            }

            System.out.println("DGB Mayor número = " + max);

        } finally {

            executor.shutdown();

        }

    }

}