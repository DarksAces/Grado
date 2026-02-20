package hilos2;

import java.util.concurrent.atomic.AtomicInteger;

public class VisibilityAtomicityDemo {

    // TEST 1: Visibilidad (Cambia volatile por nada)
    // static boolean flag = false;
    static volatile boolean flag = false;

    static int counter = 0; // NO atómico

    // TEST 2: Atomicidad (Cambia entre counter++ y atomicCounter en el bucle abajo)
    static AtomicInteger atomicCounter = new AtomicInteger(0); // ATÓMICO

    public static void main(String[] args) throws InterruptedException {

        Thread waiter = new Thread(() -> {

            System.out.println("DGB Waiter: esperando flag=true...");

            while (!flag) {

                // busy-wait: en producción se evitaría; aquí es didáctico

            }

            System.out.println("DGB Waiter: detectó flag=true");

        }, "Waiter");

        Thread setter = new Thread(() -> {

            try {

                Thread.sleep(300);

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

        // EXTRA: contador no atómico vs AtomicInteger

        // TEST 3: Carga de trabajo (Cambia el 100_000)
        int iterations = 100_000;

        Thread t1 = new Thread(() -> {

            for (int i = 0; i < iterations; i++) {

                // TEST 2: Comenta uno u otro para ver el efecto (Uno falla, el otro no)
                counter++; // NO atómico
                // atomicCounter.incrementAndGet(); // ATÓMICO

            }

        }, "Inc-1");

        Thread t2 = new Thread(() -> {

            for (int i = 0; i < iterations; i++) {

                counter++; // NO atómico
                // atomicCounter.incrementAndGet(); // ATÓMICO

            }

        }, "Inc-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("DGB counter (NO atómico)       = " + counter);

        System.out.println("DGB atomicCounter (ATÓMICO)    = " + atomicCounter.get());

        System.out.println("DGB Esperado (ideal)           = " + (iterations * 2));

    }

}
