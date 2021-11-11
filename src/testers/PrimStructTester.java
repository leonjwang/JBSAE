package testers;

import jbsae.struct.prim.*;

import static jbsae.util.Mathf.*;

public class PrimStructTester{
    public static void testSeqs(){
        new Test("Float Seq Test", () -> {
            //Setup
            java.util.ArrayList<Float> arrList = new java.util.ArrayList<>();
            FloatSeq seq = new FloatSeq();

            //Add Test
            for(int i = 0;i < randInt(10, 100);i++){
                float value = random(0, 10000);
                arrList.add(value);
                seq.add(value);
            }
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Insert Test
            for(int i = 0;i < randInt(2, 5);i++){
                float value = random(0, 10000);
                int index = randInt(0, arrList.size() - 1);
                arrList.add(index, value);
                seq.add(value, index);
            }
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Remove Index Test
            int removeIndex = randInt(0, arrList.size() - 1);
            arrList.remove(removeIndex);
            seq.remove(removeIndex);
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Remove Value Test
            float removeValue = arrList.get(randInt(0, arrList.size() - 1));
            arrList.remove(removeValue);
            seq.removeValue(removeValue);
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Remove All Values Test
            for(int i = 0;i < randInt(3, 10);i++){
                int index = randInt(0, arrList.size() - 1);
                arrList.add(index, removeValue);
                seq.add(removeValue, index);
            }
            while(arrList.remove(removeValue)) continue;
            seq.removeAll(removeValue);
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Contains Test
            float value = arrList.get(randInt(0, arrList.size() - 1));
            if(!seq.contains(value)) return false;

            return true;
        }).run();
        new Test("Boolean Seq Test", () -> {
            //Setup
            java.util.ArrayList<Boolean> arrList = new java.util.ArrayList<>();
            BoolSeq seq = new BoolSeq();

            //Add Test
            for(int i = 0;i < randInt(10, 100);i++){
                boolean value = chance(0.5f);
                arrList.add(value);
                seq.add(value);
            }
            for(int i = 0;i < arrList.size();i++) if(arrList.get(i) != seq.get(i)) return false;

            //Insert Test
            for(int i = 0;i < randInt(2, 5);i++){
                boolean value = chance(0.5f);
                int index = randInt(0, arrList.size() - 1);
                arrList.add(index, value);
                seq.add(value, index);
            }
            for(int i = 0;i < arrList.size();i++) if(arrList.get(i) != seq.get(i)) return false;

            //Remove Index Test
            int removeIndex = randInt(0, arrList.size() - 1);
            arrList.remove(removeIndex);
            seq.remove(removeIndex);
            for(int i = 0;i < arrList.size();i++) if(arrList.get(i) != seq.get(i)) return false;

            return true;
        }).run();
        new Test("Int Seq Test", () -> {
            //Setup
            java.util.ArrayList<Integer> arrList = new java.util.ArrayList<>();
            IntSeq seq = new IntSeq();

            //Add Test
            for(int i = 0;i < randInt(10, 100);i++){
                int value = randInt(0, 10000);
                arrList.add(value);
                seq.add(value);
            }
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Insert Test
            for(int i = 0;i < randInt(2, 5);i++){
                int value = randInt(0, 10000);
                int index = randInt(0, arrList.size() - 1);
                arrList.add(index, value);
                seq.add(value, index);
            }
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Remove Index Test
            int removeIndex = randInt(0, arrList.size() - 1);
            arrList.remove(removeIndex);
            seq.remove(removeIndex);
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Remove Value Test
            int removeValue = arrList.get(randInt(0, arrList.size() - 1));
            arrList.remove((Integer)removeValue);
            seq.removeValue(removeValue);
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Remove All Values Test
            for(int i = 0;i < randInt(3, 10);i++){
                int index = randInt(0, arrList.size() - 1);
                arrList.add(index, removeValue);
                seq.add(removeValue, index);
            }
            while(arrList.remove((Integer)removeValue)) continue;
            seq.removeAll(removeValue);
            for(int i = 0;i < arrList.size();i++) if(!eqlf(arrList.get(i), seq.get(i))) return false;

            //Contains Test
            int value = arrList.get(randInt(0, arrList.size() - 1));
            if(!seq.contains(value)) return false;

            return true;
        }).run();
    }

    public static void testSets(){
        new Test("Float Set Test", () -> {
            //Setup
            java.util.HashSet<Float> hashSet = new java.util.HashSet<>();
            FloatSet set = new FloatSet();

            //Add Test
            float[] values = new float[randInt(10, 100)];
            for(int i = 0;i < values.length;i++){
                float value = (i == 0) ? 0 : random(0, 10000);
                values[i] = value;
                hashSet.add(value);
                set.add(value);
            }
            for(Float value : hashSet) if(!set.contains(value)) return false;
            float[] list = set.list();
            for(int i = 0;i < list.length;i++) if(!hashSet.contains(list[i])) return false;

            //Remove Test
            float removeValue = values[randInt(0, values.length - 1)];
            hashSet.remove(removeValue);
            set.remove(removeValue);
            for(Float value : hashSet) if(!set.contains(value)) return false;
            list = set.list();
            for(int i = 0;i < list.length;i++) if(!hashSet.contains(list[i])) return false;

            //Contains Test
            float value = values[randInt(0, values.length - 1)];
            if(hashSet.contains(value) != set.contains(value)) return false;

            return true;
        }).run();
        new Test("Int Set Test", () -> {
            //Setup
            java.util.HashSet<Integer> hashSet = new java.util.HashSet<>();
            IntSet set = new IntSet();

            //Add Test
            int[] values = new int[randInt(10, 100)];
            for(int i = 0;i < values.length;i++){
                int value = (i == 0) ? 0 : randInt(0, 10000);
                values[i] = value;
                hashSet.add(value);
                set.add(value);
            }
            for(Integer value : hashSet) if(!set.contains(value)) return false;
            int[] list = set.list();
            for(int i = 0;i < list.length;i++) if(!hashSet.contains(list[i])) return false;

            //Remove Test
            int removeValue = values[randInt(0, values.length - 1)];
            hashSet.remove(removeValue);
            set.remove(removeValue);
            for(Integer value : hashSet) if(!set.contains(value)) return false;
            list = set.list();
            for(int i = 0;i < list.length;i++) if(!hashSet.contains(list[i])) return false;

            //Contains Test
            int value = values[randInt(0, values.length - 1)];
            if(hashSet.contains(value) != set.contains(value)) return false;

            return true;
        }).run();
    }

    public static void main(String[] args){
        testSeqs();
        testSets();
    }
}
