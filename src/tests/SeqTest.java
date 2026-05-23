package tests;

import jbsae.struct.*;

import java.util.*;

// Thank you gemini
public class SeqTest{
    // Number of independent randomized test runs
    private static final int TEST_RUNS = 10_000;
    // Max number of operations (add, remove, set) per test run
    private static final int MAX_OPS_PER_RUN = 200;

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("     Seq vs ArrayList Robustness Test    ");
        System.out.println("=========================================\n");

        // Use a fixed seed for reproducibility if a test fails,
        // e.g., long seed = 12345L;
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);

        System.out.println("Using Random Seed: " + seed);
        System.out.println("Running " + TEST_RUNS + " randomized cycles...");

        for(int i = 0;i < TEST_RUNS;i++){
            runRandomizedCycle(rand);
        }

        System.out.println("\n✅ All " + TEST_RUNS + " randomized test runs passed!");
    }

    private static void runRandomizedCycle(Random rand){
        Seq<Integer> seq = new Seq<>();
        List<Integer> list = new ArrayList<>();

        int operations = rand.nextInt(MAX_OPS_PER_RUN) + 50; // At least 50 ops

        for(int i = 0;i < operations;i++){
            int opType = rand.nextInt(6);
            Integer val = rand.nextInt(1000); // Random value to insert

            // If empty, force an 'add' operation to avoid out-of-bounds exceptions
            if(seq.size == 0){
                opType = 0;
            }

            try{
                switch(opType){
                    case 0 -> {
                        // Add at end
                        seq.add(val);
                        list.add(val);
                    }
                    case 1 -> {
                        // Add at random index
                        int idx = rand.nextInt(seq.size + 1); // +1 allows adding exactly at the end
                        seq.add(idx, val);
                        list.add(idx, val);
                    }
                    case 2 -> {
                        // Set at random index
                        int idx = rand.nextInt(seq.size);
                        seq.set(idx, val);
                        list.set(idx, val);
                    }
                    case 3 -> {
                        // Remove at random index (Ordered)
                        int idx = rand.nextInt(seq.size);
                        seq.ordered(true);
                        seq.remove(idx);
                        list.remove(idx);
                    }
                    case 4 -> {
                        // Remove at random index (Unordered)
                        int idx = rand.nextInt(seq.size);
                        seq.ordered(false);
                        seq.remove(idx);

                        // Manually mimic unordered remove in ArrayList (swap with last, remove last)
                        int lastIdx = list.size() - 1;
                        list.set(idx, list.get(lastIdx));
                        list.remove(lastIdx);

                        seq.ordered(true); // Reset to default state
                    }
                    case 5 -> {
                        // Remove by value
                        // 50% chance to try removing a value that we know is actually in the list
                        Integer target = rand.nextBoolean() ? list.get(rand.nextInt(list.size())) : val;
                        seq.remove(target);
                        list.remove(target);
                    }
                }
            }catch(Exception e){
                System.err.println("Exception thrown during operation type: " + opType);
                System.err.println("Current Seq Size: " + seq.size + ", Current List Size: " + list.size());
                throw e; // Rethrow to crash and show stack trace
            }

            // Immediately verify internal state after every single operation
            assertEqualState(seq, list);
        }

        // Final check: Iterators and Clear
        verifyIterators(seq, list);

        seq.clear();
        list.clear();
        assertEqualState(seq, list);
    }

    private static void verifyIterators(Seq<Integer> seq, List<Integer> list){
        int index = 0;
        for(Integer val : seq){
            if(!val.equals(list.get(index))){
                throw new RuntimeException("Iterator mismatch at index " + index + "! Expected: " + list.get(index) + ", Got: " + val);
            }
            index++;
        }
    }

    private static void assertEqualState(Seq<Integer> seq, List<Integer> list){
        if(seq.size != list.size()){
            throw new RuntimeException("Size mismatch! Seq size: " + seq.size + ", ArrayList size: " + list.size());
        }
        for(int i = 0;i < seq.size;i++){
            if(!seq.get(i).equals(list.get(i))){
                throw new RuntimeException("Element mismatch at index " + i + "!\nExpected: " + list.get(i) + "\nGot: " + seq.get(i));
            }
        }
    }
}