public class DeadlockDemo {


private static final Object LOCK_A = new Object();

private static final Object LOCK_B = new Object();


public static void main(String[] args) throws InterruptedException {


System.out.println("=== Parte 1: Provocar deadlock ===");

Thread t1 = new Thread(() -> lockInOrder("T1", LOCK_A, LOCK_B), "T1");

Thread t2 = new Thread(() -> lockInOrder("T2", LOCK_B, LOCK_A), "T2"); // orden inverso


t1.start();

t2.start();


// Esperamos un poco para "dar tiempo" a que ocurra el deadlock

Thread.sleep(800);


System.out.println("\nSi el programa se queda bloqueado aquí, es el deadlock.");

System.out.println("Puedes parar y ejecutar solo la parte 2 si lo prefieres.\n");


// === Parte 2: Corregido ===

System.out.println("=== Parte 2: Corregir con orden global ===");

Thread c1 = new Thread(() -> lockGlobalOrder("C1"), "C1");

Thread c2 = new Thread(() -> lockGlobalOrder("C2"), "C2");


c1.start();

c2.start();


c1.join();

c2.join();


System.out.println("Corrección completada (sin deadlock).");

}


static void lockInOrder(String name, Object first, Object second) {

synchronized (first) {

System.out.println(name + " adquirió FIRST");

sleep(200);

synchronized (second) {

System.out.println(name + " adquirió SECOND");

}

}

}


// Orden global: siempre LOCK_A luego LOCK_B

static void lockGlobalOrder(String name) {

synchronized (LOCK_A) {

System.out.println(name + " adquirió A");

sleep(100);

synchronized (LOCK_B) {

System.out.println(name + " adquirió B");

}

}

}


static void sleep(long ms) {

try { Thread.sleep(ms); }

catch (InterruptedException e) { Thread.currentThread().interrupt(); }

}

}