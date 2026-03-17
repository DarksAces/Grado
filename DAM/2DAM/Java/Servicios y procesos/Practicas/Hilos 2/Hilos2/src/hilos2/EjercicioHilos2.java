package hilos2;

class Contador {
    private int cuenta = 0;

    public void incrementar() {
        cuenta++;
    }

    public int getCuenta() {
        return cuenta;
    }
}

public class EjercicioHilos2 {
    public static void main(String[] args) throws InterruptedException {
        Contador contador = new Contador();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementar();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementar();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Nota: El resultado puede variar, ¿qué de volvería si NO fuera hilo-seguro?
        // O mejor, ¿qué devuelve este código específico tal cual está?
        System.out.println("Cuenta final: " + contador.getCuenta());
    }
}
