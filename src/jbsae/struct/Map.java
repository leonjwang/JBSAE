package jbsae.struct;

import jbsae.*;
import jbsae.func.*;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;


public class Map<K, V> implements Iterable<K>{
    public MapIterator i1, i2;
    public K[] keys;
    public V[] values;
    public int size = 0;


    public Map(){
        this(16);
    }

    public Map(int size){
        keys = (K[])new Object[size];
        values = (V[])new Object[size];
        i1 = new MapIterator();
        i2 = new MapIterator();
    }

    public Map(Object... entries){
        this(entries.length);
        for(int i = 0;i < entries.length;i += 2) add((K)entries[i], (V)entries[i + 1]);
    }


    public Object[] keys(){
        int i = 0;
        Object[] keys = create(size);
        for(K key : this) keys[i++] = key;
        return keys;
    }

    public Object[] values(){
        int i = 0;
        Object[] values = create(size);
        for(K key : this) values[i++] = get(key);
        return values;
    }


    public Map<K, V> add(K key, V value){
        int steps = (trailZeros(keys.length) << 1) + 1;
        for(int step = 0;step < steps;step++){
            int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
            for(int i = 0;step == 0 && i < checks.length;i++) if(eql(keys[checks[i]], key)) return set(checks[i], key, value);
            for(int i = 0;i < checks.length;i++) if(keys[checks[i]] == null) return set(checks[i], key, value);
            int index = checks[randInt(0, checks.length - 1)];
            K displacedKey = keys[index];
            V displacedValue = values[index];
            keys[index] = key;
            values[index] = value;
            key = displacedKey;
            value = displacedValue;
        }
        return resize(keys.length << 1).add(key, value);
    }

    private Map<K, V> set(int i, K key, V value){
        if(keys[i] == null) size++;
        keys[i] = key;
        values[i] = value;
        return this;
    }

    public Map<K, V> addAll(Object... entries){
        for(int i = 0;i < entries.length;i += 2) add((K)entries[i], (V)entries[i + 1]);
        return this;
    }

    public Map<K, V> remove(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eql(keys[checks[i]], key)){
                keys[checks[i]] = null;
                values[checks[i]] = null;
                size--;
                return this;
            }
        }
        return this;
    }

    public Map<K, V> removeAll(K... keys){
        for(K key : keys) remove(key);
        return this;
    }


    public V get(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return null;
    }


    public boolean contains(K key){
        int[] checks = hash3(key.hashCode(), keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
    }

    public Map<K, V> eachKey(Cons<K> cons){
        for(K key : this) cons.get(key);
        return this;
    }

    public Map<K, V> clear(){
        fill(keys, null);
        fill(values, null);
        size = 0;
        return this;
    }

    public Map<K, V> resize(int newSize){
        K[] keys = (K[])keys();
        V[] values = (V[])values();
        size = 0;
        this.keys = create(newSize, keys);
        this.values = create(newSize, values);
        for(int j = 0;j < keys.length;j++) add(keys[j], values[j]);
        return this;
    }


    @Override
    public Iterator<K> iterator(){
        if(i1.nextIndex >= keys.length){
            i1.nextIndex = 0;
            return i1;
        }
        if(i2.nextIndex >= keys.length){
            i2.nextIndex = 0;
            return i2;
        }
        return new MapIterator();
    }

    @Override
    public String toString(){
        return itrToString(this);
    }

    private class MapIterator implements Iterator<K>{
        public int nextIndex = -1;

        public MapIterator(){
        }

        public void findNextIndex(){
            for(nextIndex++;nextIndex < keys.length;nextIndex++) if(keys[nextIndex] != null) return;
        }

        @Override
        public boolean hasNext(){
            findNextIndex();
            return nextIndex < keys.length;
        }

        @Override
        public K next(){
            return keys[nextIndex];
        }
    }
}
