public class Ejercicio1 {
    private static final Object O = new Object();
    private static int v = 1;

    static class T extends Thread {
        private final int a;
        private final String b;
        private final int c;

        T(int a, String b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public void run() {
            try {
                synchronized (O) {
                    while (v != a) {
                        O.wait();
                    }
                    System.out.println(b + " (empieza)  [" + getName() + "]");
                }
                Thread.sleep(c);
                synchronized (O) {
                    System.out.println(b + " (termina)   [" + getName() + "]");
                    v++;
                    O.notifyAll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Inicio EJ1: ===");
        Thread h1 = new T(1, "AAAA", 250);
        h1.setName("HILO-AAAA");
        Thread h2 = new T(3, "ZZZZ", 180);
        h2.setName("HILO-ZZZZ");
        Thread h3 = new T(2, "EEEE", 220);
        h2.setName("HILO-ZZZZ");
        Thread h4 = new T(4, "TTTT", 120);
        h4.setName("HILO-TTTT");
        h3.start();
        h1.start();
        h4.start();
        h2.start();
        h1.join();
        h2.join();
        h3.join();
        h4.join();
        System.out.println("=== Fin EJ1 ===");
    }
}
