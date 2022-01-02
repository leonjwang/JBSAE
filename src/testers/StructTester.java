package testers;

import jbsae.math.*;
import jbsae.struct.*;
import jbsae.struct.tree.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class StructTester{
    public static String single(){
        String value = "";
        for(int j = 0;j < randInt(1, 10);j++) value += (char)randInt('A', 'Z');
        return value;
    }

    public static String[] generate(){
        int size = randInt(10, 100);
        String[] arr = new String[size];
        for(int i = 0;i < size;i++) arr[i] = single();
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

            //Insert Test
            for(int i = 0;i < randInt(2, 5);i++){
                String value = single();
                int index = randInt(0, arrList.size() - 1);
                arrList.add(index, value);
                seq.add(value, index);
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

            //Double Iteration Test
            i = 0;
            for(String a : seq){
                for(String b : seq){
                    if(!eql(arrList.get(i), b)) return false;
                    i = (i + 1) % arrList.size();
                }
            }

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
            if(hashSet.contains(value) != set.contains(value)) return false;

            //Double Iteration Test
            Set<String> found = new Set<>();
            String firstValue = (String)set.list()[0];
            for(String a : set){
                for(String b : set){
                    int originalSize = found.size;
                    found.add(b);
                    if(found.size > originalSize && !a.equals(firstValue)) return false;
                }
            }
            for(String str : hashSet) if(!found.contains(str)) return false;

            return true;
        }).run();
    }

    public static void testQueue(){
        new Test("Queue Test", () -> {
            //Setup
            String[] arr = generate();
            java.util.LinkedList<String> linkedList = new java.util.LinkedList<>();
            Queue<String> queue = new Queue<>();

            //Add Last Test
            for(int i = 0;i < arr.length;i++){
                linkedList.addLast(arr[i]);
                queue.addLast(arr[i]);
            }
            int j = 0;
            for(String str : queue) if(!eql(linkedList.get(j++), str)) return false;

            //Add First Test
            for(int i = 0;i < arr.length;i++){
                linkedList.addFirst(arr[i]);
                queue.addFirst(arr[i]);
            }
            j = 0;
            for(String str : queue) if(!eql(linkedList.get(j++), str)) return false;

            //Remove Last Test
            for(int i = 0;i < randInt(0, 5);i++){
                linkedList.removeLast();
                queue.removeLast();
            }
            j = 0;
            for(String str : queue) if(!eql(linkedList.get(j++), str)) return false;

            //Remove First Test
            for(int i = 0;i < randInt(0, 5);i++){
                linkedList.removeFirst();
                queue.removeFirst();
            }
            j = 0;
            for(String str : queue) if(!eql(linkedList.get(j++), str)) return false;

            //Get Test
            if(!eql(linkedList.get(0), queue.first())) return false;
            if(!eql(linkedList.get(linkedList.size() - 1), queue.last())) return false;

            //Contains test
            String value = arr[randInt(0, arr.length - 1)];
            if(linkedList.contains(value) != queue.contains(value)) return false;

            //Double Iteration Test
            int i = 0;
            for(String a : queue){
                for(String b : queue){
                    if(!eql(linkedList.get(i), b)) return false;
                    i = (i + 1) % linkedList.size();
                }
            }

            return true;
        }).run();
    }

    public static void testTree(){
        new Test("Tree Test", () -> {
            //Setup
            java.util.ArrayList<String> values = new java.util.ArrayList<>();
            Tree<String> tree = new Tree<>();
            Tree<String> current = tree;
            for(int i = 0;i < randInt(3, 10);i++){
                String[] added = generate();
                current.addAll(added);
                for(int j = 0;j < added.length;j++) values.add(added[j]);
                for(int j = 0;j < randInt(1, 4);j++) current.addBranch(new Tree<>());
                if(chance(0.3f) && current.parent != null) current = current.parent;
                else current = current.branches.get(randInt(0, current.branches.size - 1));
            }

            //Value Test
            for(String str : tree){
                if(!values.contains(str)) return false;
                values.remove(str);
            }
            if(values.size() > 0) return false;

            return true;
        }).run();
        new Test("BinTree Test", () -> {
            //Setup
            float max = random(10, 10000);
//            BinTree<Vec2> tree = new BinTree(0, max, v -> ((Vec2)v).x);
//            for(int i = 0;i < randInt(2, 3);i++) tree.add(new Vec2(random(0, max), 0));

            return true;
        }).run();
        new Test("QuadTree Test", 10, () -> {
            //Setup
            Seq<Vec2> original = new Seq<>();
            QuadTree tree = new QuadTree(10000, 10000).valueLimit(8);
            for(int i = 0;i < 10000;i++){
                Vec2 v = new Vec2(random(0, 10000), random(0, 10000));
                tree.add(v);
                original.add(v);
            }

            Range2 range = new Range2(random(0, 9000), random(0, 9000), random(0, 1000), random(0, 1000));
            for(int i = 0;i < 10000;i++) tree.findAll(range);

            Seq<Pos2> inside = tree.findAll(range);
            Seq<Pos2> trueInside = new Seq<>();
            for(Vec2 v : original){
                if(range.contains(v)){
                    trueInside.add(v);
                    if(!inside.contains(v)) return false;
                }
            }
            if(inside.size > trueInside.size * 2 + 100) return false;

            return true;
        }).run();
    }

    public static void testMap(){
        new Test("Map Test", () -> {
            //Setup
            String[] arr = generate();
            java.util.HashSet<String> keys = new java.util.HashSet<>();
            for(int i = 0;i < arr.length;i++) keys.add(arr[i]);
            Object[] unique = keys.toArray();
            String[] values = new String[unique.length];
            java.util.HashMap<String, String> hashMap = new java.util.HashMap<>();
            Map<String, String> map = new Map<>();
            for(int i = 0;i < unique.length;i++) values[i] = single();

            //Add Test
            for(int i = 0;i < unique.length;i++){
                hashMap.put((String)unique[i], values[i]);
                map.add((String)unique[i], values[i]);
            }
            for(String key : map){
                if(!hashMap.keySet().contains(key)) return false;
                if(!eql(hashMap.get(key), map.get(key))) return false;
            }
            for(String key : hashMap.keySet()) if(!map.contains(key)) return false;

            //Remove Test
            String removeValue = arr[randInt(0, arr.length - 1)];
            hashMap.remove(removeValue);
            map.remove(removeValue);
            for(String key : map){
                if(!hashMap.keySet().contains(key)) return false;
                if(!eql(hashMap.get(key), map.get(key))) return false;
            }
            for(String key : hashMap.keySet()) if(!map.contains(key)) return false;

            //Contains Test
            String value = arr[randInt(0, arr.length - 1)];
            if(hashMap.containsKey(value) != map.contains(value)) return false;

            //Double Iteration Test
            Set<String> found = new Set<>();
            String firstValue = (String)map.keys()[0];
            for(String a : map){
                for(String b : map){
                    int originalSize = found.size;
                    found.add(b);
                    if(found.size > originalSize && !a.equals(firstValue)) return false;
                }
            }
            for(String str : hashMap.keySet()) if(!found.contains(str)) return false;

            return true;
        }).run();
    }

    public static void main(String[] args){
//        testSeq();
//        testSet();
//        testQueue();
        testTree();
//        testMap();
    }
}
