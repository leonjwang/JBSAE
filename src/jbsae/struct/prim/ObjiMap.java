package jbsae.struct.prim;

import jbsae.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Structf.*;

public class ObjiMap<K>{
    public K[] keys;
    public int[] values;
    public int size = 0;


    public ObjiMap(){
        keys = (K[])new Object[16];
        values = new int[16];
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

    public ObjiMap<K> remove(K key){
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

    public ObjiMap<K> removeAll(K... keys){
        for(K key : keys) remove(key);
        return this;
    }


    public int get(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return 0;
    }


    public boolean contains(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
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
