package hilos2;

import java.util.concurrent.atomic.AtomicInteger;

public class VisibilityAtomicityDemo {

    // TEST 1: Visibilidad
    // EXPERIMENTO: Si borras 'volatile', el hilo "Waiter" podría entrar en un bucle
    // infinito
    // porque no "ve" que el hilo "Setter" cambió el valor en la memoria principal.
    static volatile boolean flag = false;

    // TEST 2: Atomicidad
    static int counter = 0;
    static AtomicInteger atomicCounter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        // --- SECCIÓN 1: PRUEBA DE VISIBILIDAD ---
        Thread waiter = new Thread(() -> {
            System.out.println("DGB Waiter: esperando flag=true...");
            while (!flag) {
                // El hilo se queda aquí. Sin volatile, lee de su propia caché local.
            }
            System.out.println("DGB Waiter: detectó flag=true");
        }, "Waiter");

        Thread setter = new Thread(() -> {
            try {
                Thread.sleep(300); // Pausa didáctica para que el Waiter empiece a esperar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            flag = true;
            System.out.println("DGB Setter: cambió flag=true");
        }, "Setter");

        waiter.start();
        setter.start();
        waiter.join();
        setter.join();

        System.out.println("--- Fin Test Visibilidad ---\n");

        // --- SECCIÓN 2: PRUEBA DE ATOMICIDAD ---
        // TEST 3: Carga de trabajo. Con 200,000 el error de "counter++" es muy
        // evidente.
        int iterations = 500_000;

        Runnable task = () -> {
            for (int i = 0; i < iterations; i++) {
                // ESTA LÍNEA ES EL PROBLEMA: No es atómica.
                // Lee, suma y escribe como pasos separados.
                counter++;

                // ESTA LÍNEA ES LA SOLUCIÓN: Usa hardware (CAS) para asegurar el cambio.
                atomicCounter.incrementAndGet();
            }
        };

        Thread t1 = new Thread(task, "Inc-1");
        Thread t2 = new Thread(task, "Inc-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // RESULTADOS FINALES
        System.out.println("DGB counter (NO atómico)      = " + counter);
        System.out.println("DGB atomicCounter (ATÓMICO)    = " + atomicCounter.get());
        System.out.println("DGB Esperado (ideal)           = " + (iterations * 2));

        if (counter != (iterations * 2)) {
            System.out.println(
                    ">>> ¡ERROR! Se perdieron " + ((iterations * 2) - counter) + " incrementos en el contador normal.");
        }
    }
}