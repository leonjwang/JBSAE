package jbsae.struct;

import jbsae.*;

import java.util.*;

import static jbsae.util.Structf.*;

/** @author Nathan Sweet */
public class Map<K, V> implements Iterable<K>{
    public MapIterator i1, i2;
    public K[] keys;
    public V[] values;
    public int size = 0;


    public Map(){
        keys = (K[])new Object[16];
        values = (V[])new Object[16];
        i1 = new MapIterator();
        i2 = new MapIterator();
    }

    public Map(Object... entries){
        this();
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
        K[] keys = this.keys;
        V[] values = this.values;
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

    public Map<K, V> addAll(Object... entries){
        for(int i = 0;i < entries.length;i += 2) add((K)entries[i], (V)entries[i + 1]);
        return this;
    }

    public Map<K, V> remove(K key){
        K[] keys = this.keys;
        V[] values = this.values;
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++){
            if(eql(keys[checks[i]], key)){
                keys[checks[i]] = null;
                values[checks[i]] = null;
                size--;
            }
        }
        return this;
    }

    public Map<K, V> removeAll(K... keys){
        for(K key : keys) remove(key);
        return this;
    }


    public V get(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return null;
    }


    public boolean contains(K key){
        int h = key.hashCode();
        int[] checks = hash3(h, keys.length, Tmp.i3);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
    }

    public Map<K, V> clear(){
        keys = create(16, keys);
        values = create(16, values);
        size = 0;
        return this;
    }

    public Map<K, V> resize(int newSize){
        K[] keys = this.keys;
        V[] values = this.values;
        K[] keyValues = create(size, keys);
        V[] valueValues = create(size, values);
        int i = (size = 0);
        for(int j = 0;j < keys.length;j++){
            if(keys[j] != null){
                keyValues[i] = keys[j];
                valueValues[i++] = values[j];
            }
        }
        this.keys = create(newSize, keys);
        this.values = create(newSize, values);
        for(int j = 0;j < keyValues.length;j++) add(keyValues[j], valueValues[j]);
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
