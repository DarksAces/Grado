/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilos2;

/**
 *
 * @author danielgarbru
 */
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

    public static void main(String[] args) {

        // TEST 1: Tipo de Pool (Descomenta una opción)
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // ExecutorService executor = Executors.newCachedThreadPool();
        // ExecutorService executor = Executors.newSingleThreadExecutor();

        try {

            // TEST 3: Carga de trabajo (Cambia el 10)
            for (int i = 1; i <= 10; i++) {

                final int taskId = i;

                executor.submit(() -> {

                    String tname = Thread.currentThread().getName();

                    System.out.println("DGB Tarea " + taskId + " ejecutada por " + tname);

                    try {

                        Thread.sleep(200);

                    } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();

                        System.out.println("DGB Tarea " + taskId + " interrumpida");

                    }

                });

            }

        } finally {

            executor.shutdown(); // no acepta nuevas tareas

        }

        // Espera cierre limpio

        try {

            // TEST 2: Estrategia de Cierre (Prueba a bajar el tiempo o comentar el
            // shutdownNow)
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {

                executor.shutdownNow(); // fuerza

            }

        } catch (InterruptedException e) {

            executor.shutdownNow();

            Thread.currentThread().interrupt();

        }

        System.out.println("DGB Executor cerrado correctamente.");

    }

}