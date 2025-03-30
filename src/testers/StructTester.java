package testers;

import jbsae.math.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;
import jbsae.struct.tree.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class StructTester{
    public static int failed = 0;

    public static String single(){
        CharSeq value = new CharSeq();
        for(int j = 0;j < randInt(1, 100);j++) value.add((char)randInt('A', 'Z'));
        return value.toString();
    }

    public static String[] generate(){
        return generate(10000);
    }

    public static String[] generate(int max){
        int size = randInt(1, 10000);
        String[] arr = new String[size];
        for(int i = 0;i < size;i++) arr[i] = single();
        return arr;
    }

    public static void testSeqs(){
        new Test("Seq Test", () -> {
            java.util.ArrayList<String> base = new java.util.ArrayList<>();
            Seq<String> custom = new Seq<>();

            for(String value : generate()){
                base.add(value);
                custom.add(value);
            }

            if(!eql(base, custom)) throw new Exception("Not equal after operation [add]");

            for(String value : generate()){
                int index = randInt(0, base.size());
                base.add(index, value);
                custom.add(value, index);
            }

            if(!eql(base, custom)) throw new Exception("Not equal after operation [insert]");

            java.util.Collections.sort(base);
            custom.sort();

            if(!eql(base, custom)) throw new Exception("Not equal after operation [sort]");

            for(int i = 0;i < base.size();i++) if(!eql(base.get(i), custom.get(i))) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(removeIndex);
                custom.remove(removeIndex);
            }

            if(!eql(base, custom)) throw new Exception("Not equal after operation [remove]");
        }).run();
        new Test("Float Seq Test", () -> {
            java.util.ArrayList<Float> base = new java.util.ArrayList<Float>();
            FloatSeq custom = new FloatSeq();

            int size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                float value = random(-10000, 10000);
                base.add(value);
                custom.add(value);
            }

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            for(int i = 0;i < size;i++){
                int index = randInt(0, base.size());
                float value = random(-10000, 10000);
                base.add(index, value);
                custom.add(value, index);
            }

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [insert]");

            java.util.Collections.sort(base);
            custom.sort();

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [sort]");

            for(int i = 0;i < base.size();i++) if(base.get(i) != custom.get(i)) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(removeIndex);
                custom.remove(removeIndex);
            }

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [remove]");
        }).run();
        new Test("Boolean Seq Test", () -> {
            java.util.ArrayList<Boolean> base = new java.util.ArrayList<Boolean>();
            BoolSeq custom = new BoolSeq();

            int size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                boolean value = chance(0.5f);
                base.add(value);
                custom.add(value);
            }

            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            for(int i = 0;i < size;i++){
                int index = randInt(0, base.size());
                boolean value = chance(0.5f);
                base.add(index, value);
                custom.add(value, index);
            }

            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [insert]");

//            java.util.Collections.sort(base);
//            custom.sort();
//
//            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [sort]");

            for(int i = 0;i < base.size();i++) if(base.get(i) != custom.get(i)) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(removeIndex);
                custom.remove(removeIndex);
            }

            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [remove]");
        }).run();
        new Test("Integer Seq Test", () -> {
            java.util.ArrayList<Integer> base = new java.util.ArrayList<Integer>();
            IntSeq custom = new IntSeq();

            int size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                int value = randInt(-10000, 10000);
                base.add(value);
                custom.add(value);
            }

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            for(int i = 0;i < size;i++){
                int index = randInt(0, base.size());
                int value = randInt(-10000, 10000);
                base.add(index, value);
                custom.add(value, index);
            }

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [insert]");

            java.util.Collections.sort(base);
            custom.sort();

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [sort]");

            for(int i = 0;i < base.size();i++) if(base.get(i) != custom.get(i)) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(removeIndex);
                custom.remove(removeIndex);
            }

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [remove]");
        }).run();
    }

    public static void testSets(){
        new Test("Set Test", () -> {
            java.util.HashSet<String> base = new java.util.HashSet<>();
            Set<String> custom = new Set<>();

            String[] values = generate();
            for(String value : values){
                base.add(value);
                custom.add(value);
            }

            if(!eqlc(base, custom)) throw new Exception("Not equal after operation [add]");

            for(int i = 0;i < values.length;i++){
                String value = chance(0.2f) ? single() : values[i];
                if(base.contains(value) != custom.contains(value)) throw new Exception("Not equal for operation [contains]");
            }

            if(!eqlc(base, custom)) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(values[removeIndex]);
                custom.remove(values[removeIndex]);
            }

            if(!eqlc(base, custom)) throw new Exception("Not equal after operation [remove]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Float Set Test", () -> {
            java.util.HashSet<Float> base = new java.util.HashSet<Float>();
            FloatSet custom = new FloatSet();

            float[] values = new float[randInt(1, 10000)];
            for(int i = 0;i < values.length;i++){
                values[i] = chance(0.05f) ? 0 : random(-10000, 10000);
                base.add(values[i]);
                custom.add(values[i]);
            }

            if(!eqlc(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            for(int i = 0;i < values.length;i++){
                float value = chance(0.2f) ? random(-10000, 10000) : values[i];
                if(base.contains(value) != custom.contains(value)) throw new Exception("Not equal for operation [contains]");
            }

            if(!eqlc(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(values[removeIndex]);
                custom.remove(values[removeIndex]);
            }

            if(!eqlc(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Integer Set Test", () -> {
            java.util.HashSet<Integer> base = new java.util.HashSet<Integer>();
            IntSet custom = new IntSet();

            int[] values = new int[randInt(1, 10000)];
            for(int i = 0;i < values.length;i++){
                values[i] = chance(0.05f) ? 0 : randInt(-10000, 10000);
                base.add(values[i]);
                custom.add(values[i]);
            }

            if(!eqlc(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            for(int i = 0;i < values.length;i++){
                int value = chance(0.2f) ? randInt(-10000, 10000) : values[i];
                if(base.contains(value) != custom.contains(value)) throw new Exception("Not equal for operation [contains]");
            }

            if(!eqlc(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(values[removeIndex]);
                custom.remove(values[removeIndex]);
            }

            if(!eqlc(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [add]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
    }

    public static void testQueues(){
        new Test("Queue Test", () -> {
            java.util.LinkedList<String> base = new java.util.LinkedList<>();
            Queue<String> custom = new Queue<>();

            for(String value : generate()){
                base.addLast(value);
                custom.addLast(value);
            }

            if(!eql(base, custom)) throw new Exception("Not equal after operation [addLast]");

            for(String value : generate()){
                base.addFirst(value);
                custom.addFirst(value);
            }

            if(!eql(base, custom)) throw new Exception("Not equal after operation [addFirst]");

            for(int i = 0;i < base.size();i++) if(!eql(base.get(i), custom.get(i))) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeLast();
                custom.removeLast();
            }

            if(!eql(base, custom)) throw new Exception("Not equal after operation [removeLast]");

            amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeFirst();
                custom.removeFirst();
            }

            if(!eql(base, custom)) throw new Exception("Not equal after operation [removeFirst]");
        }).run();
        new Test("Float Queue Test", () -> {
            java.util.LinkedList<Float> base = new java.util.LinkedList<Float>();
            FloatQueue custom = new FloatQueue();

            int size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                float value = random(-10000, 10000);
                base.addLast(value);
                custom.addLast(value);
            }

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [addLast]");

            size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                float value = random(-10000, 10000);
                base.addFirst(value);
                custom.addFirst(value);
            }

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [addFirst]");

            for(int i = 0;i < base.size();i++) if(base.get(i) != custom.get(i)) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeLast();
                custom.removeLast();
            }

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [removeLast]");

            amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeFirst();
                custom.removeFirst();
            }

            if(!eql(objf(base.toArray()), custom.list())) throw new Exception("Not equal after operation [removeFirst]");
        }).run();
        new Test("Boolean Queue Test", () -> {
            java.util.LinkedList<Boolean> base = new java.util.LinkedList<Boolean>();
            BoolQueue custom = new BoolQueue();

            int size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                boolean value = chance(0.5f);
                base.addLast(value);
                custom.addLast(value);
            }

            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [addLast]");

            size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                boolean value = chance(0.5f);
                base.addFirst(value);
                custom.addFirst(value);
            }

            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [addFirst]");

            for(int i = 0;i < base.size();i++) if(base.get(i) != custom.get(i)) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeLast();
                custom.removeLast();
            }

            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [removeLast]");

            amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeFirst();
                custom.removeFirst();
            }

            if(!eql(objb(base.toArray()), custom.list())) throw new Exception("Not equal after operation [removeFirst]");
        }).run();
        new Test("Integer Queue Test", () -> {
            java.util.LinkedList<Integer> base = new java.util.LinkedList<Integer>();
            IntQueue custom = new IntQueue();

            int size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                int value = randInt(-10000, 10000);
                base.addLast(value);
                custom.addLast(value);
            }

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [addLast]");

            size = randInt(1, 10000);
            for(int i = 0;i < size;i++){
                int value = randInt(-10000, 10000);
                base.addFirst(value);
                custom.addFirst(value);
            }

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [addFirst]");

            for(int i = 0;i < base.size();i++) if(base.get(i) != custom.get(i)) throw new Exception("Not equal for operation [get]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeLast();
                custom.removeLast();
            }

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [removeLast]");

            amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                base.removeFirst();
                custom.removeFirst();
            }

            if(!eql(obji(base.toArray()), custom.list())) throw new Exception("Not equal after operation [removeFirst]");
        }).run();
    }

    public static void testTrees(){
        new Test("Tree Test", () -> {
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

            for(String str : tree){
                if(!values.contains(str)) throw new Exception("Not equal for operation [contains]");
                values.remove(str);
            }
            if(values.size() > 0) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("QuadTree Test", () -> {
            Seq<Vec2> original = new Seq<>();
            QuadTree tree = new QuadTree(10000, 10000).valueLimit(8);
            for(int i = 0;i < 10000;i++){
                Vec2 v = new Vec2(random(0, 10000), random(0, 10000));
                tree.add(v);
                original.add(v);
            }

            Range2 range = new Range2(random(0, 9000), random(0, 9000), random(0, 1000), random(0, 1000));
            Seq<Pos2> inside = new Seq<>();
            for(int i = 0;i < 10000;i++) tree.query(inside, range);

            inside.clear();
            tree.query(inside, range);
            Seq<Vec2> trueInside = new Seq<>();
            for(Vec2 v : original) if(range.contains(v)) trueInside.add(v);

            for(Vec2 v : trueInside) if(!inside.contains(v)) throw new Exception("Not equal for operation [contains]");
        }).run();
        new Test("BinTree Test", () -> {
            Seq<Vec2> original = new Seq<>();
            BinTree<Vec2> tree = new BinTree<Vec2>(v -> v.x);
            for(int i = 0;i < 1000;i++){
                Vec2 v = new Vec2(random(0, 10000), random(0, 10000));
                tree.add(v);
                original.add(v);
            }

            for(Vec2 v : original){
                Vec2 res = tree.find(o -> v.x - o.x);
                if(res == null || abs(res.x - v.x) >= 1) throw new Exception("Not equal for operation [find]");
            }
        }).run();
        new Test("RangeTree Test", () -> {
            Seq<Range2> original = new Seq<>();
            RangeTree<Range2> tree = new RangeTree(10000, 10000).valueLimit(8);
            for(int i = 0;i < 10000;i++){
                Range2 r = new Range2(random(0, 10000), random(0, 10000), random(10, 50), random(10, 50));
                tree.add(r);
                original.add(r);
            }

            Range2 range = new Range2(random(0, 9000), random(0, 9000), random(0, 1000), random(0, 1000));
            Seq<Range2> inside = new Seq<>();
            for(int i = 0;i < 10000;i++) tree.query(inside, range);

            inside.clear();
            Seq<Range2> trueInside = null;
            tree.query(inside, range);
            trueInside = new Seq<>();
            for(Range2 r : original) if(range.overlaps(r)) trueInside.add(r);

            for(Range2 r : trueInside) if(!inside.contains(r)) throw new Exception("Not equal for operation [contains]");
            System.out.println(inside.size + "," + trueInside.size);
        }).run();
    }

    public static void testMaps(){
        new Test("Map Test", 10000, () -> {
            java.util.HashMap<String, String> base = new java.util.HashMap<>();
            Map<String, String> custom = new Map<>();

            String[] keys = generate(100000);
            for(String key : keys){
                if(base.containsKey(key)) continue;
                String value = single();
                base.put(key, value);
                custom.add(key, value);
            }

            for(String key : keys) if(!eql(base.get(key), custom.get(key))) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(keys[removeIndex]);
                custom.remove(keys[removeIndex]);
            }

            for(String key : keys) if(!eql(base.get(key), custom.get(key))) throw new Exception("Not equal after operation [remove]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Objectf Map Test", 10000, () -> {
            java.util.HashMap<String, Float> base = new java.util.HashMap<>();
            ObjfMap<String> custom = new ObjfMap<>();

            String[] keys = generate(100000);
            for(String key : keys){
                if(base.containsKey(key)) continue;
                float value = chance(0.05f) ? 0 : random(-10000, 10000);
                base.put(key, value);
                custom.add(key, value);
            }

            for(String key : keys) if(base.get(key) != custom.get(key)) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(keys[removeIndex]);
                custom.remove(keys[removeIndex]);
            }

            for(String key : keys) if(base.get(key) != null && base.get(key) != custom.get(key)) throw new Exception("Not equal after operation [remove]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Objectb Map Test", 10000, () -> {
            java.util.HashMap<String, Boolean> base = new java.util.HashMap<>();
            ObjbMap<String> custom = new ObjbMap<>();

            String[] keys = generate(100000);
            for(String key : keys){
                if(base.containsKey(key)) continue;
                boolean value = chance(0.5f);
                base.put(key, value);
                custom.add(key, value);
            }

            for(String key : keys) if(base.get(key) != custom.get(key)) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(keys[removeIndex]);
                custom.remove(keys[removeIndex]);
            }

            for(String key : keys) if(base.get(key) != null && base.get(key) != custom.get(key)) throw new Exception("Not equal after operation [remove]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Objecti Map Test", 10000, () -> {
            java.util.HashMap<String, Integer> base = new java.util.HashMap<>();
            ObjiMap<String> custom = new ObjiMap<>();

            String[] keys = generate(100000);
            for(String key : keys){
                if(base.containsKey(key)) continue;
                int value = chance(0.05f) ? 0 : randInt(-10000, 10000);
                base.put(key, value);
                custom.add(key, value);
            }

            for(String key : keys) if(base.get(key) != custom.get(key)) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(keys[removeIndex]);
                custom.remove(keys[removeIndex]);
            }

            for(String key : keys) if(base.get(key) != null && base.get(key) != custom.get(key)) throw new Exception("Not equal after operation [remove]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Float Map Test", 10000, () -> {
            java.util.HashMap<Float, String> base = new java.util.HashMap<>();
            FloatMap<String> custom = new FloatMap<>();

            float[] keys = new float[randInt(0, 100000)];
            for(int i = 0;i < keys.length;i++){
                keys[i] = chance(0.05f) ? 0 : random(-10000, 10000);
                if(base.containsKey(keys[i])) continue;
                String value = single();
                base.put(keys[i], value);
                custom.add(keys[i], value);
            }

            for(int i = 0;i < keys.length;i++) if(!eql(base.get(keys[i]), custom.get(keys[i]))) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(keys[removeIndex]);
                custom.remove(keys[removeIndex]);
            }

            for(int i = 0;i < keys.length;i++) if(!eql(base.get(keys[i]), custom.get(keys[i]))) throw new Exception("Not equal after operation [add]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Floatf Map Test", 10000, () -> {
            java.util.HashMap<Float, Float> base = new java.util.HashMap<>();
            FloatfMap custom = new FloatfMap();

            float[] keys = new float[randInt(0, 100000)];
            for(int i = 0;i < keys.length;i++){
                keys[i] = chance(0.05f) ? 0 : random(-10000, 10000);
                if(base.containsKey(keys[i])) continue;
                float value = chance(0.05f) ? 0 : random(-10000, 10000);
                base.put(keys[i], value);
                custom.add(keys[i], value);
            }

            for(int i = 0;i < keys.length;i++) if(base.get(keys[i]) != custom.get(keys[i])) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(keys[removeIndex]);
                custom.remove(keys[removeIndex]);
            }

            for(int i = 0;i < keys.length;i++) if(base.get(keys[i]) != null && base.get(keys[i]) != custom.get(keys[i])) throw new Exception("Not equal after operation [add]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
        new Test("Int Map Test", 10000, () -> {
            java.util.HashMap<Integer, String> base = new java.util.HashMap<>();
            IntMap<String> custom = new IntMap<>();

            int[] keys = new int[randInt(0, 100000)];
            for(int i = 0;i < keys.length;i++){
                keys[i] = chance(0.05f) ? 0 : randInt(-10000, 10000);
                if(base.containsKey(keys[i])) continue;
                String value = single();
                base.put(keys[i], value);
                custom.add(keys[i], value);
            }

            for(int i = 0;i < keys.length;i++) if(!eql(base.get(keys[i]), custom.get(keys[i]))) throw new Exception("Not equal after operation [add]");

            int amount = randInt(0, base.size());
            for(int i = 0;i < amount;i++){
                int removeIndex = randInt(0, base.size() - 1);
                base.remove(keys[removeIndex]);
                custom.remove(keys[removeIndex]);
            }

            for(int i = 0;i < keys.length;i++) if(!eql(base.get(keys[i]), custom.get(keys[i]))) throw new Exception("Not equal after operation [add]");

            if(base.size() != custom.size) throw new Exception("Not equal for operation [size]");
        }).run();
    }

    public static void main(String[] args){
        testSeqs();
        testSets();
        testQueues();
        testTrees();
        testMaps();

        if(failed == 0) System.out.println("\nAll tests passed!");
        else System.out.println("\n" + failed + " tests failed!");
    }

    public static class Test{
        public String name;
        public TestRunnable test;
        public int times;

        public Test(String name, TestRunnable test){
            this(name, 1000, test);
        }

        public Test(String name, int times, TestRunnable test){
            this.name = name;
            this.test = test;
            this.times = times;
        }

        public void run(){
            System.out.println("\nRunning test: [" + name + "]");
            long start = System.currentTimeMillis();
            try{
                test.run();
                System.out.println("[" + name + "] Test Passed");
            }catch(Exception e){
                System.out.println("[" + name + "] Test Failed: " + e.toString());
                e.printStackTrace();
                failed++;
            }
            long end = System.currentTimeMillis();
            System.out.println("[" + name + "] Runtime: " + (end - start) + "ms " + "(" + ((end - start) / 1000f) + "s)");
        }
    }

    public interface TestRunnable{
        public void run() throws Exception;
    }
}
