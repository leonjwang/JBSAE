package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class ObjiMap<K>{
    public K[] keys;
    public int[] values;
    public int size = 0;


    public ObjiMap(){
        this(16);
    }

    public ObjiMap(int size){
        keys = (K[])new Object[size];
        values = new int[size];
    }


    public Object[] keys(){
        int i = 0;
        Object[] values = create(size);
        for(int j = 0;j < keys.length;j++) if(keys[j] != null) values[i++] = keys[j];
        return values;
    }

    public int[] values(){
        K[] keys = (K[])keys();
        int[] values = new int[size];
        for(int j = 0;j < keys.length;j++) values[j] = get(keys[j]);
        return values;
    }


    public ObjiMap<K> add(K key, int value){
        int steps = (trailZeros(keys.length) << 1) + 1;
        for(int step = 0;step < steps;step++){
            int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
            for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return set(checks[i], key, value);
            for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == null) return set(checks[i], key, value);
            int index = checks[randInt(0, checks.length - 1)];
            K displacedKey = keys[index];
            int displacedValue = values[index];
            keys[index] = key;
            values[index] = value;
            key = displacedKey;
            value = displacedValue;
        }
        return resize(keys.length << 1).add(key, value);
    }

    private ObjiMap<K> set(int i, K key, int value){
        if(keys[i] == null) size++;
        keys[i] = key;
        values[i] = value;
        return this;
    }

    public ObjiMap<K> remove(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eql(keys[checks[i]], key)){
                keys[checks[i]] = null;
                values[checks[i]] = 0;
                size--;
                return this;
            }
        }
        return this;
    }

    public ObjiMap<K> removeAll(K... keys){
        for(K key : keys) remove(key);
        return this;
    }


    public int get(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return 0;
    }


    public boolean contains(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
    }

    public ObjiMap<K> eachKey(Cons<K> cons){
        for(int j = 0;j < keys.length;j++) if(keys[j] != null) cons.get(keys[j]);
        return this;
    }

    public ObjiMap<K> clear(){
        fill(keys, null);
        fill(values, 0);
        size = 0;
        return this;
    }

    public ObjiMap<K> resize(int newSize){
        K[] keys = (K[])keys();
        int[] values = values();
        size = 0;
        this.keys = create(newSize, keys);
        this.values = new int[newSize];
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
