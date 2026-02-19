package hilos2;

import java.util.concurrent.CompletableFuture;

import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    public static void main(String[] args) {

        CompletableFuture<Void> pipeline =

                descargarDatosAsync()

                        .thenApply(CompletableFutureDemo::procesarDatos) // transforma

                        .thenAccept(CompletableFutureDemo::mostrarResultado) // consume

                        .exceptionally(ex -> { // errores

                            System.out.println("Error en pipeline:  DGB" + ex);

                            return null;

                        });

        // En un programa real, el main no debería acabar antes.

        // Aquí hacemos join() solo para que se vea el resultado en consola.

        pipeline.join();

        System.out.println("Pipeline completado. DGB");

    }

    static CompletableFuture<String> descargarDatosAsync() {

        return CompletableFuture.supplyAsync(() -> {

            sleep(400);

            System.out.println("Descarga completada (" + Thread.currentThread().getName() + ")");

            return "datos_crudos: 1,2,3,4,5  DGB";

        });

    }

    static String procesarDatos(String raw) {

        sleep(300);

        System.out.println("Procesamiento completado (" + Thread.currentThread().getName() + ")");

        return raw.toUpperCase() + " | OK  DGB";

    }

    static void mostrarResultado(String processed) {

        sleep(100);

        System.out.println("Resultado (" + Thread.currentThread().getName() + "):  DGB" + processed);

    }

    static void sleep(long ms) {

        try {

            TimeUnit.MILLISECONDS.sleep(ms);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }

}