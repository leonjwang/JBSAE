package tests;

import java.util.*;

public class SetTest{
    // Number of independent randomized test runs
    private static final int TEST_RUNS = 10_000;
    // Max number of operations (add, remove, contains) per test run
    private static final int MAX_OPS_PER_RUN = 500;

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("     Cuckoo Set vs HashSet Fuzz Test     ");
        System.out.println("=========================================\n");

        // Use a fixed seed for exact bug reproduction if a test fails
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
        jbsae.struct.Set<Integer> customSet = new jbsae.struct.Set<>();
        HashSet<Integer> standardSet = new HashSet<>();

        // Add an initial bulk load to force early resizing and stashing
        int initialSize = rand.nextInt(100);
        for(int i = 0;i < initialSize;i++){
            int val = rand.nextInt(2000);
            customSet.add(val);
            standardSet.add(val);
        }

        int operations = rand.nextInt(MAX_OPS_PER_RUN) + 100;

        for(int i = 0;i < operations;i++){
            int opType = rand.nextInt(3);

            // 50% chance to use a completely random value,
            // 50% chance to pick a value we KNOW is in the set to test exact matches
            Integer val = rand.nextInt(2000);
            if(rand.nextBoolean() && !standardSet.isEmpty()){
                val = standardSet.iterator().next(); // Grabs an existing element
            }

            try{
                switch(opType){
                    case 0 -> {
                        // Test Add (handles both new items and duplicates natively)
                        customSet.add(val);
                        standardSet.add(val);
                    }
                    case 1 -> {
                        // Test Remove
                        customSet.remove(val);
                        standardSet.remove(val);
                    }
                    case 2 -> {
                        // Test Contains actively during the cycle
                        boolean customHas = customSet.contains(val);
                        boolean standardHas = standardSet.contains(val);
                        if(customHas != standardHas){
                            throw new RuntimeException("Contains mismatch on value " + val +
                            "! Expected: " + standardHas + ", Got: " + customHas);
                        }
                    }
                }
            }catch(Exception e){
                System.err.println("Exception thrown during operation type: " + opType + " with value: " + val);
                System.err.println("Current Custom Size: " + customSet.size + ", Standard Size: " + standardSet.size());
                throw e;
            }

            // Continuously verify size equivalence
            if(customSet.size != standardSet.size()){
                System.out.println("Custom Elements: " + customSet.toString());
                System.out.println("Standard Elements: " + standardSet.toString());
                throw new RuntimeException("Size mismatch mid-cycle! Custom: " + customSet.size + ", Standard: " + standardSet.size());
            }
        }

        // Final comprehensive check
        verifyStateAndIterators(customSet, standardSet);
    }

    private static void verifyStateAndIterators(jbsae.struct.Set<Integer> customSet, HashSet<Integer> standardSet){
        // 1. Verify every single element in HashSet exists in CustomSet via .contains()
        for(Integer expectedVal : standardSet){
            if(!customSet.contains(expectedVal)){
                throw new RuntimeException("Custom set is missing value: " + expectedVal);
            }
        }

        // 2. Extract elements via Iterators.
        // Hash sets do not guarantee order, so we must sort them to compare them.
        List<Integer> customExtracted = new ArrayList<>();
        for(Integer val : customSet){
            customExtracted.add(val);
        }

        List<Integer> standardExtracted = new ArrayList<>(standardSet);

        if(customExtracted.size() != standardExtracted.size()){
            throw new RuntimeException("Iterator size mismatch! Custom: " + customExtracted.size() + ", Standard: " + standardExtracted.size());
        }

        Collections.sort(customExtracted);
        Collections.sort(standardExtracted);

        // 3. Verify exactly matching contents
        for(int i = 0;i < customExtracted.size();i++){
            if(!customExtracted.get(i).equals(standardExtracted.get(i))){
                throw new RuntimeException("Iterator content mismatch at sorted index " + i +
                "! Expected: " + standardExtracted.get(i) + ", Got: " + customExtracted.get(i));
            }
        }
    }
}