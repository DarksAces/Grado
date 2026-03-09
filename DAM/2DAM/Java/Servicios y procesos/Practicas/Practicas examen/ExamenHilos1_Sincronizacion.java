package practicas_examen;

/**
 * Ejercicio 1: Sincronización y Condiciones de Carrera.
 * 
 * Analiza el siguiente código y responde:
 * 1. ¿Cuál es el valor esperado de 'cuenta' al final?
 * 2. ¿Por qué el valor real suele ser diferente al esperado?
 * 3. ¿Cómo se solucionaría usando un bloque synchronized?
 */
public class ExamenHilos1_Sincronizacion {
    static class Contador {
        private int cuenta = 0;

        // Metodo que incrementa la cuenta
        public void incrementar() {
            cuenta++;
        }

        public int getCuenta() {
            return cuenta;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador();
        int numHilos = 5;
        int incrementosPorHilo = 2000;
        Thread[] hilos = new Thread[numHilos];

        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < incrementosPorHilo; j++) {
                    contador.incrementar();
                }
            });
            hilos[i].start();
        }

        // Esperar a que terminen todos
        for (Thread t : hilos) {
            t.join();
        }

        System.out.println("Resultado final: " + contador.getCuenta());
    }
}
