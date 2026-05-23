package tests;

import java.util.*;

public class QueueTest{
    private static final int TEST_RUNS = 10_000;
    private static final int MAX_OPS_PER_RUN = 500;

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("   Queue vs ArrayDeque Robustness Test   ");
        System.out.println("=========================================\n");

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
        jbsae.struct.Queue<Integer> customQueue = new jbsae.struct.Queue<>(16);
        Deque<Integer> standardQueue = new ArrayDeque<>(16);

        int operations = rand.nextInt(MAX_OPS_PER_RUN) + 100;

        for(int i = 0;i < operations;i++){
            int opType = rand.nextInt(6);
            Integer val = rand.nextInt(1000);

            // Force an add if empty to avoid popping from empty queues
            if(customQueue.size == 0){
                opType = rand.nextBoolean() ? 0 : 1;
            }

            try{
                switch(opType){
                    case 0 -> {
                        // Add First
                        customQueue.addFirst(val);
                        standardQueue.addFirst(val);
                    }
                    case 1 -> {
                        // Add Last
                        customQueue.addLast(val);
                        standardQueue.addLast(val);
                    }
                    case 2 -> {
                        // Pop First
                        Integer customVal = customQueue.popFirst();
                        Integer standardVal = standardQueue.removeFirst();
                        if(!customVal.equals(standardVal)){
                            throw new RuntimeException("popFirst mismatch! Expected: " + standardVal + ", Got: " + customVal);
                        }
                    }
                    case 3 -> {
                        // Pop Last
                        Integer customVal = customQueue.popLast();
                        Integer standardVal = standardQueue.removeLast();
                        if(!customVal.equals(standardVal)){
                            throw new RuntimeException("popLast mismatch! Expected: " + standardVal + ", Got: " + customVal);
                        }
                    }
                    case 4 -> {
                        // Check first() / last() without removing
                        if(!customQueue.first().equals(standardQueue.getFirst())){
                            throw new RuntimeException("first() mismatch!");
                        }
                        if(!customQueue.last().equals(standardQueue.getLast())){
                            throw new RuntimeException("last() mismatch!");
                        }
                    }
                    case 5 -> {
                        // Test Set / Get by Index
                        if(customQueue.size > 0){
                            int idx = rand.nextInt(customQueue.size);
                            Integer newVal = rand.nextInt(1000);

                            customQueue.set(idx, newVal);

                            // ArrayDeque doesn't have a direct set(index) method,
                            // so we rebuild it to match the state.
                            List<Integer> temp = new ArrayList<>(standardQueue);
                            temp.set(idx, newVal);
                            standardQueue.clear();
                            standardQueue.addAll(temp);
                        }
                    }
                }
            }catch(Exception e){
                System.err.println("Exception thrown during operation type: " + opType + " with value: " + val);
                System.err.println("Current Custom Size: " + customQueue.size + ", Standard Size: " + standardQueue.size());
                throw e;
            }

            // Verify state completely after every operation
            assertEqualState(customQueue, standardQueue);
        }

        // Final check: Clear
        customQueue.clear();
        standardQueue.clear();
        assertEqualState(customQueue, standardQueue);
    }

    private static void assertEqualState(jbsae.struct.Queue<Integer> customQueue, Deque<Integer> standardQueue){
        if(customQueue.size != standardQueue.size()){
            throw new RuntimeException("Size mismatch! Custom size: " + customQueue.size + ", ArrayDeque size: " + standardQueue.size());
        }

        // Test iterator order matches exactly
        int index = 0;
        for(Integer standardVal : standardQueue){
            Integer customGetVal = customQueue.get(index);

            if(!customGetVal.equals(standardVal)){
                throw new RuntimeException("Element mismatch at index " + index + "!\nExpected: " + standardVal + "\nGot (via get): " + customGetVal);
            }
            index++;
        }

        // Also test the custom iterator specifically
        index = 0;
        for(Integer customIterVal : customQueue){
            // standardQueue.toArray() order matches the iterator order
            Integer standardVal = (Integer)standardQueue.toArray()[index];
            if(!customIterVal.equals(standardVal)){
                throw new RuntimeException("Iterator mismatch at index " + index + "!\nExpected: " + standardVal + "\nGot (via iter): " + customIterVal);
            }
            index++;
        }
    }
}