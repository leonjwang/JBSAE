package testers;

import jbsae.struct.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class StructTester{
    public static String[] generate(){
        int size = randInt(10, 100);
        String[] arr = new String[size];
        for(int i = 0;i < size;i++){
            String value = "";
            for(int j = 0;j < randInt(1, 10);j++) value += (char)randInt(32, 126);
            arr[i] = value;
        }
        return arr;
    }

    public static void testSeq(){
        new Test("Seq Test", () -> {
            //Setup
            String[] arr = generate();
            java.util.ArrayList<String> arrList = new java.util.ArrayList<>();
            Seq<String> seq = new Seq<>();

            //Add Test
            for(int i = 0;i < arr.length;i++){
                arrList.add(arr[i]);
                seq.add(arr[i]);
            }
            for(int i = 0;i < arrList.size();i++) if(!eql(arrList.get(i), seq.get(i))) return false;

            //Remove Index Test
            int removeIndex = randInt(0, arrList.size() - 1);
            arrList.remove(removeIndex);
            seq.remove(removeIndex);
            for(int i = 0;i < arrList.size();i++) if(!eql(arrList.get(i), seq.get(i))) return false;

            //Remove Value Test
            String removeValue = arrList.get(randInt(0, arrList.size() - 1));
            arrList.remove(removeValue);
            seq.remove(removeValue);
            for(int i = 0;i < arrList.size();i++) if(!eql(arrList.get(i), seq.get(i))) return false;

            //Contains Test
            String value = arrList.get(randInt(0, arrList.size() - 1));
            if(!seq.contains(value)) return false;

            //Iteration Test
            int i = 0;
            for(String str : seq) if(!eql(arrList.get(i++), str)) return false;

            return true;
        }).run();
    }

    public static void testSet(){
        new Test("Set Test", () -> {
            //Setup
            String[] arr = generate();
            java.util.HashSet<String> hashSet = new java.util.HashSet<>();
            Set<String> set = new Set<>();

            //Add Test
            for(int i = 0;i < arr.length;i++){
                hashSet.add(arr[i]);
                set.add(arr[i]);
            }
            for(String str : set) if(!hashSet.contains(str)) return false;
            for(String str : hashSet) if(!set.contains(str)) return false;

            //Remove Test
            String removeValue = arr[randInt(0, arr.length - 1)];
            hashSet.remove(removeValue);
            set.remove(removeValue);
            for(String str : set) if(!hashSet.contains(str)) return false;
            for(String str : hashSet) if(!set.contains(str)) return false;

            //Contains Test
            String value = arr[randInt(0, arr.length - 1)];
            if(hashSet.contains(value) && !set.contains(value)) return false;
            if(!hashSet.contains(value) && set.contains(value)) return false;

            return true;

        }).run();
    }

    public static void main(String[] args){
        testSeq();
        testSet();
    }
}
