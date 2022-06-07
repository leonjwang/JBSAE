package jbsae.struct.prim;

import jbsae.*;

import static jbsae.util.Structf.*;

public class IntMap<V>{
    public V zero;
    public int[] keys;
    public V[] values;
    public int size = 0;


    public IntMap(){
        keys = new int[16];
        values = (V[])new Object[16];
    }


    public int[] keys(){
        int i = 0;
        int[] values = new int[size];
        for(int j = 0;j < keys.length;j++) if(keys[j] != 0) values[i++] = keys[j];
        if(zero != null) values[size - 1] = 0;
        return values;
    }

    public Object[] values(){
        int[] keys = keys();
        Object[] values = create(size);
        for(int i = 0;i < keys.length;i++) values[i] = get(keys[i]);
        return values;
    }


    public IntMap<V> add(int key, V value){
        if(value == null) return this;
        if(key == 0){
            if(zero == null){
                zero = value;
                size++;
            }
            return this;
        }
        int[] keys = this.keys;
        V[] values = this.values;
        int[] checks = hash3(key, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == key) return this;
        for(int i = 0;i < checks.length;i++){
            if(keys[checks[i]] == 0){
                keys[checks[i]] = key;
                values[checks[i]] = value;
                size++;
                return this;
            }
        }
        return resize(keys.length << 1).add(key, value);
    }

    public IntMap<V> remove(int key){
        if(key == 0){
            if(zero != null){
                zero = null;
                size--;
            }
            return this;
        }
        int[] keys = this.keys;
        V[] values = this.values;
        int[] checks = hash3(key, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(keys[checks[i]] == key){
                keys[checks[i]] = 0;
                values[checks[i]] = null;
                size--;
            }
        }
        return this;
    }

    public IntMap<V> removeAll(int... keys){
        for(int i = 0;i < keys.length;i++) remove(keys[i]);
        return this;
    }


    public V get(int key){
        if(key == 0) return zero;
        int[] checks = hash3(key, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == key) return values[checks[i]];
        return null;
    }


    public boolean contains(int key){
        if(key == 0) return zero != null;
        int[] checks = hash3(key, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == key) return true;
        return false;
    }

    public IntMap<V> clear(){
        fill(keys, 0);
        fill(values, null);
        size = 0;
        return this;
    }

    public IntMap<V> resize(int newSize){
        int[] keys = keys();
        V[] values = (V[])values();
        size = 0;
        zero = null;
        this.keys = new int[newSize];
        this.values = create(newSize, values);
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }
}
