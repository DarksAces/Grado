package hilos2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danielgarbru
 */
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
public class LockBufferSimpleWithRelativeTime {
    static class BoundedBuffer {
        private final Queue<Integer> queue = new ArrayDeque<>();
        private final int capacity;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();
        // Tiempo de inicio del programa
        private final long startTime;
        public BoundedBuffer(int capacity, long startTime) {
            this.capacity = capacity;
            this.startTime = startTime;
        }
        private long elapsed() {
            return System.currentTimeMillis() - startTime;
        }
        public void put(int item) throws InterruptedException {
            lock.lock();
            try {
                System.out.println("[" + elapsed() + " ms] [Producer] intenta producir "
                        + item + " (buffer=" + queue.size() + ")");
                while (queue.size() == capacity) {
                    System.out.println("[" + elapsed() + " ms] [Producer] BLOQUEADO para item "
                            + item + " (buffer FULL)");
                    notFull.await();
                    System.out.println("[" + elapsed() + " ms] [Producer] DESPIERTA para item "
                            + item + " (buffer=" + queue.size() + ")");
                }
                queue.add(item);
                System.out.println("[" + elapsed() + " ms] [Producer] produjo "
                        + item + " (buffer=" + queue.size() + ")");
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
        public int take() throws InterruptedException {
            lock.lock();
            try {
                System.out.println("[" + elapsed() + " ms] [Consumer] intenta consumir "
                        + "(buffer=" + queue.size() + ")");
                while (queue.isEmpty()) {
                    System.out.println("[" + elapsed() + " ms] [Consumer] BLOQUEADO "
                            + "(buffer EMPTY)");
                    notEmpty.await();
                    System.out.println("[" + elapsed() + " ms] [Consumer] DESPIERTA "
                            + "(buffer=" + queue.size() + ")");
                }
                int item = queue.remove();
                System.out.println("[" + elapsed() + " ms] [Consumer] consumió "
                        + item + " (buffer=" + queue.size() + ")");
                notFull.signal();
                return item;
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        BoundedBuffer buffer = new BoundedBuffer(2, startTime);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    buffer.put(i);
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Producer");
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(50);
                for (int i = 1; i <= 3; i++) {
                    buffer.take();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Consumer");
        producer.start();
        consumer.start();
    }
}

