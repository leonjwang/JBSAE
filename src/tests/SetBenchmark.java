package tests;

import java.util.*;

public class SetBenchmark{

    private static final int WARMUP_ROUNDS = 5;
    private static final int MEASUREMENT_ROUNDS = 10;
    private static final int N = 2_000_000; // 2 Million items for heavy stress testing

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("      Cuckoo Set vs HashSet Benchmarks   ");
        System.out.println("=========================================\n");

        System.out.println("Warming up the JVM (JIT Compilation)...");
        for(int i = 0;i < WARMUP_ROUNDS;i++){
            benchmarkAdd(500_000, false);
            benchmarkContainsPresent(500_000, false);
        }
        System.out.println("Warmup complete. Starting measurements.\n");

        System.out.println("--- Insertion & Resizing ---");
        benchmarkAdd(N, true);

        System.out.println("\n--- Lookups ---");
        benchmarkContainsPresent(N, true);
        benchmarkContainsMissing(N, true);

        System.out.println("\n--- Removals & Iteration ---");
        benchmarkRemove(N, true);
        benchmarkIteration(N, true);
    }

    private static void benchmarkAdd(int count, boolean print){
        long customTotal = 0;
        long standardTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            // Measure Custom Cuckoo Set
            jbsae.struct.Set<Integer> customSet = new jbsae.struct.Set<>();
            long start = System.nanoTime();
            for(int i = 0;i < count;i++) customSet.add(i);
            customTotal += (System.nanoTime() - start);

            // Measure Standard HashSet
            HashSet<Integer> standardSet = new HashSet<>();
            start = System.nanoTime();
            for(int i = 0;i < count;i++) standardSet.add(i);
            standardTotal += (System.nanoTime() - start);
        }

        printResult("Add Elements (Includes Resizing)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkContainsPresent(int count, boolean print){
        jbsae.struct.Set<Integer> customSet = new jbsae.struct.Set<>();
        HashSet<Integer> standardSet = new HashSet<>();

        for(int i = 0;i < count;i++){
            customSet.add(i);
            standardSet.add(i);
        }

        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0; // JIT Defeat

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(int i = 0;i < count;i++) dummy ^= customSet.contains(i) ? 1 : 0;
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(int i = 0;i < count;i++) dummy ^= standardSet.contains(i) ? 1 : 0;
            standardTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Contains (Successful Lookups)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkContainsMissing(int count, boolean print){
        jbsae.struct.Set<Integer> customSet = new jbsae.struct.Set<>();
        HashSet<Integer> standardSet = new HashSet<>();

        for(int i = 0;i < count;i++){
            customSet.add(i);
            standardSet.add(i);
        }

        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            // Checking items out of bounds (count to count*2) ensures 100% miss rate
            for(int i = count;i < count * 2;i++) dummy ^= customSet.contains(i) ? 1 : 0;
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(int i = count;i < count * 2;i++) dummy ^= standardSet.contains(i) ? 1 : 0;
            standardTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Contains (Failed Lookups)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkRemove(int count, boolean print){
        long customTotal = 0;
        long standardTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            jbsae.struct.Set<Integer> customSet = new jbsae.struct.Set<>();
            HashSet<Integer> standardSet = new HashSet<>();
            for(int i = 0;i < count;i++){
                customSet.add(i);
                standardSet.add(i);
            }

            long start = System.nanoTime();
            for(int i = 0;i < count;i++) customSet.remove(i);
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(int i = 0;i < count;i++) standardSet.remove(i);
            standardTotal += (System.nanoTime() - start);
        }

        printResult("Remove Elements", count, customTotal, standardTotal, print);
    }

    private static void benchmarkIteration(int count, boolean print){
        jbsae.struct.Set<Integer> customSet = new jbsae.struct.Set<>();
        HashSet<Integer> standardSet = new HashSet<>();
        for(int i = 0;i < count;i++){
            customSet.add(i);
            standardSet.add(i);
        }

        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(Integer val : customSet) dummy ^= val;
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(Integer val : standardSet) dummy ^= val;
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
        System.out.printf("  HashSet    : %8.2f ms avg\n", standardMs);
        System.out.printf("  Cuckoo Set : %8.2f ms avg\n", customMs);
        System.out.println("-----------------------------------------");
    }
}