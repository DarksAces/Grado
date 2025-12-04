public class DemoSleepYieldPriority {
    // ====== DEMO 1: sleep() ======
    static class SleepTask implements Runnable {
        private final String name;
        private final int delay;
        public SleepTask(String name, int delay) {
            this.name = name;
            this.delay = delay;
        }
        @Override
        public void run() {
            System.out.println("[" + name + "] empieza");
            for (int i = 1; i <= 5; i++) {
                System.out.println("[" + name + "] paso " + i);
                try {
                    Thread.sleep(delay); // se duerme 'delay' milisegundos
                } catch (InterruptedException e) {
                    System.out.println("[" + name + "] interrumpido");
                }
            }
            System.out.println("[" + name + "] termina");
        }
    }
    // ====== DEMO 2: yield() ======
    static class YieldTask implements Runnable {
        private final String name;
        private final boolean useYield;
        public YieldTask(String name, boolean useYield) {
            this.name = name;
            this.useYield = useYield;
        }
        @Override
        public void run() {
            System.out.println("{" + name + "} empieza");
            for (int i = 1; i <= 20; i++) {
                System.out.println("{" + name + "} i=" + i);
                // Hacemos algo de "trabajo" tonto
                for (int j = 0; j < 1_000_00; j++) {
                    // ocupamos CPU
                }
                if (useYield && i % 3 == 0) {
                    System.out.println("{" + name + "} hace yield()");
                    Thread.yield(); // sugiere ceder la CPU
                }
            }
            System.out.println("{" + name + "} termina");
        }
    }
    // ====== DEMO 3: setPriority() ======
    static class PriorityTask implements Runnable {
        private final String name;
        public PriorityTask(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            long sum = 0;
            for (int i = 0; i < 50_000_000; i++) {
                sum += i;
                if (i % 10_000_000 == 0) {
                    System.out.println("[" + name + "] i=" + i);
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("[" + name + "] termina en " + (end - start) + " ms (sum=" + sum + ")");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        // =============================
        // DEMO 1: sleep()
        // =============================
        System.out.println("======= DEMO SLEEP =======");
        Thread s1 = new Thread(new SleepTask("Lento", 400)); // duerme más
        Thread s2 = new Thread(new SleepTask("Rápido", 200)); // duerme menos
        s1.start();
        s2.start();
        s1.join();
        s2.join();
        // =============================
        // DEMO 2: yield()
        // =============================
        System.out.println("\n======= DEMO YIELD =======");
        Thread y1 = new Thread(new YieldTask("SinYield", false));
        Thread y2 = new Thread(new YieldTask("ConYield", true));
        y1.start();
        y2.start();
        y1.join();
        y2.join();
        // =============================
        // DEMO 3: setPriority()
        // =============================
        System.out.println("\n======= DEMO PRIORITY =======");
        Thread p1 = new Thread(new PriorityTask("Prioridad_Baja"));
        Thread p2 = new Thread(new PriorityTask("Prioridad_Media"));
        Thread p3 = new Thread(new PriorityTask("Prioridad_Alta"));
        // Prioridades (1 a 10)
        p1.setPriority(Thread.MIN_PRIORITY);   // 1
        p2.setPriority(Thread.NORM_PRIORITY);  // 5
        p3.setPriority(Thread.MAX_PRIORITY);   // 10
        System.out.println("Prioridad_Baja = " + p1.getPriority());
        System.out.println("Prioridad_Media = " + p2.getPriority());
        System.out.println("Prioridad_Alta = " + p3.getPriority());
        p1.start();
        p2.start();
        p3.start();
        p1.join();
        p2.join();
        p3.join();
        System.out.println("\nFin de todas las demos");
    }
}
