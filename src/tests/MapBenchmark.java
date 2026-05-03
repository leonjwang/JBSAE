package tests;

import java.util.*;

public class MapBenchmark{

    private static final int WARMUP_ROUNDS = 5;
    private static final int MEASUREMENT_ROUNDS = 10;
    private static final int N = 2_000_000; // 2 Million entries

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("      Cuckoo Map vs HashMap Benchmarks   ");
        System.out.println("=========================================\n");

        System.out.println("Warming up the JVM (JIT Compilation)...");
        for(int i = 0;i < WARMUP_ROUNDS;i++){
            benchmarkPut(500_000, false);
            benchmarkGetPresent(500_000, false);
        }
        System.out.println("Warmup complete. Starting measurements.\n");

        System.out.println("--- Insertion & Resizing ---");
        benchmarkPut(N, true);

        System.out.println("\n--- Lookups ---");
        benchmarkGetPresent(N, true);
        benchmarkGetMissing(N, true);

        System.out.println("\n--- Removals & Iteration ---");
        benchmarkRemove(N, true);
        benchmarkIteration(N, true);
    }

    private static void benchmarkPut(int count, boolean print){
        long customTotal = 0;
        long standardTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            jbsae.struct.Map<Integer, Integer> customMap = new jbsae.struct.Map<>(16);
            long start = System.nanoTime();
            for(int i = 0;i < count;i++) customMap.add(i, i);
            customTotal += (System.nanoTime() - start);

            HashMap<Integer, Integer> standardMap = new HashMap<>(16);
            start = System.nanoTime();
            for(int i = 0;i < count;i++) standardMap.put(i, i);
            standardTotal += (System.nanoTime() - start);
        }

        printResult("Put/Add Elements (Includes Resizing)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkGetPresent(int count, boolean print){
        jbsae.struct.Map<Integer, Integer> customMap = new jbsae.struct.Map<>(count);
        HashMap<Integer, Integer> standardMap = new HashMap<>(count);

        for(int i = 0;i < count;i++){
            customMap.add(i, i);
            standardMap.put(i, i);
        }

        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(int i = 0;i < count;i++) dummy ^= customMap.get(i);
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(int i = 0;i < count;i++) dummy ^= standardMap.get(i);
            standardTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Get (Successful Lookups)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkGetMissing(int count, boolean print){
        jbsae.struct.Map<Integer, Integer> customMap = new jbsae.struct.Map<>(count);
        HashMap<Integer, Integer> standardMap = new HashMap<>(count);

        for(int i = 0;i < count;i++){
            customMap.add(i, i);
            standardMap.put(i, i);
        }

        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(int i = count;i < count * 2;i++){
                Integer val = customMap.get(i);
                if(val != null) dummy ^= val;
            }
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(int i = count;i < count * 2;i++){
                Integer val = standardMap.get(i);
                if(val != null) dummy ^= val;
            }
            standardTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Get (Failed Lookups)", count, customTotal, standardTotal, print);
    }

    private static void benchmarkRemove(int count, boolean print){
        long customTotal = 0;
        long standardTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            jbsae.struct.Map<Integer, Integer> customMap = new jbsae.struct.Map<>(count);
            HashMap<Integer, Integer> standardMap = new HashMap<>(count);
            for(int i = 0;i < count;i++){
                customMap.add(i, i);
                standardMap.put(i, i);
            }

            long start = System.nanoTime();
            for(int i = 0;i < count;i++) customMap.remove(i);
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(int i = 0;i < count;i++) standardMap.remove(i);
            standardTotal += (System.nanoTime() - start);
        }

        printResult("Remove Elements", count, customTotal, standardTotal, print);
    }

    private static void benchmarkIteration(int count, boolean print){
        jbsae.struct.Map<Integer, Integer> customMap = new jbsae.struct.Map<>(count);
        HashMap<Integer, Integer> standardMap = new HashMap<>(count);
        for(int i = 0;i < count;i++){
            customMap.add(i, i);
            standardMap.put(i, i);
        }

        long customTotal = 0;
        long standardTotal = 0;
        int dummy = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(Integer key : customMap) dummy ^= key;
            customTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(Integer key : standardMap.keySet()) dummy ^= key;
            standardTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Key Iteration", count, customTotal, standardTotal, print);
    }

    private static void printResult(String name, int count, long customNanos, long standardNanos, boolean print){
        if(!print) return;

        double customMs = (customNanos / 1_000_000.0) / MEASUREMENT_ROUNDS;
        double standardMs = (standardNanos / 1_000_000.0) / MEASUREMENT_ROUNDS;

        System.out.printf("[%s] N = %,d\n", name, count);
        System.out.printf("  HashMap    : %8.2f ms avg\n", standardMs);
        System.out.printf("  Cuckoo Map : %8.2f ms avg\n", customMs);
        System.out.println("-----------------------------------------");
    }
}