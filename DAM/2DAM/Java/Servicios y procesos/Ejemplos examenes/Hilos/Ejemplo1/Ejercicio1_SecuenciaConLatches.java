import java.util.concurrent.CountDownLatch;
public class Ejercicio1_SecuenciaConLatches {
    static class Paso implements Runnable {
        private final String nombre;
        private final int delayMs;
        private final CountDownLatch esperarA;
        private final CountDownLatch liberarA;
        public Paso(String nombre, int delayMs, CountDownLatch esperarA, CountDownLatch liberarA) {
            this.nombre = nombre;
            this.delayMs = delayMs;
            this.esperarA = esperarA;
            this.liberarA = liberarA;
        }
        @Override
        public void run() {
            try {
                if (esperarA != null) {
                    esperarA.await();
                }
                System.out.println("[" + Thread.currentThread().getName() + "] " + nombre + " (empieza)");
                Thread.sleep(delayMs);
                System.out.println("[" + Thread.currentThread().getName() + "] " + nombre + " (termina)");
            } catch (InterruptedException e) {
                System.out.println("[" + Thread.currentThread().getName() + "] " + nombre + " interrumpido");
                Thread.currentThread().interrupt();
            } finally {
                if (liberarA != null) {
                    liberarA.countDown();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== EJ1: Secuencia determinista con CountDownLatch ===");
        CountDownLatch prepararHecho = new CountDownLatch(1);
        CountDownLatch cocinarHecho = new CountDownLatch(1);
        CountDownLatch emplatarHecho = new CountDownLatch(1);
        Thread t1 = new Thread(new Paso("1) Preparar ingredientes", 450, null, prepararHecho), "HILO-A");
        Thread t2 = new Thread(new Paso("2) Cocinar", 450, prepararHecho, cocinarHecho), "HILO-B");
        Thread t3 = new Thread(new Paso("3) Servir", 150, emplatarHecho, null), "HILO-D");
        Thread t4 = new Thread(new Paso("4) Emplatar", 350, cocinarHecho, emplatarHecho), "HILO-C");
        t2.start();
        t3.start();
        t1.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        System.out.println("=== Fin EJ1 ===");
    }
}
