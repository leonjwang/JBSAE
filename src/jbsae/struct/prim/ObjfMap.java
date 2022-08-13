package jbsae.struct.prim;

import jbsae.*;
import jbsae.func.*;
import jbsae.struct.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class ObjfMap<K>{
    public K[] keys;
    public float[] values;
    public int size = 0;


    public ObjfMap(){
        this(16);
    }

    public ObjfMap(int size){
        keys = (K[])new Object[size];
        values = new float[size];
    }


    public Object[] keys(){
        int i = 0;
        Object[] values = create(size);
        for(int j = 0;j < keys.length;j++) if(keys[j] != null) values[i++] = keys[j];
        return values;
    }

    public float[] values(){
        K[] keys = (K[])keys();
        float[] values = new float[size];
        for(int j = 0;j < keys.length;j++) values[j] = get(keys[j]);
        return values;
    }


    public ObjfMap<K> add(K key, float value){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return set(checks[i], key, value);
        for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == null) return set(checks[i], key, value);
        resize(keys.length << 1);
        add(key, value);
        return this;
    }

    private ObjfMap<K> set(int i, K key, float value){
        if(keys[i] == null) size++;
        keys[i] = key;
        values[i] = value;
        return this;
    }

    public ObjfMap<K> remove(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eql(keys[checks[i]], key)){
                keys[checks[i]] = null;
                values[checks[i]] = 0;
                size--;
            }
        }
        return this;
    }

    public ObjfMap<K> removeAll(K... keys){
        for(K key : keys) remove(key);
        return this;
    }


    public float get(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return nan;
    }


    public boolean contains(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
    }

    public ObjfMap<K> eachKey(Cons<K> cons){
        for(int j = 0;j < keys.length;j++) if(keys[j] != null) cons.get(keys[j]);
        return this;
    }

    public ObjfMap<K> clear(){
        fill(keys, null);
        fill(values, 0);
        size = 0;
        return this;
    }

    public ObjfMap<K> resize(int newSize){
        K[] keys = (K[])keys();
        float[] values = values();
        size = 0;
        this.keys = create(newSize, keys);
        this.values = new float[newSize];
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
