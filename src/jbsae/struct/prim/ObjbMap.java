package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.*;
import jbsae.func.prim.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class ObjbMap<K>{
    public K[] keys;
    public boolean[] values;
    public int size = 0;


    public ObjbMap(){
        this(16);
    }

    public ObjbMap(int size){
        keys = (K[])new Object[size];
        values = new boolean[size];
    }


    public Object[] keys(){
        int i = 0;
        Object[] values = create(size);
        for(int j = 0;j < keys.length;j++) if(keys[j] != null) values[i++] = keys[j];
        return values;
    }

    public boolean[] values(){
        K[] keys = (K[])keys();
        boolean[] values = new boolean[size];
        for(int j = 0;j < keys.length;j++) values[j] = get(keys[j]);
        return values;
    }


    public ObjbMap<K> add(K key, boolean value){
        int steps = (trailZeros(keys.length) << 1) + 1;
        for(int step = 0;step < steps;step++){
            int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
            for(int i = 0;step == 0 && i < checks.length;i++) if(eql(keys[checks[i]], key)) return set(checks[i], key, value);
            for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == null) return set(checks[i], key, value);
            int index = checks[randInt(0, checks.length - 1)];
            K displacedKey = keys[index];
            boolean displacedValue = values[index];
            keys[index] = key;
            values[index] = value;
            key = displacedKey;
            value = displacedValue;
        }
        return resize(keys.length << 1).add(key, value);
    }

    private ObjbMap<K> set(int i, K key, boolean value){
        if(keys[i] == null) size++;
        keys[i] = key;
        values[i] = value;
        return this;
    }

    public ObjbMap<K> remove(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eql(keys[checks[i]], key)){
                keys[checks[i]] = null;
                values[checks[i]] = false;
                size--;
                return this;
            }
        }
        return this;
    }

    public ObjbMap<K> removeAll(K... keys){
        for(K key : keys) remove(key);
        return this;
    }


    public boolean get(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return false;
    }


    public boolean contains(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
    }

    public ObjbMap<K> eachKey(Cons<K> cons){
        for(int j = 0;j < keys.length;j++) if(keys[j] != null) cons.get(keys[j]);
        return this;
    }

    public ObjbMap<K> clear(){
        fill(keys, null);
        fill(values, false);
        size = 0;
        return this;
    }

    public ObjbMap<K> resize(int newSize){
        K[] keys = (K[])keys();
        boolean[] values = values();
        size = 0;
        this.keys = create(newSize, keys);
        this.values = new boolean[newSize];
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
