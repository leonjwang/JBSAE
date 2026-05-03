package tests;

import jbsae.struct.*;

import java.util.*;

public class SeqBenchmark{

    // Number of times to run before taking measurements
    private static final int WARMUP_ROUNDS = 5;
    // Number of times to run and average the results
    private static final int MEASUREMENT_ROUNDS = 10;

    // Large sizes for O(1) operations
    private static final int LARGE_N = 5_000_000;
    // Smaller sizes for O(N) operations like inserting/removing at the front
    private static final int SMALL_N = 100_000;

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("          Seq vs ArrayList Benchmarks    ");
        System.out.println("=========================================\n");

        System.out.println("Warming up the JVM (JIT Compilation)...");
        for(int i = 0;i < WARMUP_ROUNDS;i++){
            benchmarkAddEnd(LARGE_N, false);
            benchmarkRandomAccess(LARGE_N, false);
        }
        System.out.println("Warmup complete. Starting measurements.\n");

        System.out.println("--- O(1) Operations ---");
        benchmarkAddEnd(LARGE_N, true);
        benchmarkRandomAccess(LARGE_N, true);
        benchmarkIteration(LARGE_N, true);

        System.out.println("\n--- O(N) vs O(1) Operations ---");
        benchmarkRemoveFront(SMALL_N, true);
    }

    private static void benchmarkAddEnd(int count, boolean print){
        long seqTotal = 0;
        long listTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            // Measure Seq
            Seq<Integer> seq = new Seq<>(16);
            long start = System.nanoTime();
            for(int i = 0;i < count;i++){
                seq.add(i);
            }
            seqTotal += (System.nanoTime() - start);

            // Measure ArrayList
            List<Integer> list = new ArrayList<>(16);
            start = System.nanoTime();
            for(int i = 0;i < count;i++){
                list.add(i);
            }
            listTotal += (System.nanoTime() - start);
        }

        printResult("Sequential Add (at end)", count, seqTotal, listTotal, print);
    }

    private static void benchmarkRandomAccess(int count, boolean print){
        Seq<Integer> seq = new Seq<>(count);
        List<Integer> list = new ArrayList<>(count);
        for(int i = 0;i < count;i++){
            seq.add(i);
            list.add(i);
        }

        long seqTotal = 0;
        long listTotal = 0;
        int dummy = 0; // Accumulator to defeat dead-code elimination

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(int i = 0;i < count;i++){
                dummy ^= seq.get(i);
            }
            seqTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(int i = 0;i < count;i++){
                dummy ^= list.get(i);
            }
            listTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore"); // Force use of dummy
        printResult("Random Access (get by index)", count, seqTotal, listTotal, print);
    }

    private static void benchmarkIteration(int count, boolean print){
        Seq<Integer> seq = new Seq<>(count);
        List<Integer> list = new ArrayList<>(count);
        for(int i = 0;i < count;i++){
            seq.add(i);
            list.add(i);
        }

        long seqTotal = 0;
        long listTotal = 0;
        int dummy = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            long start = System.nanoTime();
            for(Integer val : seq){
                dummy ^= val;
            }
            seqTotal += (System.nanoTime() - start);

            start = System.nanoTime();
            for(Integer val : list){
                dummy ^= val;
            }
            listTotal += (System.nanoTime() - start);
        }

        if(dummy == -1) System.out.println("Ignore");
        printResult("Iterator / Enhanced For-Loop", count, seqTotal, listTotal, print);
    }

    private static void benchmarkRemoveFront(int count, boolean print){
        long seqOrderedTotal = 0;
        long seqUnorderedTotal = 0;
        long listTotal = 0;

        for(int r = 0;r < MEASUREMENT_ROUNDS;r++){
            // Measure ArrayList
            List<Integer> list = new ArrayList<>(count);
            for(int i = 0;i < count;i++) list.add(i);

            long start = System.nanoTime();
            while(!list.isEmpty()){
                list.remove(0); // O(N) operation
            }
            listTotal += (System.nanoTime() - start);

            // Measure Seq (Ordered)
            Seq<Integer> seqOrd = new Seq<Integer>(count).ordered(true);
            for(int i = 0;i < count;i++) seqOrd.add(i);

            start = System.nanoTime();
            while(seqOrd.size > 0){
                seqOrd.remove(0); // O(N) operation
            }
            seqOrderedTotal += (System.nanoTime() - start);

            // Measure Seq (Unordered - Swap with tail)
            Seq<Integer> seqUnord = new Seq<Integer>(count).ordered(false);
            for(int i = 0;i < count;i++) seqUnord.add(i);

            start = System.nanoTime();
            while(seqUnord.size > 0){
                seqUnord.remove(0); // O(1) operation
            }
            seqUnorderedTotal += (System.nanoTime() - start);
        }

        if(print){
            System.out.printf("[Remove from Index 0] N = %,d\n", count);
            System.out.printf("  ArrayList     : %8.2f ms avg\n", (listTotal / 1_000_000.0) / MEASUREMENT_ROUNDS);
            System.out.printf("  Seq (Ordered) : %8.2f ms avg\n", (seqOrderedTotal / 1_000_000.0) / MEASUREMENT_ROUNDS);
            System.out.printf("  Seq (Unorder) : %8.2f ms avg (O(1) swap advantage)\n", (seqUnorderedTotal / 1_000_000.0) / MEASUREMENT_ROUNDS);
            System.out.println("-----------------------------------------");
        }
    }

    private static void printResult(String name, int count, long seqNanos, long listNanos, boolean print){
        if(!print) return;

        double seqMs = (seqNanos / 1_000_000.0) / MEASUREMENT_ROUNDS;
        double listMs = (listNanos / 1_000_000.0) / MEASUREMENT_ROUNDS;

        System.out.printf("[%s] N = %,d\n", name, count);
        System.out.printf("  ArrayList : %8.2f ms avg\n", listMs);
        System.out.printf("  Seq       : %8.2f ms avg\n", seqMs);
        System.out.println("-----------------------------------------");
    }
}