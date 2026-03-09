package practicas_examen;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ejercicio 10: Atomic vs Volatile.
 * 
 * Tenemos dos contadores compartidos por 10 hilos que lo incrementan 1000
 * veces.
 * 1. ¿Cuál de los contadores será siempre 10000?
 * 2. ¿Qué significa que una operación sea "atómica"?
 * 3. ¿Por qué 'volatile' no es suficiente para asegurar un incremento correcto?
 */
public class ExamenHilos2_Atomic {
    static volatile int contadorVolatile = 0;
    static AtomicInteger contadorAtomic = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Thread[] hilos = new Thread[10];

        for (int i = 0; i < 10; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    contadorVolatile++; // NO ES ATÓMICO (lee, suma, escribe)
                    contadorAtomic.incrementAndGet(); // ES ATÓMICO
                }
            });
            hilos[i].start();
        }

        for (Thread t : hilos)
            t.join();

        System.out.println("Resultado Volatile (esperado 10000): " + contadorVolatile);
        System.out.println("Resultado Atomic (esperado 10000): " + contadorAtomic.get());
    }
}
