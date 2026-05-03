package jbsae.struct;

import java.util.*;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Structf.*;

public class Map<K, V> implements Iterable<K>{
    private K[] keys;
    private V[] values;

    private int tableCap, stashCap;
    private int threshold, shift, mask, steps;

    private int stashSize;

    public float loadFactor = 0.8f;
    public int size;

    public Map(){
        this(50);
    }

    public Map(int capacity){
        setCap(nextPow2((int)(capacity / loadFactor + 1)));
        keys = create(tableCap + stashCap);
        values = create(tableCap + stashCap);
    }

    public Map<K, V> loadFactor(float loadFactor){
        this.loadFactor = loadFactor;
        this.threshold = (int)(tableCap * loadFactor);
        return this;
    }

    private void setCap(int cap){
        this.tableCap = cap;
        this.stashCap = floorLog2(cap) * 2;
        this.threshold = (int)(cap * loadFactor);
        this.shift = floorLog2(cap);
        this.mask = cap - 1;
        this.steps = (int)(rt2(cap) / 16 + 4);
    }

    public Map<K, V> add(K key, V value){
        int base = key.hashCode();
        int hash1 = base & mask;
        int hash2 = (hash(base, shift, PRIME1) & mask);
        int hash3 = (hash(base, shift, PRIME2) & mask);
        int hash4 = (hash(base, shift, PRIME3) & mask);

        if(tryReplace(hash1, key, value)) return this;
        if(tryReplace(hash2, key, value)) return this;
        if(tryReplace(hash3, key, value)) return this;
        if(tryReplace(hash4, key, value)) return this;
        for(int i = 0; i < stashSize; i++) if(tryReplace(tableCap + i, key, value)) return this;

        if(tryPlace(hash1, key, value)) return this;
        if(tryPlace(hash2, key, value)) return this;
        if(tryPlace(hash3, key, value)) return this;
        if(tryPlace(hash4, key, value)) return this;

        swapRandom(hash1, hash2, hash3, hash4, key, value);
        key = displacedKey;
        value = displacedValue;
        place(key, value);
        displacedKey = null;
        displacedValue = null;

        return this;
    }

    private boolean tryReplace(int index, K key, V value){
        if(!key.equals(keys[index])) return false;
        values[index] = value;
        return true;
    }

    public V get(K key){
        if(key == null) return null;
        int base = key.hashCode();
        int hash1 = base & mask;
        int hash2 = (hash(base, shift,  PRIME1) & mask);
        int hash3 = (hash(base, shift, PRIME2) & mask);
        int hash4 = (hash(base, shift, PRIME3) & mask);

        if(key.equals(keys[hash1])) return values[hash1];
        if(key.equals(keys[hash2])) return values[hash2];
        if(key.equals(keys[hash3])) return values[hash3];
        if(key.equals(keys[hash4])) return values[hash4];

        for(int i = 0; i < stashSize; i++) if(key.equals(keys[tableCap + i])) return values[tableCap + i];

        return null;
    }

    public Map<K, V> remove(K key){
        int base = key.hashCode();
        if(tryErase(base & mask, key)) return this;
        if(tryErase((hash(base, shift, PRIME1) & mask), key)) return this;
        if(tryErase((hash(base, shift, PRIME2) & mask), key)) return this;
        if(tryErase((hash(base, shift, PRIME3) & mask), key)) return this;
        for(int i = 0;i < stashSize;i++) if(key.equals(keys[tableCap + i])){
            keys[tableCap + i] = keys[tableCap + stashSize - 1];
            values[tableCap + i] = values[tableCap + stashSize - 1];
            keys[tableCap + stashSize - 1] = null;
            values[tableCap + stashSize - 1] = null;
            stashSize--;
            size--;
            return this;
        }
        return this;
    }

    private boolean tryErase(int index, K key){
        if(!key.equals(keys[index])) return false;
        keys[index] = null;
        values[index] = null;
        size--;
        return true;
    }

    public Map<K, V> clear(){
        Arrays.fill(keys, null);
        Arrays.fill(values, null);
        size = stashSize = 0;
        return this;
    }

    public Map<K, V> ensure(int space){
        int needed = (int)((size + space) / loadFactor + 1);
        if((size + needed) > tableCap) resize(nextPow2((size + needed)));
        return this;
    }

    private K displacedKey = null;
    private V displacedValue = null;

    private void place(K key, V value){
        for(int i = 0; i < steps; i++){
            int base = key.hashCode();
            int hash1 = base & mask;
            if(tryPlace(hash1, key, value)) return;

            int hash2 = (hash(base, shift, PRIME1) & mask);
            if(tryPlace(hash2, key, value)) return;

            int hash3 = (hash(base,shift,  PRIME2) & mask);
            if(tryPlace(hash3, key, value)) return;

            int hash4 = (hash(base, shift, PRIME3) & mask);
            if(tryPlace(hash4, key, value)) return;

            swapRandom(hash1, hash2, hash3, hash4, key, value);
            key = displacedKey;
            value = displacedValue;
        }

        stash(key, value);
    }

    private boolean tryPlace(int index, K key, V value){
        if(keys[index] != null) return false;
        keys[index] = key;
        values[index] = value;
        if(size++ >= threshold) resize(tableCap * 2);
        return true;
    }

    private void swapRandom(int hash1, int hash2, int hash3, int hash4, K key, V value){
        switch(RAND.nexti() & 3){
            case 0:
                swap(hash1, key, value);
                return;
            case 1:
                swap(hash2, key, value);
                return;
            case 2:
                swap(hash3, key, value);
                return;
            default:
                swap(hash4, key, value);
        }
    }

    private void swap(int index, K key, V value){
        displacedKey = keys[index];
        displacedValue = values[index];
        keys[index] = key;
        values[index] = value;
    }

    private void stash(K key, V value){
        if(stashSize >= stashCap) resize(tableCap * 2);
        values[tableCap + stashSize] = value;
        keys[tableCap + stashSize++] = key;
        size++;
    }

    private void resize(int capacity){
        K[] oldKeys = keys;
        V[] oldVals = values;
        setCap(capacity);
        keys = create(tableCap + stashCap);
        values = create(tableCap + stashCap);
        size = stashSize = 0;
        for(int i = 0; i < oldKeys.length; i++) if(oldKeys[i] != null) place(oldKeys[i], oldVals[i]);
        displacedKey = null;
        displacedValue = null;
    }

    @Override
    public Iterator<K> iterator(){
        return new KeyIterator();
    }

    public Iterator<V> values(){
        return new ValIterator();
    }

    @Override
    public String toString(){
        return itrToString(this);
    }

    private class KeyIterator implements Iterator<K>, Sized{
        public int nextIndex = 0;

        public KeyIterator(){
        }

        public void findNextIndex(){
            for(; nextIndex < keys.length; nextIndex++) if(keys[nextIndex] != null) return;
        }

        @Override
        public boolean hasNext(){
            findNextIndex();
            return nextIndex < keys.length;
        }

        @Override
        public K next(){
            findNextIndex();
            return keys[nextIndex++];
        }

        @Override
        public int size(){
            return size;
        }
    }

    private class ValIterator implements Iterator<V>, Sized{
        public int nextIndex = 0;

        public ValIterator(){
        }

        public void findNextIndex(){
            for(; nextIndex < keys.length; nextIndex++) if(keys[nextIndex] != null) return;
        }

        @Override
        public boolean hasNext(){
            findNextIndex();
            return nextIndex < keys.length;
        }

        @Override
        public V next(){
            findNextIndex();
            return values[nextIndex++];
        }

        @Override
        public int size(){
            return size;
        }
    }
}