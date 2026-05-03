package tests;

import jbsae.struct.Queue;

import java.util.*;

public class QueueBenchmark{
    private static final int WARMUP_ROUNDS = 5;
    private static final int MEASUREMENT_ROUNDS = 10;
    private static final int N = 5_000_000; // 5 Million items for heavy stress testing

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("     Queue vs ArrayDeque Benchmarks      ");
        System.out.println("=========================================\n");

        System.out.println("Warming up the JVM (JIT Compilation)...");
        for(int i = 0;i < WARMUP_ROUNDS;i++){
            benchmarkAddLast(100_000, false);
            benchmarkPopFirst(100_000, false);
        }
        System.out.println("Warmup complete. Starting measurements.\n");

        System.out.println("--- Insertion (Includes Resizing) ---");
        benchmarkAddLast(N, true);
        benchmarkAddFirst(N, true);

        System.out.println("\n--- Removals ---");
        benchmarkPopFirst(N, true);

        System.out.println("\n--- Iteration ---");
        benchmarkIteration(N, true);
    }

    private static void benchmarkAddLast(int count, boolean print){
        long customTotal = 0;
        long standardTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            // Measure Custom Queue (Enqueue)
            Queue<Integer> customQueue = new Queue<>(16);
            long start = System.nanoTime();
            for(int i = 0;i < count;i++) customQueue.addLast(i);
            customTotal += (System.nanoTime() - start);

            // Measure ArrayDeque (Enqueue)
            Deque<Integer> standardQueue = new ArrayDeque<>(16);
            start = System.nanoTime();
            for(int i = 0;i < count;i++) standardQueue.addLast(i);
            standardTotal += (System.nanoTime() - start);
        }

        printResult("Add Last (Standard FIFO Enqueue)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkAddFirst(int count, boolean print){
        long customTotal = 0;
        long standardTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            // Measure Custom Queue
            Queue<Integer> customQueue = new Queue<>(16);
            long start = System.nanoTime();
            for(int i = 0;i < count;i++) customQueue.addFirst(i);
            customTotal += (System.nanoTime() - start);

            // Measure ArrayDeque
            Deque<Integer> standardQueue = new ArrayDeque<>(16);
            start = System.nanoTime();
            for(int i = 0;i < count;i++) standardQueue.addFirst(i);
            standardTotal += (System.nanoTime() - start);
        }

        printResult("Add First (Stack Push)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkPopFirst(int count, boolean print){
        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0; // Prevent dead code elimination

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            // Setup
            Queue<Integer> customQueue = new Queue<>(count);
            Deque<Integer> standardQueue = new ArrayDeque<>(count);
            for(int i = 0;i < count;i++){
                customQueue.addLast(i);
                standardQueue.addLast(i);
            }

            // Measure Custom Queue (Dequeue)
            long start = System.nanoTime();
            while(customQueue.size > 0) dummy ^= customQueue.popFirst();
            customTotal += (System.nanoTime() - start);

            // Measure ArrayDeque (Dequeue)
            start = System.nanoTime();
            while(!standardQueue.isEmpty()) dummy ^= standardQueue.removeFirst();
            standardTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Pop First (Standard FIFO Dequeue)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkIteration(int count, boolean print){
        Queue<Integer> customQueue = new Queue<>(count);
        Deque<Integer> standardQueue = new ArrayDeque<>(count);
        for(int i = 0;i < count;i++){
            customQueue.addLast(i);
            standardQueue.addLast(i);
        }

        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(Integer val : customQueue) dummy ^= val;
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(Integer val : standardQueue) dummy ^= val;
            standardTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Enhanced For-Loop Iteration", count, customTotal, standardTotal, print);
    }

    private static void printResult(String name, int count, long customNanos, long standardNanos, boolean print){
        if(!print) return;

        double customMs = (customNanos / 1_000_000.0) / MEASUREMENT_ROUNDS;
        double standardMs = (standardNanos / 1_000_000.0) / MEASUREMENT_ROUNDS;

        System.out.printf("[%s] N = %,d\n", name, count);
        System.out.printf("  ArrayDeque : %8.2f ms avg\n", standardMs);
        System.out.printf("  Queue      : %8.2f ms avg\n", customMs);
        System.out.println("-----------------------------------------");
    }
}