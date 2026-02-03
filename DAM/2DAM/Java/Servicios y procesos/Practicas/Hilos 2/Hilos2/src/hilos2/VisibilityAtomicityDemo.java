package hilos2;

import java.util.concurrent.atomic.AtomicInteger;


public class VisibilityAtomicityDemo {


// Cambia entre estas dos líneas para probar con y sin volatile:

// static boolean flag = false;

static volatile boolean flag = false;


static int counter = 0; // NO atómico

static AtomicInteger atomicCounter = new AtomicInteger(0); // ATÓMICO


public static void main(String[] args) throws InterruptedException {


Thread waiter = new Thread(() -> {

System.out.println("Waiter: esperando flag=true...");

while (!flag) {

// busy-wait: en producción se evitaría; aquí es didáctico

}

System.out.println("Waiter: detectó flag=true");

}, "Waiter");


Thread setter = new Thread(() -> {

try {

Thread.sleep(300);

} catch (InterruptedException e) {

Thread.currentThread().interrupt();

}

flag = true;

System.out.println("Setter: cambió flag=true");

}, "Setter");


waiter.start();

setter.start();


waiter.join();

setter.join();


// EXTRA: contador no atómico vs AtomicInteger

Thread t1 = new Thread(() -> {

for (int i = 0; i < 100_000; i++) {

counter++; // race condition

atomicCounter.incrementAndGet();

}

}, "Inc-1");


Thread t2 = new Thread(() -> {

for (int i = 0; i < 100_000; i++) {

counter++; // race condition

atomicCounter.incrementAndGet();

}

}, "Inc-2");


t1.start(); t2.start();

t1.join(); t2.join();


System.out.println("counter (NO atómico) = " + counter);

System.out.println("atomicCounter (ATÓMICO) = " + atomicCounter.get());

System.out.println("Esperado (ideal) = 200000");

}

}