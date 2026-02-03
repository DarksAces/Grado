package hilos2;

import java.util.Random;

import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.RecursiveTask;


public class ForkJoinSumDemo {


static class SumTask extends RecursiveTask<Long> {

private final long[] arr;

private final int start, end;

private final int threshold;


SumTask(long[] arr, int start, int end, int threshold) {

this.arr = arr;

this.start = start;

this.end = end;

this.threshold = threshold;

}


@Override

protected Long compute() {

int len = end - start;

if (len <= threshold) {

long sum = 0;

for (int i = start; i < end; i++) sum += arr[i];

return sum;

}

int mid = start + len / 2;

SumTask left = new SumTask(arr, start, mid, threshold);

SumTask right = new SumTask(arr, mid, end, threshold);


left.fork(); // ejecuta en paralelo (posible)

long rightSum = right.compute(); // computa en el hilo actual

long leftSum = left.join(); // espera y recoge

return leftSum + rightSum;

}

}


public static void main(String[] args) {

int n = 20_000_000;

long[] arr = new long[n];

Random rnd = new Random(42);

for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(10);


// Secuencial

long t1 = System.nanoTime();

long seq = 0;

for (long v : arr) seq += v;

long t2 = System.nanoTime();


// Paralelo Fork/Join

ForkJoinPool pool = ForkJoinPool.commonPool();

int threshold = 200_000; // ajustable

long t3 = System.nanoTime();

long par = pool.invoke(new SumTask(arr, 0, arr.length, threshold));

long t4 = System.nanoTime();


System.out.println("Suma secuencial = " + seq);

System.out.println("Suma paralela = " + par);

System.out.printf("Tiempo secuencial: %.2f ms%n", (t2 - t1) / 1e6);

System.out.printf("Tiempo paralelo: %.2f ms%n", (t4 - t3) / 1e6);

}

}