package jbsae.struct.prim;

import jbsae.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class ObjfMap<K>{
    public K[] keys;
    public float[] values;
    public int size = 0;


    public ObjfMap(){
        keys = (K[])new Object[16];
        values = new float[16];
    }

    public ObjfMap(Object... entries){
        this();
        for(int i = 0;i < entries.length;i += 2) add((K)entries[i], (Float)entries[i + 1]);
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
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return this;
        for(int i = 0;i < checks.length;i++){
            if(keys[checks[i]] == null){
                keys[checks[i]] = key;
                values[checks[i]] = value;
                size++;
                return this;
            }
        }
        resize(keys.length << 1);
        add(key, value);
        return this;
    }

    public ObjfMap<K> addAll(Object... entries){
        for(int i = 0;i < entries.length;i += 2) add((K)entries[i], (Float)entries[i + 1]);
        return this;
    }

    public ObjfMap<K> remove(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
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
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return nan;
    }


    public boolean contains(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
    }

    public ObjfMap<K> clear(){
        keys = create(16, keys);
        values = new float[16];
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
