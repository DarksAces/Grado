package practicas_examen;

/**
 * Ejercicio 4: Monitor y Sincronización (Productor-Consumidor).
 * 
 * Analiza el comportamiento de este buffer compartido:
 * 1. ¿Qué pasaría si quitamos la palabra 'synchronized' de los métodos?
 * 2. ¿Para qué sirven wait() y notify()?
 * 3. ¿Qué hilo se quedaría bloqueado si el consumidor es más rápido que el
 * productor?
 */
public class ExamenHilos1_MonitorBuffer {
    static class Almacen {
        private String producto = null;

        public synchronized void producir(String p) throws InterruptedException {
            while (producto != null) {
                wait(); // Si hay producto, espera a que se consuma
            }
            producto = p;
            System.out.println("Producido: " + p);
            notify(); // Avisa que ya hay algo
        }

        public synchronized String consumir() throws InterruptedException {
            while (producto == null) {
                wait(); // Si está vacío, espera a que produzcan
            }
            String p = producto;
            producto = null;
            System.out.println("Consumido: " + p);
            notify(); // Avisa que hay espacio
            return p;
        }
    }

    public static void main(String[] args) {
        Almacen almacen = new Almacen();

        Thread p = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    almacen.producir("Item-" + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
            }
        });

        Thread c = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    almacen.consumir();
                }
            } catch (InterruptedException e) {
            }
        });

        p.start();
        c.start();
    }
}
