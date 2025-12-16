package test;

public class MainRunnable {

    public static void main(String[] args) {

        Cliente cliente1 = new Cliente("Cliente 1", new int[] { 2, 2, 1, 5, 2, 3 });
        Cliente cliente2 = new Cliente("Cliente 2", new int[] { 1, 3, 5, 1, 1 });

        // Tiempo inicial de referencia
        long initialTime = System.currentTimeMillis();

        CajeraRunnable cajera1 = new CajeraRunnable("Cajera 1", cliente1, initialTime);
        CajeraRunnable cajera2 = new CajeraRunnable("Cajera 2", cliente2, initialTime);

        Thread t1 = new Thread(cajera1);
        Thread t2 = new Thread(cajera2);

        t1.start();
        t2.start();
    }
}
