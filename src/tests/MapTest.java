package tests;

import java.util.*;

public class MapTest{

    private static final int TEST_RUNS = 10_000;
    private static final int MAX_OPS_PER_RUN = 500;

    public static void main(String[] args){
        System.out.println("=========================================");
        System.out.println("     Cuckoo Map vs HashMap Fuzz Test     ");
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
        jbsae.struct.Map<Integer, String> customMap = new jbsae.struct.Map<>(16);
        java.util.Map<Integer, String> standardMap = new HashMap<>(16);

        // Bulk load to force initial collisions and stashing
        int initialSize = rand.nextInt(100);
        for(int i = 0;i < initialSize;i++){
            int key = rand.nextInt(2000);
            String val = "V" + rand.nextInt(10000);
            customMap.add(key, val);
            standardMap.put(key, val);
        }

        int operations = rand.nextInt(MAX_OPS_PER_RUN) + 100;

        for(int i = 0;i < operations;i++){
            int opType = rand.nextInt(4);

            // 50% chance to target an existing key to test overwrites and lookups
            Integer key = rand.nextInt(2000);
            if(rand.nextBoolean() && !standardMap.isEmpty()){
                key = standardMap.keySet().iterator().next();
            }
            String val = "V" + rand.nextInt(10000);

            try{
                switch(opType){
                    case 0 -> {
                        // Test Add / Overwrite
                        customMap.add(key, val);
                        standardMap.put(key, val);
                    }
                    case 1 -> {
                        // Test Remove
                        customMap.remove(key);
                        standardMap.remove(key);
                    }
                    case 2 -> {
                        // Test Get
                        String customVal = customMap.get(key);
                        String standardVal = standardMap.get(key);

                        if(customVal == null && standardVal != null){
                            throw new RuntimeException("Get mismatch! Custom returned null, Standard returned " + standardVal);
                        }
                        if(customVal != null && !customVal.equals(standardVal)){
                            throw new RuntimeException("Get mismatch! Expected: " + standardVal + ", Got: " + customVal);
                        }
                    }
                    case 3 -> {
                        // Test Clear (randomly triggered rarely)
                        if(rand.nextInt(100) == 0){
                            customMap.clear();
                            standardMap.clear();
                        }
                    }
                }
            }catch(Exception e){
                System.err.println("Exception thrown during operation type: " + opType + " with key: " + key);
                System.err.println("Current Custom Size: " + customMap.size + ", Standard Size: " + standardMap.size());
                throw e;
            }

            // Immediately verify size
            if(customMap.size != standardMap.size()){
                throw new RuntimeException("Size mismatch! Custom: " + customMap.size + ", Standard: " + standardMap.size());
            }
        }

        // Final thorough state verification
        verifyStateAndIterators(customMap, standardMap);
    }

    private static void verifyStateAndIterators(jbsae.struct.Map<Integer, String> customMap, java.util.Map<Integer, String> standardMap){

        // 1. Verify all mappings via get()
        for(java.util.Map.Entry<Integer, String> entry : standardMap.entrySet()){
            String customVal = customMap.get(entry.getKey());
            if(!entry.getValue().equals(customVal)){
                throw new RuntimeException("State mismatch for key " + entry.getKey() +
                ". Expected: " + entry.getValue() + ", Got: " + customVal);
            }
        }

        // 2. Extract and Verify Keys Iterator
        List<Integer> customKeys = new ArrayList<>();
        for(Integer k : customMap) customKeys.add(k);

        List<Integer> standardKeys = new ArrayList<>(standardMap.keySet());

        if(customKeys.size() != standardKeys.size()){
            throw new RuntimeException("Key Iterator size mismatch!");
        }

        Collections.sort(customKeys);
        Collections.sort(standardKeys);

        if(!customKeys.equals(standardKeys)){
            throw new RuntimeException("Key Iterator contents mismatch!");
        }

        // 3. Extract and Verify Values Iterator
        List<String> customVals = new ArrayList<>();
        java.util.Iterator<String> valItr = customMap.values();
        while(valItr.hasNext()) customVals.add(valItr.next());

        List<String> standardVals = new ArrayList<>(standardMap.values());

        if(customVals.size() != standardVals.size()){
            throw new RuntimeException("Value Iterator size mismatch!");
        }

        Collections.sort(customVals);
        Collections.sort(standardVals);

        if(!customVals.equals(standardVals)){
            throw new RuntimeException("Value Iterator contents mismatch!");
        }
    }
}