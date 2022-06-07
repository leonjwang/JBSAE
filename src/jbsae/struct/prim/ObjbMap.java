package jbsae.struct.prim;

import jbsae.*;

import static jbsae.util.Structf.*;

public class ObjbMap<K>{
    public K[] keys;
    public boolean[] values;
    public int size = 0;


    public ObjbMap(){
        keys = (K[])new Object[16];
        values = new boolean[16];
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

    public ObjbMap<K> remove(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eql(keys[checks[i]], key)){
                keys[checks[i]] = null;
                values[checks[i]] = false;
                size--;
            }
        }
        return this;
    }

    public ObjbMap<K> removeAll(K... keys){
        for(K key : keys) remove(key);
        return this;
    }


    public boolean get(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return false;
    }


    public boolean contains(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
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
