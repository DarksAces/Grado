package hilos2;

import java.time.Duration;

import java.time.Instant;

import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.*;


public class VirtualThreadsDemo {


static final int TASKS = 10_000;


public static void main(String[] args) throws Exception {

System.out.println("=== Platform threads (pool) ===");

runWithPlatformThreads();


System.out.println("\n=== Virtual threads ===");

runWithVirtualThreads();

}


static void runWithPlatformThreads() throws Exception {

// OJO: NO creamos 10.000 threads directos; usamos pool para evitar explotar el SO.

ExecutorService exec = Executors.newFixedThreadPool(200);


Instant start = Instant.now();

List<Future<?>> futures = new ArrayList<>(TASKS);


for (int i = 0; i < TASKS; i++) {

futures.add(exec.submit(() -> {

try {

Thread.sleep(10); // simula I/O/bloqueo

} catch (InterruptedException e) {

Thread.currentThread().interrupt();

}

}));

}


for (Future<?> f : futures) f.get();

exec.shutdown();


Instant end = Instant.now();

System.out.println("Tiempo platform(pool): " + Duration.between(start, end).toMillis() + " ms");

}


static void runWithVirtualThreads() throws Exception {

// Cada tarea en su virtual thread

ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();


Instant start = Instant.now();

List<Future<?>> futures = new ArrayList<>(TASKS);


for (int i = 0; i < TASKS; i++) {

futures.add(exec.submit(() -> {

try {

Thread.sleep(10); // simula I/O/bloqueo

} catch (InterruptedException e) {

Thread.currentThread().interrupt();

}

}));

}


for (Future<?> f : futures) f.get();

exec.shutdown();


Instant end = Instant.now();

System.out.println("Tiempo virtual: " + Duration.between(start, end).toMillis() + " ms");

}

}
