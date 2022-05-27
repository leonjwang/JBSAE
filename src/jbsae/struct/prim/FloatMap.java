package jbsae.struct.prim;

import jbsae.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class FloatMap<V>{
    public V zero;
    public float[] keys;
    public V[] values;
    public int size = 0;


    public FloatMap(){
        keys = new float[16];
        values = (V[])new Object[16];
    }

    public FloatMap(Object... entries){
        this();
        for(int i = 0;i < entries.length;i += 2) add((Float)entries[i], (V)entries[i + 1]);
    }


    public float[] keys(){
        int i = 0;
        float[] values = new float[size];
        for(int j = 0;j < keys.length;j++) if(keys[j] != 0) values[i++] = keys[j];
        if(zero != null) values[size - 1] = 0;
        return values;
    }

    public Object[] values(){
        float[] keys = keys();
        Object[] values = create(size);
        for(int i = 0;i < keys.length;i++) values[i] = get(keys[i]);
        return values;
    }


    public FloatMap<V> add(float key, V value){
        if(value == null) return this;
        if(zero(key)){
            if(zero == null){
                zero = value;
                size++;
            }
            return this;
        }
        float[] keys = this.keys;
        V[] values = this.values;
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return this;
        for(int i = 0;i < checks.length;i++){
            if(zero(keys[checks[i]])){
                keys[checks[i]] = key;
                values[checks[i]] = value;
                size++;
                return this;
            }
        }
        return resize(keys.length << 1).add(key, value);
    }

    public FloatMap<V> addAll(Object... entries){
        for(int i = 0;i < entries.length;i += 2) add((Float)entries[i], (V)entries[i + 1]);
        return this;
    }

    public FloatMap<V> remove(float key){
        if(zero(key)){
            if(zero != null){
                zero = null;
                size--;
            }
            return this;
        }
        float[] keys = this.keys;
        V[] values = this.values;
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eqlf(keys[checks[i]], key)){
                keys[checks[i]] = 0;
                values[checks[i]] = null;
                size--;
            }
        }
        return this;
    }

    public FloatMap<V> removeAll(float... keys){
        for(int i = 0;i < keys.length;i++) remove(keys[i]);
        return this;
    }


    public V get(float key){
        if(zero(key)) return zero;
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return values[checks[i]];
        return null;
    }


    public boolean contains(float key){
        if(zero(key)) return zero != null;
        int h = intBits(key);
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eqlf(keys[checks[i]], key)) return true;
        return false;
    }

    public FloatMap<V> clear(){
        fill(keys, 0);
        fill(values, null);
        size = 0;
        return this;
    }

    public FloatMap<V> resize(int newSize){
        float[] keys = keys();
        V[] values = (V[])values();
        size = 0;
        zero = null;
        this.keys = new float[newSize];
        this.values = create(newSize, values);
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
