package jbsae.struct;

import jbsae.struct.Set.*;

import java.util.*;

import static jbsae.util.Structf.*;

public class Map<K, V> implements Iterable<K>{
    public MapIterator i1, i2;
    public K[] keys;
    public V[] values;
    public int size = 0;

    public Map(){
        keys = (K[])new Object[16*16*4];
        values = (V[])new Object[16*16*4];
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

    public void add(K key, V value){
        K[] keys = this.keys;
        V[] values = this.values;
        int h = key.hashCode();
        int[] checks = hashes(h, keys.length);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return;
        for(int i = 0;i < checks.length;i++){
            if(keys[checks[i]] == null){
                keys[checks[i]] = key;
                values[checks[i]] = value;
                size++;
                return;
            }
        }
        resize(keys.length << 1);
        add(key, value);
    }

    public void addAll(Object... entries){
        for(int i = 0;i < entries.length;i += 2) add((K)entries[i], (V)entries[i + 1]);
    }

    public void remove(K key){
        K[] keys = this.keys;
        V[] values = this.values;
        int h = key.hashCode();
        int[] checks = hashes(h, keys.length);
        for(int i = 0;i < checks.length;i++){
            if(eql(keys[checks[i]], key)){
                keys[checks[i]] = null;
                values[checks[i]] = null;
                size--;
            }
        }
    }

    public void removaAll(K... keys){
        for(K key : keys) remove(key);
    }

    public V get(K key){
        int h = key.hashCode();
        int[] checks = hashes(h, keys.length);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return values[checks[i]];
        return null;
    }

    public boolean contains(K key){
        int h = key.hashCode();
        int[] checks = hashes(h, keys.length);
        for(int i = 0;i < checks.length;i++) if(eql(keys[checks[i]], key)) return true;
        return false;
    }

    public void resize(int newSize){
        K[] keys = this.keys;
        V[] values = this.values;
        K[] keyValues = create(size, keys);
        V[] valueValues = create(size, values);
        int index = 0;
        for(int i = 0;i < keys.length;i++){
            if(keys[i] != null){
                keyValues[index] = keys[i];
                valueValues[index++] = values[i];
            }
        }
        size = 0;
        this.keys = create(newSize, keys);
        this.values = create(newSize, values);
        for(int i = 0;i < keyValues.length;i++) add(keyValues[i], valueValues[i]);
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
