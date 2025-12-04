package test;

public class DaemonBasico {
    public static void main(String[] args) throws InterruptedException {
        Thread daemon = new Thread(() -> {
            int i = 0;
            while (true) {
                System.out.println("[DAEMON] trabajando... " + i++);
                try {
                    Thread.sleep(500); // medio segundo
                } catch (InterruptedException e) {
                    System.out.println("[DAEMON] interrumpido");
                    break;
                }
            }
        });
        // Lo marcamos como hilo demonio ANTES de arrancarlo
        daemon.setDaemon(true);
        System.out.println("¿daemon.isDaemon()? " + daemon.isDaemon()); // true
        daemon.start();
        // El hilo main sigue con su vida
        System.out.println("[MAIN] voy a dormir 2 segundos");
        Thread.sleep(2000);
        System.out.println("[MAIN] termino ahora. La JVM se apagará y matará al daemon.");
    }
}
