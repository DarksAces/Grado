package hilos2;

import java.util.concurrent.CompletableFuture;

import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    public static void main(String[] args) {

        CompletableFuture<Void> pipeline =

                descargarDatosAsync()

                        .thenApply(CompletableFutureDemo::procesarDatos) // transforma

                        // TEST 2: Encadenamiento extra (Descomenta para añadir un paso)
                        // .thenApply(data -> data + " | VALIDADO DGB")

                        .thenAccept(CompletableFutureDemo::mostrarResultado) // consume

                        .exceptionally(ex -> { // errores

                            System.out.println("DGB Error en pipeline: " + ex);

                            return null;

                        });

        // En un programa real, el main no debería acabar antes.

        // Aquí hacemos join() solo para que se vea el resultado en consola.

        pipeline.join();

        System.out.println("DGB Pipeline completado.");

    }

    static CompletableFuture<String> descargarDatosAsync() {

        return CompletableFuture.supplyAsync(() -> {

            // TEST 3: Retraso en descarga (Cambia los 400ms)
            sleep(400);
            // sleep(2000); // Descarga lenta DGB

            System.out.println("DGB Descarga completada (" + Thread.currentThread().getName() + ")");

            // TEST 1: Simular Error (Descomenta la línea de abajo)
            // if (true)
            // throw new RuntimeException("Fallo de red simulado! DGB");

            return "datos_crudos: 1,2,3,4,5  DGB";

        });

    }

    static String procesarDatos(String raw) {

        sleep(300);

        System.out.println("DGB Procesamiento completado (" + Thread.currentThread().getName() + ")");

        return raw.toUpperCase() + " | OK  DGB";

    }

    static void mostrarResultado(String processed) {

        sleep(100);

        System.out.println("DGB Resultado (" + Thread.currentThread().getName() + "): " + processed);

    }

    static void sleep(long ms) {

        try {

            TimeUnit.MILLISECONDS.sleep(ms);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

}