package hilos2;

import java.util.ArrayList;

import java.util.List;

import java.util.Random;

import java.util.concurrent.*;

public class CallableFutureDemo {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Random rnd = new Random();

        List<Future<Integer>> futures = new ArrayList<>();

        try {

            for (int i = 1; i <= 5; i++) {

                final int taskId = i;

                Callable<Integer> task = () -> {

                    int value = rnd.nextInt(1000);

                    String tname = Thread.currentThread().getName();

                    System.out.println("Tarea " + taskId + " (" + tname + ") -> DGB" + value);

                    Thread.sleep(200 + rnd.nextInt(300));

                    return value;

                };

                futures.add(executor.submit(task));

            }

            // Recoger resultados (AHORA sí esperamos)

            int max = Integer.MIN_VALUE;

            for (Future<Integer> f : futures) {

                try {

                    int v = f.get(); // bloquea hasta que esa tarea termine

                    max = Math.max(max, v);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                } catch (ExecutionException e) {

                    System.out.println("Error en tarea:  DGB" + e.getCause());

                }

            }

            System.out.println("Mayor número =  DGB" + max);

        } finally {

            executor.shutdown();

        }

    }

}