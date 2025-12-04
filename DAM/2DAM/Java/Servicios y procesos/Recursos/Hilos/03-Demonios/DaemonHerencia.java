public class DaemonHerencia {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("[MAIN] isDaemon = " + Thread.currentThread().isDaemon()); // false
        Thread noDaemon = new Thread(() -> {
            System.out.println("[NO-DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            // Hilo creado desde un hilo normal (no daemon)
            Thread hijo = new Thread(() -> {
                System.out.println("[HIJO DE NO-DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            });
            hijo.start();
            try {
                hijo.join();
            } catch (InterruptedException e) {
            }
        });
        Thread daemon = new Thread(() -> {
            System.out.println("[DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            // Hilo creado desde un hilo daemon
            Thread hijo = new Thread(() -> {
                System.out.println("[HIJO DE DAEMON] isDaemon = " + Thread.currentThread().isDaemon());
            });
            hijo.start();
            try {
                hijo.join();
            } catch (InterruptedException e) {
            }
        });
        daemon.setDaemon(true);
        noDaemon.start();
        daemon.start();
        noDaemon.join();
        daemon.join();
        System.out.println("[MAIN] fin");
    }
}
